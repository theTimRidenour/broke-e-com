package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.data.CategoriesDao;
import com.brokeshirts.ecom.models.data.ProductsDao;
import com.brokeshirts.ecom.models.data.TypesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="store")
public class StoreController {

    @Autowired
    CategoriesDao categoriesDao;

    @Autowired
    TypesDao typesDao;

    @Autowired
    ProductsDao productsDao;

//// DISPLAY FORMS

    // DISPLAY PRODUCTS IN SINGLE CATEGORY
    @RequestMapping(value="{categoryName}", method = RequestMethod.GET)
    public String showCat(@PathVariable String categoryName, Model model) {

        model.addAttribute("title", categoryName);
        model.addAttribute("types", Store.allCatTypes(categoryName, typesDao, categoriesDao));
        model.addAttribute("products", Store.limitedProductListByType(categoryName, typesDao, categoriesDao, productsDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "store/category";
    }

    // DISPLAY PRODUCTS IN SINGLE SUB-CATEGORY
    @RequestMapping(value="{categoryName}/{typeName}", method = RequestMethod.GET)
    public String showType(@PathVariable String categoryName,@PathVariable String typeName, Model model) {

        model.addAttribute("title", categoryName + " : " + typeName);
        model.addAttribute("type", Store.oneTypeByName(typeName, typesDao));
        model.addAttribute("products", Store.oneCatProducts(typeName, productsDao, typesDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "store/type";
    }
}
