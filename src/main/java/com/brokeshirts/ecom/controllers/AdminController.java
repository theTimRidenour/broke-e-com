package com.brokeshirts.ecom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;

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


    @RequestMapping(value="")
    public String adminIndex(Model model) {

        model.addAttribute("title","ADMIN");
        model.addAttribute("menuItems", categoriesDao.findAll());

        return "admin/index";

    }

    @RequestMapping(value="{menuOption}")
    public String showAdminForm(Model model, @PathVariable String menuOption) {

        if (menuOption.equals("addresses")) {
            model.addAttribute("addresses", addressesDao.findAll());
        } else if (menuOption.equals("categories")) {
            model.addAttribute("categories", categoriesDao.findAll());
        } else if (menuOption.equals("colors")) {
            model.addAttribute("colors", colorsDao.findAll());
        } else if (menuOption.equals("customers")) {
            model.addAttribute("customers", customersDao.findAll());
        } else if (menuOption.equals("inventory")) {
            model.addAttribute("inventory", inventoryDao.findAll());
        } else if (menuOption.equals("photos")) {
            model.addAttribute("photos", photosDao.findAll());
        } else if (menuOption.equals("products")) {
            model.addAttribute("products", productsDao.findAll());
        } else if (menuOption.equals("sizes")) {
            model.addAttribute("sizes", sizesDao.findAll());
        } else if (menuOption.equals("types")) {
            model.addAttribute("types", typesDao.findAll());
        }

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems",categoriesDao.findAll());

        return "admin/" + menuOption;
    }

}
