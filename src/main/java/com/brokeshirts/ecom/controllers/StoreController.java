package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brokeshirts.ecom.models.Types;
import com.brokeshirts.ecom.models.Products;
import com.brokeshirts.ecom.models.Categories;

import com.brokeshirts.ecom.models.data.CategoriesDao;
import com.brokeshirts.ecom.models.data.ProductsDao;
import com.brokeshirts.ecom.models.data.TypesDao;

import java.util.ArrayList;

@Controller
@RequestMapping(value="store")
public class StoreController {

    @Autowired
    CategoriesDao categoriesDao;

    @Autowired
    TypesDao typesDao;

    @Autowired
    ProductsDao productsDao;

// DISPLAY FORMS

    // DISPLAY PRODUCTS IN SINGLE CATEGORY
    @RequestMapping(value="{categoryName}", method = RequestMethod.GET)
    public String showCat(@PathVariable String categoryName, Model model) {

        Categories oneCategory = null;

        for (Categories cat : categoriesDao.findAll()) {
            if (cat.getName().equals(categoryName)) {
                oneCategory = cat;
            }
        }

        ArrayList<Types> categoryTypes = new ArrayList<>();

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == oneCategory.getId()) {
                categoryTypes.add(type);
            }
        }

        ArrayList<Products> allProducts = new ArrayList<>();

        int limit = 0;
        for (Types type : categoryTypes) {
            limit = 4;
            for (Products product : productsDao.findAll()) {
                if (limit > 0) {
                    if (product.getTypeId() == type.getId()) {
                        allProducts.add(product);
                        limit--;
                    }
                }
            }
        }

        model.addAttribute("title", categoryName);
        model.addAttribute("types", categoryTypes);
        model.addAttribute("products", allProducts);
        model.addAttribute("menuItems", Menus.sortedCat(categoriesDao));

        return "store/category";
    }

    // DISPLAY PRODUCTS IN SINGLE SUBCATEGORY
    @RequestMapping(value="{categoryName}/{typeName}", method = RequestMethod.GET)
    public String showType(@PathVariable String categoryName,@PathVariable String typeName, Model model) {

        Types oneType = null;

        for (Types type : typesDao.findAll()) {
            if (type.getName().equals(typeName)) {
                oneType = type;
            }
        }

        ArrayList<Products> allProducts = new ArrayList<>();

        for (Products product : productsDao.findAll()) {
            if (product.getTypeId() == oneType.getId()) {
                allProducts.add(product);
            }
        }

        model.addAttribute("title", categoryName + " : " + typeName);
        model.addAttribute("type", oneType);
        model.addAttribute("products", allProducts);
        model.addAttribute("menuItems", Menus.sortedCat(categoriesDao));

        return "store/type";
    }
}
