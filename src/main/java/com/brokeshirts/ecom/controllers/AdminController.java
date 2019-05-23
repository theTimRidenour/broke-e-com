package com.brokeshirts.ecom.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;

import java.util.ArrayList;

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

        ArrayList<Categories> unsortedCat = new ArrayList<>();
        ArrayList<Categories> sortedCat = new ArrayList<>();
        ArrayList<Categories> sortedCatMain = new ArrayList<>();

        int sortId = 1;

        for (Categories cat : categoriesDao.findAll()) {
            unsortedCat.add(cat);
        }

        while (sortId <= unsortedCat.size()) {
            for (Categories cat : unsortedCat) {
                if (cat.getSortId() == sortId) {
                    if (cat.getHidden().equals("no")) {
                        sortedCat.add(cat);
                    }
                    sortId++;
                }
            }
        }

        model.addAttribute("title","ADMIN");
        model.addAttribute("menuItems", sortedCat);

        return "admin/index";

    }

    @RequestMapping(value="{menuOption}")
    public String showAdminForm(Model model, @PathVariable String menuOption) {

        ArrayList<Categories> unsortedCat = new ArrayList<>();
        ArrayList<Categories> sortedCat = new ArrayList<>();
        ArrayList<Categories> sortedCatMain = new ArrayList<>();

        int sortId = 1;

        for (Categories cat : categoriesDao.findAll()) {
            unsortedCat.add(cat);
        }

        while (sortId <= unsortedCat.size()) {
            for (Categories cat : unsortedCat) {
                if (cat.getSortId() == sortId) {
                    if (cat.getArchive().equals("no")) {
                        sortedCatMain.add(cat);
                    }
                    if (cat.getHidden().equals("no")) {
                        sortedCat.add(cat);
                    }
                    sortId++;
                }
            }
        }

        if (menuOption.equals("addresses")) {
            model.addAttribute("addresses", addressesDao.findAll());
        } else if (menuOption.equals("categories")) {
            model.addAttribute("categories", sortedCatMain);
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
        model.addAttribute("menuItems", sortedCat);

        return "admin/" + menuOption;
    }

    @RequestMapping(value="{menuOption}/moveup/{sortId}")
    public String moveUp(Model model, @PathVariable String menuOption, @PathVariable int sortId) {

        if (menuOption.equals("categories")) {
            for (Categories cat : categoriesDao.findAll()) {
                if (cat.getSortId() == sortId) {
                    cat.setSortId(sortId - 1);
                    categoriesDao.save(cat);
                } else if (cat.getSortId() == sortId - 1) {
                    cat.setSortId(sortId);
                    categoriesDao.save(cat);
                }
            }
        }

        return "redirect:/admin/" + menuOption;
    }

    @RequestMapping(value="{menuOption}/hidden/{id}/{choice}")
    public String changeHiddenStatus(Model model, @PathVariable String menuOption, @PathVariable int id, @PathVariable String choice) {

        Categories hiddenCat = categoriesDao.findOne(id);
        hiddenCat.setHidden(choice);
        categoriesDao.save(hiddenCat);

        return "redirect:/admin/" + menuOption;

    }

    @RequestMapping(value="{menuOption}/movedown/{sortId}")
    public String moveDown(Model model, @PathVariable String menuOption, @PathVariable int sortId) {

        if (menuOption.equals("categories")) {
            for (Categories cat : categoriesDao.findAll()) {
                if (cat.getSortId() == sortId) {
                    cat.setSortId(sortId + 1);
                    categoriesDao.save(cat);
                } else if (cat.getSortId() == sortId + 1) {
                    cat.setSortId(sortId);
                    categoriesDao.save(cat);
                }
            }
        }

        return "redirect:/admin/" + menuOption;
    }

}
