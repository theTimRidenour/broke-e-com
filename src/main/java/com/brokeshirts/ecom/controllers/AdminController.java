package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Admin;
import com.brokeshirts.ecom.functions.Data;
import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="admin")
public class AdminController {

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

    @Autowired
    private OrdersDao ordersDao;


//// DISPLAY FORMS

    // ARCHIVE
    @RequestMapping(value="archive")
    public String adminArchive(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            model.addAttribute("title", "ADMIN");
            model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
            model.addAttribute("categories", categoriesDao.findAll());
            model.addAttribute("types", typesDao.findAll());
            model.addAttribute("styles", stylesDao.findAll());
            model.addAttribute("sizes", sizesDao.findAll());
            model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
            model.addAttribute("colors", colorsDao.findAll());
            model.addAttribute("products", productsDao.findAll());
            model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
            model.addAttribute("username", Data.userHeaderName(user, userDao));

            return "admin/archive";
        }

        return "redirect:/";
    }

    // CATEGORIES
    @RequestMapping(value="categories")
    public String adminCategories(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            model.addAttribute("categories", Menus.sortCatAdmin(categoriesDao));
            model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
            model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
            model.addAttribute("title", "ADMIN");
            model.addAttribute("types", Menus.sortTypesAdmin(categoriesDao, typesDao));
            model.addAttribute("typesCnt", Menus.catTypeCnt(categoriesDao, typesDao));
            model.addAttribute("styles", Menus.sortStylesAdmin(categoriesDao, stylesDao));
            model.addAttribute("stylesCnt", Menus.catStyleCnt(categoriesDao, stylesDao));
            model.addAttribute("adminMenu", "categories");
            model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
            model.addAttribute("username", Data.userHeaderName(user, userDao));

            return "admin/categories";
        }

        return "redirect:/";
    }

    // COLORS
    @RequestMapping(value="colors")
    public String adminColors(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            model.addAttribute("title", "ADMIN");
            model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
            model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
            model.addAttribute("adminMenu", "colors");
            model.addAttribute("colors", Menus.sortColorsAdmin(colorsDao));
            model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
            model.addAttribute("username", Data.userHeaderName(user, userDao));

            return "admin/colors";
        }

        return "redirect:/";
    }

    // ORDERS
    @RequestMapping(value="")
    public String adminOrders(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
            model.addAttribute("title", "ADMIN");
            model.addAttribute("adminMenu", "orders");
            model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
            model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
            model.addAttribute("username", Data.userHeaderName(user, userDao));
            model.addAttribute("orders", ordersDao.findAll());

            return "admin/index";
        }

        return "redirect:/";
    }

    // PRODUCTS
    @RequestMapping(value="products")
    public String adminProducts(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            model.addAttribute("title", "ADMIN");
            model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
            model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
            model.addAttribute("adminMenu", "products");
            model.addAttribute("products", Menus.sortProductsAdmin(productsDao, categoriesDao, typesDao));
            model.addAttribute("productsInventoryTotals", Menus.productInventoryCnt(productsDao, inventoryDao));
            model.addAttribute("inventory", Menus.sortInventoryAdmin(inventoryDao, productsDao, categoriesDao, typesDao, sizesDao, colorsDao));
            model.addAttribute("categories", Menus.sortCatAdmin(categoriesDao));
            model.addAttribute("types", Menus.sortTypesAdmin(categoriesDao, typesDao));
            model.addAttribute("styles", Menus.sortStylesAdmin(categoriesDao, stylesDao));
            model.addAttribute("colors", Menus.sortColorsAdmin(colorsDao));
            model.addAttribute("sizes", Menus.sortSizes(sizesDao));
            model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
            model.addAttribute("username", Data.userHeaderName(user, userDao));

            return "admin/products";
        }

        return "redirect:/";
    }

    // SIZES
    @RequestMapping(value="sizes")
    public String adminSizes(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            model.addAttribute("title", "ADMIN");
            model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
            model.addAttribute("sizes", Menus.sortSizes(sizesDao));
            model.addAttribute("adminMenu", "sizes");
            model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
            model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));
            model.addAttribute("username", Data.userHeaderName(user, userDao));

            return "admin/sizes";
        }

        return "redirect:/";
    }


//// SORTING FORMS

    // MOVE CATEGORY UP OR DOWN LIST
    @RequestMapping(value="categories/move{direction}/{sortId}")
    public String moveCat(@PathVariable String direction, @PathVariable int sortId, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.moveCat(sortId, direction, categoriesDao);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // MOVE SUB-CATEGORY(TYPE) UP OR DOWN LIST
    @RequestMapping(value="categories/type/{categoryId}/{sortId}/move{direction}")
    public String moveType(@PathVariable int categoryId, @PathVariable int sortId,@PathVariable String direction, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response){

        if (Admin.adminCheck(user, userDao)) {
            Admin.moveType(sortId, direction, typesDao, categoryId);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // MOVE STYLE UP OR DOWN LIST
    @RequestMapping(value="categories/style/{categoryId}/{sortId}/move{direction}")
    public String moveStyle(@PathVariable int categoryId, @PathVariable int sortId, @PathVariable String direction, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.moveStyle(sortId, direction, stylesDao, categoryId);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // MOVE SIZE UP OR DOWN LIST
    @RequestMapping(value="sizes/move{direction}/{sortId}")
    public String moveSize(@PathVariable String direction, @PathVariable int sortId, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.moveSize(sortId, direction, sizesDao);
            return "redirect:/admin/sizes";
        }

        return "redirect:/";
    }


//// (UN)HIDE FROM CUSTOMERS

    // HIDE CATEGORY FROM CUSTOMERS
    @RequestMapping(value="categories/hidden/{id}/{choice}")
    public String changeCatHiddenStatus(@PathVariable int id, @PathVariable String choice, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.hideCat(id, choice, categoriesDao);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // HIDE COLOR FROM CUSTOMERS
    @RequestMapping(value="colors/hidden/{id}/{choice}")
    public String changeColorHiddenStatus(@PathVariable int id, @PathVariable String choice, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.hideColor(id, choice, colorsDao);
            return "redirect:/admin/colors";
        }

        return "redirect:/";
    }

    // HIDE SUB-CATEGORY(TYPE) FROM CUSTOMERS
    @RequestMapping(value="categories/type/{typeId}/hidden/{choice}")
    public String changeTypeHiddenStatus(@PathVariable int typeId, @PathVariable String choice, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.hideType(typeId, choice, typesDao);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // HIDE PRODUCT FROM CUSTOMERS
    @RequestMapping(value="products/hidden/{productId}/{choice}")
    public String changeProductHiddenStatus(@PathVariable int productId, @PathVariable String choice, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.hideProduct(productId, productsDao, choice);
            return "redirect:/admin/products";
        }

        return "redirect:/";
    }

    // HIDE STYLE FROM CUSTOMERS
    @RequestMapping(value="categories/style/{styleId}/hidden/{choice}")
    public String changeStyleHiddenStatus(@PathVariable int styleId, @PathVariable String choice, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.hideStyle(styleId, choice, stylesDao);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // HIDE SIZE FROM CUSTOMERS
    @RequestMapping(value="sizes/hidden/{id}/{choice}")
    public String changeSizeHiddenStatus(@PathVariable int id, @PathVariable String choice, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.hideSize(id, choice, sizesDao);
            return "redirect:/admin/sizes";
        }

        return "redirect:/";
    }


//// ARCHIVE

    // ARCHIVE CATEGORY
    @RequestMapping(value="categories/archive/{categoryId}")
    public String archiveCat(@PathVariable int categoryId, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.archiveCat(categoryId, categoriesDao, typesDao);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // ARCHIVE COLOR
    @RequestMapping(value="colors/archive/{colorId}")
    public String archiveColor(@PathVariable int colorId, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.archiveColor(colorId, colorsDao);
            return "redirect:/admin/colors";
        }

        return "redirect:/";
    }

    // ARCHIVE PRODUCT
    @RequestMapping(value="products/archive/{productId}")
    public String archiveProduct(@PathVariable int productId, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.archiveProduct(productId, productsDao);
            return "redirect:/admin/products";
        }

        return "redirect:/";
    }

    // ARCHIVE SUB-CATEGORY
    @RequestMapping(value="categories/type/{typeId}/archive/{categoryId}")
    public String archiveType(@PathVariable int typeId, @PathVariable int categoryId, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.archiveType(typeId, categoryId, typesDao);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // ARCHIVE STYLE
    @RequestMapping(value="categories/style/{styleId}/archive/{categoryId}")
    public String archiveStyle(@PathVariable int styleId, @PathVariable int categoryId, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.archiveStyle(styleId, categoryId, stylesDao);
            return "redirect:/admin/categories";
        }

        return "redirect:/";
    }

    // ARCHIVE SIZE
    @RequestMapping(value="sizes/archive/{id}")
    public String archiveSize(@PathVariable int id, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.archiveSize(id, sizesDao);
            return "redirect:/admin/sizes";
        }

        return "redirect:/";
    }


//// REACTIVATE

    // REACTIVATE CATEGORY
    @RequestMapping(value="categories/reactivate/cat/{id}")
    public String reactivateCat(@PathVariable int id, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.reactivateCat(id, categoriesDao, typesDao);
            return "redirect:/admin/archive";
        }

        return "redirect:/";
    }

    // REACTIVATE COLOR
    @RequestMapping(value="colors/reactivate/{id}")
    public String reactivateColor(@PathVariable int id, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.reactivateColor(id, colorsDao);
            return "redirect:/admin/archive";
        }

        return "redirect:/";
    }

    // REACTIVATE PRODUCT
    @RequestMapping(value="products/reactivate/{id}")
    public String reactivateProduct(@PathVariable int id, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.reactivateProduct(id, productsDao);
            return "redirect:/admin/archive";
        }

        return "redirect:/";
    }

    // REACTIVATE SUB-CATEGORY
    @RequestMapping(value="categories/reactivate/type/{id}")
    public String reactivateType(@PathVariable int id, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if(Admin.adminCheck(user, userDao)) {
            Admin.reactivateType(id, typesDao);
            return "redirect:/admin/archive";
        }

        return "redirect:/";
    }

    // REACTIVATE STYLE
    @RequestMapping(value="categories/reactivate/style/{id}")
    public String reactivateStyle(@PathVariable int id, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.reactivateStyle(id, stylesDao);
            return "redirect:/admin/archive";
        }

        return "redirect:/";
    }

    // REACTIVATE SIZE
    @RequestMapping(value="sizes/reactivate/{id}")
    public String reactivateSize(@PathVariable int id, @CookieValue(value = "user", defaultValue = "guest") String user, HttpServletResponse response) {

        if (Admin.adminCheck(user, userDao)) {
            Admin.reactivateSize(id, sizesDao);
            return "redirect:/admin/archive";
        }

        return "redirect:/";
    }
}