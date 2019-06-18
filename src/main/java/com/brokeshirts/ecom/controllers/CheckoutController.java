package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Data;
import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

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

//// DISPLAY CONTROLLERS

    // CART
    @RequestMapping(value = "cart")
    public String cart (Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {
        if (userDao.equals("guest")) {
            return "redirect:/";
        }

        model.addAttribute("title", "cart");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
        model.addAttribute("returnPath", "/");
        model.addAttribute("username", Data.userHeaderName(user, userDao));
        model.addAttribute("allCartItems", Store.loadCart(cartItems, inventoryDao, sizesDao, photosDao, colorsDao));

        return "checkout/cart";
    }
}
