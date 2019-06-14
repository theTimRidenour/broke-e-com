package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Data;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.Cart;
import com.brokeshirts.ecom.models.Inventory;
import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.User;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

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
    public String addDescriptionForm(@PathVariable int productId, Model model) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

        model.addAttribute("title", "ADMIN");
        model.addAttribute("product", productsDao.findById(productId).orElse(new Products()));
        model.addAttribute("colors", Store.prodColorsImages(productsDao.findById(productId).orElse(new Products()), colorsDao, photosDao));

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

//// USER LOGIN, REGISTRATION, AND LOG OUT

    // USER REGISTRATION
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @RequestParam String returnPath, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, HttpServletResponse response) {

        // EMAIL VALIDATION
        User newUser = userDao.findByEmail(email);
        if (newUser != null) {
            model.addAttribute("emailError", "email already registered");
            model.addAttribute("loginForm", "LOG");
            model.addAttribute("emailPH", email);
            return "redirect:" + returnPath;
        }

        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (!email.matches(regex)) {
            model.addAttribute("emailError", "Must enter valid email address");
            model.addAttribute("loginForm", "REG");
            return "redirect:" + returnPath;
        }

        // PASSWORD VALIDATION
        if (!password.equals(confirmPassword)) {
            model.addAttribute("passwordError", "Passwords do not match.");
            model.addAttribute("loginForm", "REG");
            return "redirect:" + returnPath;
        } else if (password.length() < 5 || password.length() > 50) {
            model.addAttribute("passwordError", "Password must be between 5 and 50 characters.");
            model.addAttribute("loginForm", "REG");
            return "redirect:" + returnPath;
        }

        String token = UUID.randomUUID().toString();

        // CREATE USER AND LOGIN
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        createUser.setRole("USER");
        createUser.setToken(token);
        userDao.save(createUser);

        Cookie newCookie = new Cookie("user", token);
        newCookie.setPath("/");
        newCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(newCookie);

        // ADD CART COOKIE TO DATABASE
        User findId = userDao.findByToken(token);
        Cart checkCart = cartDao.findByUserId(findId.getId());

        if (!cartItems.equals("empty")) {
            if (checkCart == null) {
                Cart newCart = new Cart();
                newCart.setUserId(findId.getId());
                newCart.setCartItems(cartItems);
                cartDao.save(newCart);
            } else {
                checkCart.setCartItems(cartItems);
                cartDao.save(checkCart);
            }
        } else {
            if  (checkCart == null) {
                Cart newCart = new Cart();
                newCart.setUserId(findId.getId());
                newCart.setCartItems("");
                cartDao.save(newCart);
            } else {
                checkCart.setCartItems("");
                cartDao.save(checkCart);
            }
        }

        return "redirect:" + returnPath;
    }

    // USER LOGIN
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @RequestParam String returnPath, @RequestParam String email, @RequestParam String password, HttpServletResponse response) {

        User loginUser = userDao.findByEmail(email);
        if (loginUser == null) {
            model.addAttribute("emailError", "wrong email");
            model.addAttribute("loginForm", "LOG");
            return "redirect:" + returnPath;
        }

        if (BCrypt.checkpw(password, loginUser.getPasswordHash())) {
            String newToken = UUID.randomUUID().toString();
            loginUser.setToken(newToken);
            userDao.save(loginUser);

            Cookie newCookie = new Cookie ("user", newToken);
            newCookie.setPath("/");
            newCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(newCookie);

            String cartOne = "";
            String cartTwo = "";

            if (!cartItems.equals("empty")) {
                cartOne = cartItems;
            }

            Cart checkCart = cartDao.findByUserId(loginUser.getId());
            if (!checkCart.getCartItems().equals("")) {
                cartTwo = checkCart.getCartItems();
            }
            if (cartOne.equals(cartTwo)) {
            } else if (cartOne.equals("")) {
                Cookie cookie = new Cookie("cartItems", cartTwo);
                response.addCookie(cookie);
            } else if (cartTwo.equals("")) {
                checkCart.setCartItems(cartOne);
                cartDao.save(checkCart);
            } else {
                HashMap<Integer, Integer> cart = new HashMap<>();
                cart = Data.addCartList(cart, cartOne);
                cart = Data.addCartList(cart, cartTwo);

                String newCartItems = Data.cartHashToString(cart);

                Cookie cookie = new Cookie("cartItems", newCartItems);
                cookie.setPath("/");
                response.addCookie(cookie);

                checkCart.setCartItems(newCartItems);
                cartDao.save(checkCart);
            }
        } else {
            model.addAttribute("passwordError", "wrong password");
            model.addAttribute("loginForm", "LOG");
            model.addAttribute("emailPH", email);
            return "redirect:" + returnPath;
        }


        return "redirect:" + returnPath;
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
