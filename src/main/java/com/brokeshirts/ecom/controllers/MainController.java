package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.models.*;

import java.util.ArrayList;
import java.util.Collections;

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

// DISPLAY FORMS

    // DISPLAY LANDING PAGE (INDEX)
    @RequestMapping(value= "")
    public String index(Model model) {

        ArrayList<Products> allProducts = new ArrayList<>();
        ArrayList<Products> featured = new ArrayList<>();
        ArrayList<Products> indexProducts = new ArrayList<>();
        int counter = 0;

        for (Products product : productsDao.findAll()) {
            allProducts.add(product);
        }

        Collections.reverse(allProducts);

        if (allProducts.size() < 4) {
            counter = allProducts.size();
        } else {
            counter = 4;
        }

        for (Products product : allProducts) {
            if ( counter > 0 ) {
                featured.add(product);
                counter--;
            }
        }

        for (Types type : typesDao.findAll()) {
            counter = 4;
            for (Products product : productsDao.findAll()) {
                if (counter > 0) {
                    if (product.getTypeId() == type.getId()) {
                        indexProducts.add(product);
                        counter--;
                    }
                }
            }
        }

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", featured);
        model.addAttribute("types", Menus.sortedTypes(categoriesDao, typesDao));
        model.addAttribute("products", indexProducts);
        model.addAttribute("menuItems", Menus.sortedCat(categoriesDao));

        return "index";
    }

    // DISPLAY TERMS AND CONDITIONS
    @RequestMapping(value="terms")
    public String showTerms(Model model) {

        model.addAttribute("title", "Terms and Conditions");
        model.addAttribute("menuItems", Menus.sortedCat(categoriesDao));

        return "terms";
    }
}
