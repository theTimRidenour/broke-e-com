package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Data;
import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.Addresses;
import com.brokeshirts.ecom.models.Inventory;
import com.brokeshirts.ecom.models.Orders;
import com.brokeshirts.ecom.models.User;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping(value = "checkout")
public class CheckoutController {

    @Autowired
    UserDao userDao;

    @Autowired
    InventoryDao inventoryDao;

    @Autowired
    CategoriesDao categoriesDao;

    @Autowired
    SizesDao sizesDao;

    @Autowired
    PhotosDao photosDao;

    @Autowired
    ColorsDao colorsDao;

    @Autowired
    TypesDao typesDao;

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    AddressesDao addressesDao;

//// DISPLAY CONTROLLERS

    // CART
    @RequestMapping(value = "cart")
    public String cart (Model model,@CookieValue(value = "order", defaultValue = "no_order") String order, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {
        if (user.equals("guest")) {
            return "redirect:/";
        }

        if (order.equals("no_order")) {
            int userId = userDao.findByToken(user).getId();
            for (Orders checkOrder : ordersDao.findAll()) {
                if (checkOrder.getUserId() == userId) {
                    if (checkOrder.getOrderPaid() == null) {
                        Cookie cookie = new Cookie("order", checkOrder.getToken());
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        order = checkOrder.getToken();
                    }
                }
            }
        }


        if (!order.equals("no_order")) {
            Orders checkOrder = ordersDao.findByToken(order);
            if (checkOrder.getInventory() != null) {
                return "redirect:/checkout/shipping";
            }
        }


        model.addAttribute("title", "CHECKOUT");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
        model.addAttribute("returnPath", "/");
        model.addAttribute("username", Data.userHeaderName(user, userDao));
        model.addAttribute("allCartItems", Store.loadCart(cartItems, inventoryDao, sizesDao, photosDao, colorsDao));
        model.addAttribute("checkout", "cart");
        model.addAttribute("cartPrice", Store.cartPrices(cartItems, inventoryDao));

        return "checkout/cart";
    }

    // SHIPPING DETAILS
    @RequestMapping(value = "shipping")
    public String shipping (Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {
        if (user.equals("guest")) {
            return "redirect:/";
        }

        model.addAttribute("title", "CHECKOUT");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
        model.addAttribute("returnPath", "/");
        model.addAttribute("username", Data.userHeaderName(user, userDao));
        model.addAttribute("allCartItems", Store.loadCart(cartItems, inventoryDao, sizesDao, photosDao, colorsDao));
        model.addAttribute("checkout", "shipping");
        model.addAttribute("cartPrice", Store.cartPrices(cartItems, inventoryDao));
        model.addAttribute("freeShipping", Store.checkShipping(cartItems, inventoryDao));

        return "checkout/shipping";
    }

//// VERIFY, CREATE, MODIFY ORDER

    // CANCEL ORDER IN PROGRESS
    @RequestMapping(value = "cancel")
    public String cancelProgress(@CookieValue(value = "order", defaultValue = "no_order") String order) {
        int tracker = 0;
        Orders cancelOrder = ordersDao.findByToken(order);
        Inventory theItem = new Inventory();
        String itemId = "";
        String quantity = "";

        for (char c : cancelOrder.getInventory().toCharArray()) {
            if (c == '/') {
                if (tracker == 0) {
                    tracker = 1;
                } else {
                    tracker = 2;
                }
            } else if (c == '.') {
                if (tracker == 2) {
                    tracker = 0;
                    theItem = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
                    theItem.setQuantity(theItem.getQuantity() + Integer.valueOf(quantity));
                    inventoryDao.save(theItem);
                    theItem = new Inventory();
                    itemId = "";
                    quantity = "";
                }
            } else if (tracker == 1) {
                itemId += c;
            } else if (tracker == 2) {
                quantity += c;
            }
        }
        theItem = inventoryDao.findById(Integer.valueOf(itemId)).orElse(new Inventory());
        theItem.setQuantity(theItem.getQuantity() + Integer.valueOf(quantity));
        inventoryDao.save(theItem);

        cancelOrder.setInventory(null);
        ordersDao.save(cancelOrder);

        Cookie cookie = new Cookie("order", "no_order");

        return "redirect:/";
    }

    // VERIFY ADDRESS
    @RequestMapping(value = "verify/address", method = RequestMethod.POST)
    public String verifyAddress(@RequestParam String firstName, @RequestParam String middleInitial, @RequestParam String lastName, @RequestParam String addressOne, @RequestParam String addressTwo, @RequestParam String city, @RequestParam String state, @RequestParam int zipCode, @RequestParam Float shippingCharge, @CookieValue(value = "user", defaultValue = "guest") String user, @CookieValue(value = "order", defaultValue = "no_order") String order) {
        if (firstName.equals("") || lastName.equals("") || addressOne.equals("") || city.equals("") || state.equals("") || zipCode < 10000 || zipCode > 99999) {
            return "redirect:/checkout/shipping";
        }

        User theUser = userDao.findByToken(user);
        Orders theOrder = ordersDao.findByToken(order);
        Addresses address = new Addresses();

        address.setFirstName(firstName);
        address.setMiddleInitial(middleInitial);
        address.setLastName(lastName);
        address.setAddressOne(addressOne);
        address.setAddressTwo(addressTwo);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        address.setUserId(theUser.getId());
        addressesDao.save(address);

        Addresses checkAddresses = new Addresses();
        for (Addresses oneAddress : addressesDao.findAll()) {
            if (oneAddress.getFirstName().equals(firstName)) {
                if (oneAddress.getLastName().equals(lastName)) {
                    if (oneAddress.getAddressOne().equals(addressOne)) {
                        if (oneAddress.getZipCode() == zipCode) {
                            checkAddresses = oneAddress;
                        }
                    }
                }
            }
        }

        theOrder.setAddressId(checkAddresses.getId());
        theOrder.setShipping(shippingCharge);
        ordersDao.save(theOrder);

        return "redirect:/checkout/payment";
    }

    // VERIFY CART
    @RequestMapping(value = "verify/cart")
    public String verifyCart(@CookieValue(value = "order", defaultValue = "no_order") String order, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {
        if (user.equals("guest") || cartItems.equals("empty")) {
            return "redirect:/";
        }

        if (Data.checkCart(cartItems, inventoryDao)) {
            Orders myOrder = new Orders();
            Data.removeCartFromInventory(cartItems, inventoryDao);
            if (!order.equals("no_order")) {
                myOrder = ordersDao.findByToken(order);
            }
            myOrder.setUserId(userDao.findByToken(user).getId());
            myOrder.setInventory(Data.createInventoryString(cartItems, inventoryDao));
            myOrder.setToken(UUID.randomUUID().toString());
            ordersDao.save(myOrder);

            Cookie newCookie = new Cookie("order", myOrder.getToken());
            newCookie.setPath("/");
            newCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(newCookie);

        } else {
            return "redirect:/checkout/cart";
        }

        return "redirect:/checkout/shipping";
    }
}
