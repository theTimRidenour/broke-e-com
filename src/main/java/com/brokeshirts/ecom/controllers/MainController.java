package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Data;
import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.Cart;
import com.brokeshirts.ecom.models.User;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class MainController {

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
    private UserDao userDao;

//// DISPLAY FORMS

    // DISPLAY LANDING PAGE (INDEX)
    @RequestMapping(value= "")
    public String index(Model model, @CookieValue(value = "user", defaultValue = "guest") String user, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", Store.featuredProducts(productsDao, photosDao));
        model.addAttribute("types", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("products", Store.revProdsByCat(categoriesDao, productsDao, photosDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
        model.addAttribute("returnPath", "/");
        model.addAttribute("username", Data.userHeaderName(user, userDao));

        return "index";
    }

    // DISPLAY TERMS AND CONDITIONS
    @RequestMapping(value="terms")
    public String showTerms(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        model.addAttribute("title", "Terms and Conditions");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
        model.addAttribute("returnPath", "/terms");
        model.addAttribute("username", Data.userHeaderName(user, userDao));

        return "terms";
    }

//// USER LOGIN, REGISTRATION, AND LOG OUT

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
            if (!checkCart.getCartItems().equals("empty")) {
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

    // USER LOGOUT
    @RequestMapping(value = "logout")
    public String logout(@CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {
        Cookie cookie = new Cookie("user", "guest");
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

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
                newCart.setCartItems("empty");
                cartDao.save(newCart);
            } else {
                checkCart.setCartItems("");
                cartDao.save(checkCart);
            }
        }

        return "redirect:" + returnPath;
    }
}
