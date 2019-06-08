package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.functions.Store;
import com.brokeshirts.ecom.models.data.CategoriesDao;
import com.brokeshirts.ecom.models.data.PhotosDao;
import com.brokeshirts.ecom.models.data.ProductsDao;
import com.brokeshirts.ecom.models.data.TypesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.brokeshirts.ecom.functions.Data.replaceUnderscore;

@Controller
@RequestMapping(value="store")
public class StoreController {

    @Autowired
    CategoriesDao categoriesDao;

    @Autowired
    TypesDao typesDao;

    @Autowired
    ProductsDao productsDao;

    @Autowired
    PhotosDao photosDao;

//// DISPLAY FORMS

    // DISPLAY PRODUCTS IN SINGLE CATEGORY
    @RequestMapping(value="{categoryName}", method = RequestMethod.GET)
    public String showCat(@PathVariable String categoryName, Model model) {

        model.addAttribute("title", categoryName);
        model.addAttribute("types", Store.allCatTypes(replaceUnderscore(categoryName), typesDao, categoriesDao));
        model.addAttribute("products", Store.limitedProductListByType(replaceUnderscore(categoryName), typesDao, categoriesDao, productsDao, photosDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "store/category";
    }

    // DISPLAY PRODUCTS IN SINGLE SUB-CATEGORY
    @RequestMapping(value="{categoryName}/{typeName}", method = RequestMethod.GET)
    public String showType(@PathVariable String categoryName,@PathVariable String typeName, Model model) {

        model.addAttribute("title", replaceUnderscore(categoryName) + " : " + replaceUnderscore(typeName));
        model.addAttribute("type", Store.oneTypeByName(replaceUnderscore(typeName), typesDao));
        model.addAttribute("products", Store.oneCatProducts(replaceUnderscore(typeName), productsDao, typesDao, photosDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("subMenuItems", Menus.sortTypes(categoriesDao, typesDao));

        return "store/type";
    }
}
