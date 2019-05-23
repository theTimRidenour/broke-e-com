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
import java.util.HashMap;

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


  // DISPLAY FORMS

    // ORDERS
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

    // OTHER ADMIN CATEGORY FORMS
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

            HashMap<Integer, Integer> categoryTypeCount = new HashMap<>();
            int count = 0;
            int maxTypeCnt = 0;

            for (Categories cat : categoriesDao.findAll()) {
                count = 0;
                for (Types type : typesDao.findAll()) {
                    if (type.getCategoryId() == cat.getId()) {
                        count++;
                    }
                }
                categoryTypeCount.put(cat.getId(), count);
                if (maxTypeCnt < count) {
                    maxTypeCnt = count;
                }
            }

            ArrayList<Types> unsortedTypes = new ArrayList<>();
            ArrayList<Types> sortedTypes = new ArrayList<>();
            ArrayList<Types> sortedTypesMain = new ArrayList<>();

            sortId = 1;

            for (Types type : typesDao.findAll()) {
                unsortedTypes.add(type);
            }

            while (sortId <= maxTypeCnt) {
                for (Types type : unsortedTypes) {
                    if (type.getSortId() == sortId) {
                        if (type.getArchive().equals("no")) {
                            sortedTypesMain.add(type);
                        }
                        if (type.getHidden().equals("no")) {
                            sortedTypes.add(type);
                        }
                    }
                }
                sortId++;
            }

            model.addAttribute("categories", sortedCatMain);
            model.addAttribute("types", sortedTypesMain);
            model.addAttribute("categoryTypeCount", categoryTypeCount);
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
        }

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems", sortedCat);

        return "admin/" + menuOption;
    }


  // SORTING FORMS

    // MOVE CATEGORY UP LIST
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

    //MOVE CATEGORY DOWN LIST
    @RequestMapping(value="{menuOption}/movedown/{sortId}")
    public String moveDown(@PathVariable String menuOption, @PathVariable int sortId) {

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

    //MOVE SUBCATEGORY UP LIST
    @RequestMapping(value="{menuOption}/{typeOption}/{sortId}/moveup")
    public String moveSubcategoryUp(@PathVariable String menuOption, @PathVariable int typeOption, @PathVariable int sortId){

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == typeOption) {
                if (type.getSortId() == sortId) {
                    type.setSortId(sortId - 1);
                    typesDao.save(type);
                } else if (type.getSortId() == sortId - 1) {
                    type.setSortId(sortId);
                    typesDao.save(type);
                }
            }
        }

        return "redirect:/admin/" + menuOption;

    }

    //MOVE SUBCATEGORY DOWN LIST
    @RequestMapping(value="{menuOption}/{typeOption}/{sortId}/movedown")
    public String moveSubcategoryDown(@PathVariable String menuOption, @PathVariable int typeOption, @PathVariable int sortId){

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == typeOption) {
                if (type.getSortId() == sortId) {
                    type.setSortId(sortId + 1);
                    typesDao.save(type);
                } else if (type.getSortId() == sortId + 1) {
                    type.setSortId(sortId);
                    typesDao.save(type);
                }
            }
        }

        return "redirect:/admin/" + menuOption;

    }


  // HIDING AND ARCHIVING

    // HIDE CATEGORY FROM CUSTOMERS
    @RequestMapping(value="{menuOption}/hidden/{id}/{choice}")
    public String changeHiddenStatus(@PathVariable String menuOption, @PathVariable int id, @PathVariable String choice) {

        Categories hiddenCat = categoriesDao.findOne(id);
        hiddenCat.setHidden(choice);
        categoriesDao.save(hiddenCat);

        return "redirect:/admin/" + menuOption;

    }

    // HIDE SUBCATEGORY FROM CUSTOMERS
    @RequestMapping(value="{menuOption}/{typeId}/hidden/{choice}")
    public String changeSubcategoryHiddenStatus(@PathVariable String menuOption, @PathVariable int typeId, @PathVariable String choice) {

        Types hiddenType = typesDao.findOne(typeId);
        hiddenType.setHidden(choice);
        typesDao.save(hiddenType);

        return "redirect:/admin/" + menuOption;

    }

}
