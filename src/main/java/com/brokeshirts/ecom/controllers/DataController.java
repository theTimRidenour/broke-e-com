package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Data;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.Cart;
import com.brokeshirts.ecom.models.Inventory;
import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.User;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="data")
public class DataController {

    @Autowired
    private AddressesDao addressesDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CategoriesDao categoriesDao;

    @Autowired
    private ColorsDao colorsDao;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private PhotosDao photosDao;

    @Autowired
    private ProductsDao productsDao;

    @Autowired
    private SizesDao sizesDao;

    @Autowired
    private TypesDao typesDao;

    @Autowired
    private StylesDao stylesDao;

    @Autowired
    private UserDao userDao;

//// ADD DATA

    // ADD CATEGORY
    @RequestMapping(value="add/category", method = RequestMethod.POST)
    public String addCategory(@RequestParam("categoryName") String categoryName) {

        if (!categoryName.isEmpty()) {
            Data.addCat(categoryName, categoriesDao);
        }
        return "redirect:/admin/categories";
    }

    // ADD COLOR
    @RequestMapping(value="add/color", method = RequestMethod.POST)
    public String addColor(@RequestParam("file") MultipartFile file, @RequestParam String hex, @RequestParam String name) {

        if (!hex.isEmpty() && !name.isEmpty()) {
            Data.addColor(file, hex, name, colorsDao);
        }
        return "redirect:/admin/colors";
    }

    // ADD INVENTORY ITEM
    @RequestMapping(value="add/item", method = RequestMethod.POST)
    public String addItem(@RequestParam int productId, @RequestParam int colorId, @RequestParam(value="sizeIds", required = true) int[] sizeIds, @RequestParam("file") MultipartFile file, @RequestParam String sku, @RequestParam float price) {

        Data.addItem(price, productId, colorId, sizeIds, file, sku, productsDao, photosDao, inventoryDao);

        return "redirect:/admin/products";
    }

    // ADD PRODUCT
    @RequestMapping(value="add/product", method = RequestMethod.POST)
    public String addProduct(@RequestParam int categoryId, @RequestParam int typeId, @RequestParam(value="styleIds", required = false) int[] styleIds, @RequestParam("file") MultipartFile file, String name) {

        Data.addProduct(categoryId, typeId, styleIds, stylesDao, name, file, photosDao, productsDao, typesDao);

        return "redirect:/admin/products";
    }

    // ADD PRODUCT TO CART
    @RequestMapping(value="cart", method = RequestMethod.POST)
    public String addToCart(@RequestParam int productId, @RequestParam int colorId, @RequestParam int sizeId, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        Inventory item = Data.findItem(productId, sizeId, colorId, productsDao, inventoryDao);
        Store.addItemToCart(item.getId(), cartItems, response);

        return "redirect:/store/product/" + productId;
    }

    // DISPLAY ADD PRODUCT DESCRIPTIONS FORM
    @RequestMapping(value="add/descriptions/{productId}", method = RequestMethod.GET)
    public String addDescriptionForm(@PathVariable int productId, Model model, @CookieValue(value = "user", defaultValue = "guest") String user, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

        model.addAttribute("title", "ADMIN");
        model.addAttribute("product", productsDao.findById(productId).orElse(new Products()));
        model.addAttribute("colors", Store.prodColorsImages(productsDao.findById(productId).orElse(new Products()), colorsDao, photosDao));
        model.addAttribute("username", Data.userHeaderName(user, userDao));

        return "admin/description";
    }

    // PROCESS ADD PRODUCT DESCRIPTIONS FORM
    @RequestMapping(value = "description", method = RequestMethod.POST)
    public String processAddDescription(@RequestParam int productId, @RequestParam String desc) {

        Products theProduct = productsDao.findById(productId).orElse(new Products());
        theProduct.setDescriptions(desc);
        productsDao.save(theProduct);

        return "redirect:/admin/products";
    }

    // ADD SIZE
    @RequestMapping(value="add/size", method = RequestMethod.POST)
    public String addSize(@RequestParam String longName, @RequestParam String shortName) {

        if (!longName.isEmpty() && !shortName.isEmpty()) {
            Data.addSize(longName, shortName, sizesDao);
        }
        return "redirect:/admin/sizes";
    }

    // ADD STYLE
    @RequestMapping(value="add/style", method = RequestMethod.POST)
    public String addStyle(@RequestParam("styleName") String styleName, @RequestParam("categoryId") int categoryId) {

        if (!styleName.isEmpty() && categoryId != 0) {
            Data.addStyle(styleName, categoryId, stylesDao, categoriesDao);
        }
        return "redirect:/admin/categories";
    }

    // ADD SUB-CATEGORY
    @RequestMapping(value="add/subcategory", method = RequestMethod.POST)
    public String addType(@RequestParam("typeName") String typeName, @RequestParam("categoryId") int categoryId) {

        if (!typeName.isEmpty() && categoryId != 0) {
            Data.addType(typeName, categoryId, typesDao, categoriesDao);
        }
        return "redirect:/admin/categories";
    }

//// UPDATE DATA

    // UPDATE QUANTITY OF INVENTORY ITEM
    @RequestMapping(value="update/quantity", method = RequestMethod.POST)
    public String updateItemQuantity(@RequestParam int itemId, @RequestParam int quantity) {

        if (itemId != 0) {
            Data.updateItemQuantity(itemId, quantity, inventoryDao);
        }
        return "redirect:/admin/products";
    }

    // UPDATE QUANTITY OF ITEM IN CART
    @RequestMapping(value="cart/updateQuant", method = RequestMethod.POST)
    public String cartQuantUpdate(@CookieValue(value = "cartItems", defaultValue = "empty")String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response, @RequestParam String itemId, @RequestParam String quantity) {

        System.out.println("itemId: " + itemId);
        System.out.println("quantity: " + quantity);
        System.out.println("cartItems: " + cartItems);

        String item = "";
        String quant = "";
        String newCart = "";
        int tracker = 0;
        int cnt = 0;

        for (char c : cartItems.toCharArray()) {
            if (c == '/') {
                tracker = 1;
            } else if (c == '.') {
                tracker = 0;
                if (item.equals(itemId)) {
                    if (Integer.valueOf(quantity) > 0) {
                        if (cnt > 0) {
                            newCart += ".";
                        }
                        newCart += item + "/" + quantity;
                        cnt++;
                    }
                } else {
                    if (cnt > 0) {
                        newCart += ".";
                    }
                    newCart += item + "/" + quant;
                    cnt++;
                }
                item = "";
                quant = "";
            } else if (tracker == 0) {
                item += c;
            } else {
                quant += c;
            }
        }
        if (item.equals(itemId)) {
            if (Integer.valueOf(quantity) > 0) {
                if (cnt > 0) {
                    newCart += ".";
                }
                newCart += item + "/" + quantity;
            }
        } else {
            if (cnt > 0) {
                newCart += ".";
            }
            newCart += item + "/" + quant;
        }

        Cookie cookie = new Cookie("cartItems", newCart);
        cookie.setPath("/");
        response.addCookie(cookie);

        User theUser = userDao.findByToken(user);
        Cart theCart = cartDao.findByUserId(theUser.getId());

        theCart.setCartItems(newCart);
        cartDao.save(theCart);

        return "redirect:/checkout/cart";
    }


//// DELETE DATA

    // DELETE CATEGORY AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/cat/{id}")
    public String delCat(@PathVariable int id) {

        Data.delCat(id, categoriesDao, typesDao, stylesDao);
        return "redirect:/admin/archive";
    }

    // DELETE COLOR AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/color/{id}")
    public String delColor(@PathVariable int id) {

        Data.delColor(id, colorsDao);
        return "redirect:/admin/archive";
    }

    // DELETE PRODUCT AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/product/{id}")
    public String delProduct(@PathVariable int id) {

        Data.delProduct(id, productsDao, photosDao);
        return "redirect:/admin/archive";
    }

    // DELETE SIZE AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/size/{id}")
    public String delSize(@PathVariable int id) {

        Data.delSize(id, sizesDao);
        return "redirect:/admin/archive";
    }

    // DELETE STYLE AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/style/{id}")
    public String delStyle(@PathVariable int id) {

        Data.delStyle(id, stylesDao);
        return "redirect:/admin/archive";
    }

    // DELETE SUB-CATEGORY AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/type/{id}")
    public String delType(@PathVariable int id) {

        Data.delType(id, typesDao);
        return "redirect:/admin/archive";
    }

//// CREATE ADMIN (ONE TIME)

    // ONE TIME MAKE ADMIN USER
    @RequestMapping(value = "create-admin")
    public String makeAdmin(@CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {
        boolean adminCheck = false;

        for (User checkAdmin : userDao.findAll()) {
            if (checkAdmin.getRole().equals("ADMIN")) {
                adminCheck = true;
            }
        }

        if (!adminCheck) {
            User makeAdmin = userDao.findByToken(user);
            makeAdmin.setRole("ADMIN");
            userDao.save(makeAdmin);
        }

        return "redirect:/";
    }
}
