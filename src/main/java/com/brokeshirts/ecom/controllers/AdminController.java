package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Admin;
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


//// DISPLAY FORMS

    // ARCHIVE
    @RequestMapping(value="archive")
    public String adminArchive(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

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

        return "admin/archive";
    }

    // CATEGORIES
    @RequestMapping(value="categories")
    public String adminCategories(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

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

        return "admin/categories";
    }

    // COLORS
    @RequestMapping(value="colors")
    public String adminColors(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("adminMenu", "colors");
        model.addAttribute("colors", Menus.sortColorsAdmin(colorsDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));

        return "admin/colors";
    }

    // ORDERS
    @RequestMapping(value="")
    public String adminOrders(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("title","ADMIN");
        model.addAttribute("adminMenu", "orders");
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));

        return "admin/index";
    }

    // PRODUCTS
    @RequestMapping(value="products")
    public String adminProducts(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

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

        return "admin/products";
    }

    // SIZES
    @RequestMapping(value="sizes")
    public String adminSizes(Model model, @CookieValue(value = "cartItems", defaultValue = "empty") String cartItems, HttpServletResponse response) {

        String userRole = "USER";

        if (!userRole.equals("ADMIN")) {
            return "redirect:/";
        }

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("sizes", Menus.sortSizes(sizesDao));
        model.addAttribute("adminMenu", "sizes");
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("cartCnt", Store.cartItemCnt(cartItems));

        return "admin/sizes";
    }


//// SORTING FORMS

    // MOVE CATEGORY UP OR DOWN LIST
    @RequestMapping(value="categories/move{direction}/{sortId}")
    public String moveCat(@PathVariable String direction, @PathVariable int sortId) {

        Admin.moveCat(sortId, direction, categoriesDao);
        return "redirect:/admin/categories";
    }

    // MOVE SUB-CATEGORY(TYPE) UP OR DOWN LIST
    @RequestMapping(value="categories/type/{categoryId}/{sortId}/move{direction}")
    public String moveType(@PathVariable int categoryId, @PathVariable int sortId,@PathVariable String direction){

        Admin.moveType(sortId, direction, typesDao, categoryId);
        return "redirect:/admin/categories";
    }

    // MOVE STYLE UP OR DOWN LIST
    @RequestMapping(value="categories/style/{categoryId}/{sortId}/move{direction}")
    public String moveStyle(@PathVariable int categoryId, @PathVariable int sortId, @PathVariable String direction) {

        Admin.moveStyle(sortId, direction, stylesDao, categoryId);
        return "redirect:/admin/categories";
    }

    // MOVE SIZE UP OR DOWN LIST
    @RequestMapping(value="sizes/move{direction}/{sortId}")
    public String moveSize(@PathVariable String direction, @PathVariable int sortId) {

        Admin.moveSize(sortId, direction, sizesDao);
        return "redirect:/admin/sizes";
    }


//// (UN)HIDE FROM CUSTOMERS

    // HIDE CATEGORY FROM CUSTOMERS
    @RequestMapping(value="categories/hidden/{id}/{choice}")
    public String changeCatHiddenStatus(@PathVariable int id, @PathVariable String choice) {

        Admin.hideCat(id, choice, categoriesDao);
        return "redirect:/admin/categories";
    }

    // HIDE COLOR FROM CUSTOMERS
    @RequestMapping(value="colors/hidden/{id}/{choice}")
    public String changeColorHiddenStatus(@PathVariable int id, @PathVariable String choice) {

        Admin.hideColor(id, choice, colorsDao);
        return "redirect:/admin/colors";
    }

    // HIDE SUB-CATEGORY(TYPE) FROM CUSTOMERS
    @RequestMapping(value="categories/type/{typeId}/hidden/{choice}")
    public String changeTypeHiddenStatus(@PathVariable int typeId, @PathVariable String choice) {

        Admin.hideType(typeId, choice, typesDao);
        return "redirect:/admin/categories";
    }

    // HIDE PRODUCT FROM CUSTOMERS
    @RequestMapping(value="products/hidden/{productId}/{choice}")
    public String changeProductHiddenStatus(@PathVariable int productId, @PathVariable String choice) {

        Admin.hideProduct(productId, productsDao, choice);
        return "redirect:/admin/products";
    }

    // HIDE STYLE FROM CUSTOMERS
    @RequestMapping(value="categories/style/{styleId}/hidden/{choice}")
    public String changeStyleHiddenStatus(@PathVariable int styleId, @PathVariable String choice) {

        Admin.hideStyle(styleId, choice, stylesDao);
        return "redirect:/admin/categories";
    }

    // HIDE SIZE FROM CUSTOMERS
    @RequestMapping(value="sizes/hidden/{id}/{choice}")
    public String changeSizeHiddenStatus(@PathVariable int id, @PathVariable String choice) {

        Admin.hideSize(id, choice, sizesDao);
        return "redirect:/admin/sizes";
    }


//// ARCHIVE

    // ARCHIVE CATEGORY
    @RequestMapping(value="categories/archive/{categoryId}")
    public String archiveCat(@PathVariable int categoryId) {

        Admin.archiveCat(categoryId, categoriesDao, typesDao);
        return "redirect:/admin/categories";
    }

    // ARCHIVE COLOR
    @RequestMapping(value="colors/archive/{colorId}")
    public String archiveColor(@PathVariable int colorId) {

        Admin.archiveColor(colorId, colorsDao);
        return "redirect:/admin/colors";
    }

    // ARCHIVE PRODUCT
    @RequestMapping(value="products/archive/{productId}")
    public String archiveProduct(@PathVariable int productId) {

        Admin.archiveProduct(productId, productsDao);
        return "redirect:/admin/products";
    }

    // ARCHIVE SUB-CATEGORY
    @RequestMapping(value="categories/type/{typeId}/archive/{categoryId}")
    public String archiveType(@PathVariable int typeId, @PathVariable int categoryId) {

        Admin.archiveType(typeId, categoryId, typesDao);
        return "redirect:/admin/categories";
    }

    // ARCHIVE STYLE
    @RequestMapping(value="categories/style/{styleId}/archive/{categoryId}")
    public String archiveStyle(@PathVariable int styleId, @PathVariable int categoryId) {

        Admin.archiveStyle(styleId, categoryId, stylesDao);
        return "redirect:/admin/categories";
    }

    // ARCHIVE SIZE
    @RequestMapping(value="sizes/archive/{id}")
    public String archiveSize(@PathVariable int id) {

        Admin.archiveSize(id, sizesDao);
        return "redirect:/admin/sizes";
    }


//// REACTIVATE

    // REACTIVATE CATEGORY
    @RequestMapping(value="categories/reactivate/cat/{id}")
    public String reactivateCat(@PathVariable int id) {

        Admin.reactivateCat(id, categoriesDao, typesDao);
        return "redirect:/admin/archive";
    }

    // REACTIVATE COLOR
    @RequestMapping(value="colors/reactivate/{id}")
    public String reactivateColor(@PathVariable int id) {

        Admin.reactivateColor(id, colorsDao);
        return "redirect:/admin/archive";
    }

    // REACTIVATE PRODUCT
    @RequestMapping(value="products/reactivate/{id}")
    public String reactivateProduct(@PathVariable int id) {

        Admin.reactivateProduct(id, productsDao);
        return "redirect:/admin/archive";
    }

    // REACTIVATE SUB-CATEGORY
    @RequestMapping(value="categories/reactivate/type/{id}")
    public String reactivateType(@PathVariable int id) {

        Admin.reactivateType(id, typesDao);
        return "redirect:/admin/archive";
    }

    // REACTIVATE STYLE
    @RequestMapping(value="categories/reactivate/style/{id}")
    public String reactivateStyle(@PathVariable int id) {

        Admin.reactivateStyle(id, stylesDao);
        return "redirect:/admin/archive";
    }

    // REACTIVATE SIZE
    @RequestMapping(value="sizes/reactivate/{id}")
    public String reactivateSize(@PathVariable int id) {

        Admin.reactivateSize(id, sizesDao);
        return "redirect:/admin/archive";
    }
}