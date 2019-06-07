package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // DISPLAY LANDING PAGE (INDEX)
    @RequestMapping(value= "")
    public String index(Model model) {

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", Store.featuredProducts(productsDao, photosDao));
        model.addAttribute("types", Menus.sortTypes(categoriesDao, typesDao));
        model.addAttribute("products", Store.revProdsByCat(categoriesDao, productsDao, photosDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "index";
    }

    // DISPLAY TERMS AND CONDITIONS
    @RequestMapping(value="terms")
    public String showTerms(Model model) {

        model.addAttribute("title", "Terms and Conditions");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "terms";
    }
}
