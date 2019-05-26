package com.brokeshirts.ecom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.functions.*;

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
    private CustomersDao customersDao;

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


//// DISPLAY FORMS

    // ORDERS
    @RequestMapping(value="")
    public String adminOrders(Model model) {

        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("title","ADMIN");
        model.addAttribute("adminMenu", "orders");
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "admin/index";
    }

    // CATEGORIES
    @RequestMapping(value="categories")
    public String adminCategories(Model model) {

        model.addAttribute("categories", Menus.sortCatAdmin(categoriesDao));
        model.addAttribute("categoryTypeCount", Menus.catTypeCnt(categoriesDao, typesDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("title", "ADMIN");
        model.addAttribute("types", Menus.sortTypesAdmin(categoriesDao, typesDao));
        model.addAttribute("adminMenu", "categories");

        return "admin/categories";
    }

    // ARCHIVE
    @RequestMapping(value="archive")
    public String adminArchive(Model model) {

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("categories", categoriesDao.findAll());
        model.addAttribute("types", typesDao.findAll());
        model.addAttribute("sizes", sizesDao.findAll());
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "admin/archive";
    }

    // SIZES
    @RequestMapping(value="sizes")
    public String adminSizes(Model model) {

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems", Menus.sortCat((categoriesDao)));
        model.addAttribute("sizes", Menus.sortSizes(sizesDao));
        model.addAttribute("adminMenu", "sizes");
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "admin/sizes";
    }


//// SORTING FORMS

    // MOVE CATEGORY UP OR DOWN LIST
    @RequestMapping(value="categories/move{direction}/{sortId}")
    public String moveCat(@PathVariable String direction, @PathVariable int sortId) {

        Admin.moveCat(sortId, direction, categoriesDao);
        return "redirect:/admin/categories";
    }

    // MOVE SUBCATEGORY(TYPE) UP OR DOWN LIST
    @RequestMapping(value="categories/{categoryId}/{sortId}/move{direction}")
    public String moveType(@PathVariable int categoryId, @PathVariable int sortId,@PathVariable String direction){

        Admin.moveType(sortId, direction, typesDao, categoryId);
        return "redirect:/admin/categories";
    }

    // MOVE SIZE UP OR DOWN LIST
    @RequestMapping(value="sizes/move{direction}/{sortId}")
    public String moveSize(@PathVariable String direction, @PathVariable int sortId) {

        Admin.moveSize(sortId, direction, sizesDao);
        return "redirect:/admin/sizes";
    }


//// (UN)HIDE FROM CUSTOMERS AND ARCHIVING

    // HIDE CATEGORY FROM CUSTOMERS
    @RequestMapping(value="categories/hidden/{id}/{choice}")
    public String changeCatHiddenStatus(@PathVariable int id, @PathVariable String choice) {

        Admin.hideCat(id, choice, categoriesDao);
        return "redirect:/admin/categories";
    }

    // HIDE SUBCATEGORY(TYPE) FROM CUSTOMERS
    @RequestMapping(value="categories/{typeId}/hidden/{choice}")
    public String changeTypeHiddenStatus(@PathVariable int typeId, @PathVariable String choice) {

        Admin.hideType(typeId, choice, typesDao);
        return "redirect:/admin/categories";
    }

    // HIDE SIZE FROM CUSTOMERS
    @RequestMapping(value="sizes/hidden/{id}/{choice}")
    public String changeSizeHiddenStatus(@PathVariable int id, @PathVariable String choice) {

        Admin.hideSize(id, choice, sizesDao);
        return "redirect:/admin/sizes";
    }

    // ARCHIVE CATEGORY
    @RequestMapping(value="categories/archive/{categoryId}")
    public String archiveCat(@PathVariable int categoryId) {

        Admin.archiveCat(categoryId, categoriesDao, typesDao);
        return "redirect:/admin/categories";
    }

    // ARCHIVE SUBCATEGORY
    @RequestMapping(value="categories/{typeId}/archive/{categoryId}")
    public String archiveType(@PathVariable int typeId, @PathVariable int categoryId) {

        Admin.archiveType(typeId, categoryId, typesDao);
        return "redirect:/admin/categories";
    }

    // ARCHIVE SIZE
    @RequestMapping(value="sizes/archive/{id}")
    public String archiveSize(@PathVariable int id) {

        Admin.archiveSize(id, sizesDao);
        return "redirect:/admin/sizes";
    }

    // REACTIVATE CATEGORY
    @RequestMapping(value="categories/reactivate/cat/{id}")
    public String reactivateCat(@PathVariable int id) {

        Admin.reactivateCat(id, categoriesDao, typesDao);
        return "redirect:/admin/archive";
    }

    // REACTIVATE SUB-CATEGORY
    @RequestMapping(value="categories/reactivate/type/{id}")
    public String reactivateType(@PathVariable int id) {

        Admin.reactivateType(id, typesDao);
        return "redirect:/admin/archive";
    }

    // REACTIVATE SIZE
    @RequestMapping(value="sizes/reactivate/{id}")
    public String reactivateSize(@PathVariable int id) {

        Admin.reactivateSize(id, sizesDao);
        return "redirect:/admin/archive";
    }
}
