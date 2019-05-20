package com.brokeshirts.ecom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.models.*;
import java.util.ArrayList;

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

    @RequestMapping(value= "")
    public String index(Model model) {

        ArrayList<Products> allProducts = new ArrayList<>();
        ArrayList<Products> featured = new ArrayList<>();
        int counter = 0;

        for (Products product : productsDao.findAll()) {
            allProducts.add(product);
        }

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


        ArrayList<Products> shirts = new ArrayList<>();
        ArrayList<Types> shirtTypes = new ArrayList<>();
        counter = 4;

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == 1) {
                shirtTypes.add(type);
            }
        }

        for (Products product : allProducts) {
            for (Types type : shirtTypes) {
                if (product.getTypeId() == type.getId()) {
                    if (counter > 0) {
                        shirts.add(product);
                        counter--;
                    }
                }
            }
        }

        model.addAttribute("title", "Broke Shirts");
        model.addAttribute("featured", featured);
        model.addAttribute("types", typesDao.findAll());
        model.addAttribute("products", productsDao.findAll());
        model.addAttribute("menuItems", categoriesDao.findAll());

        return "index";

    }

    @RequestMapping(value="terms")
    public String showTermsAndConditions(Model model) {

        model.addAttribute("title", "Terms and Conditions");
        model.addAttribute("menuItems", categoriesDao.findAll());


        return "terms";
    }

}
