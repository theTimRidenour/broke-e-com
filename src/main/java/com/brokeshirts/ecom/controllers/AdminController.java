package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Menus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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


  // DISPLAY FORMS

    // ORDERS
    @RequestMapping(value="")
    public String adminOrders(Model model) {

        model.addAttribute("menuItems", Menus.sortedCat(categoriesDao));
        model.addAttribute("title","ADMIN");

        return "admin/index";
    }

    // CATEGORIES
    @RequestMapping(value="categories")
    public String adminCategories(Model model) {

        model.addAttribute("categories", Menus.sortedCat(categoriesDao));
        model.addAttribute("categoryTypeCount", Menus.catTypeCnt(categoriesDao, typesDao));
        model.addAttribute("menuItems", Menus.sortedCat(categoriesDao));
        model.addAttribute("title", "ADMIN");
        model.addAttribute("types", Menus.sortedTypes(categoriesDao, typesDao));

        return "admin/categories";
    }

  // SORTING FORMS

    // MOVE CATEGORY UP LIST
    @RequestMapping(value="categories/moveup/{sortId}")
    public String moveCatUp(@PathVariable int sortId) {

        for (Categories cat : categoriesDao.findAll()) {
            if (cat.getSortId() == sortId) {
                cat.setSortId(sortId - 1);
                categoriesDao.save(cat);
            } else if (cat.getSortId() == sortId - 1) {
                cat.setSortId(sortId);
                categoriesDao.save(cat);
            }
        }

        return "redirect:/admin/categories";
    }

    //MOVE CATEGORY DOWN LIST
    @RequestMapping(value="categories/movedown/{sortId}")
    public String moveCatDown(@PathVariable int sortId) {

        for (Categories cat : categoriesDao.findAll()) {
            if (cat.getSortId() == sortId) {
                cat.setSortId(sortId + 1);
                categoriesDao.save(cat);
            } else if (cat.getSortId() == sortId + 1) {
                cat.setSortId(sortId);
                categoriesDao.save(cat);
            }
        }

        return "redirect:/admin/categories";
    }

    //MOVE SUBCATEGORY(TYPE) UP LIST
    @RequestMapping(value="categories/{categoryId}/{sortId}/moveup")
    public String moveTypeUp(@PathVariable int categoryId, @PathVariable int sortId){

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == categoryId) {
                if (type.getSortId() == sortId) {
                    type.setSortId(sortId - 1);
                    typesDao.save(type);
                } else if (type.getSortId() == sortId - 1) {
                    type.setSortId(sortId);
                    typesDao.save(type);
                }
            }
        }

        return "redirect:/admin/categories";
    }

    //MOVE SUBCATEGORY(TYPE) DOWN LIST
    @RequestMapping(value="categories/{categoryId}/{sortId}/movedown")
    public String moveTypeDown(@PathVariable int categoryId, @PathVariable int sortId){

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == categoryId) {
                if (type.getSortId() == sortId) {
                    type.setSortId(sortId + 1);
                    typesDao.save(type);
                } else if (type.getSortId() == sortId + 1) {
                    type.setSortId(sortId);
                    typesDao.save(type);
                }
            }
        }

        return "redirect:/admin/categories";
    }

  // HIDING FROM CUSTOMERS AND ARCHIVING

    // HIDE CATEGORY FROM CUSTOMERS
    @RequestMapping(value="categories/hidden/{id}/{choice}")
    public String changeCatHiddenStatus(@PathVariable int id, @PathVariable String choice) {

        Categories hiddenCat = categoriesDao.findOne(id);
        hiddenCat.setHidden(choice);
        categoriesDao.save(hiddenCat);

        return "redirect:/admin/categories";
    }

    // HIDE SUBCATEGORY(TYPE) FROM CUSTOMERS
    @RequestMapping(value="categories/{typeId}/hidden/{choice}")
    public String changeTypeHiddenStatus(@PathVariable int typeId, @PathVariable String choice) {

        Types hiddenType = typesDao.findOne(typeId);
        hiddenType.setHidden(choice);
        typesDao.save(hiddenType);

        return "redirect:/admin/categories";
    }

    //ARCHIVE CATEGORY
    @RequestMapping(value="categories/archive/{categoryId}")
    public String archiveCat(@PathVariable int categoryId) {

        Categories updateCat = categoriesDao.findOne(categoryId);

        for (Categories sortCat : categoriesDao.findAll()) {
            if (sortCat.getSortId() > updateCat.getSortId()) {
                sortCat.setSortId(sortCat.getSortId() - 1);
            }
        }

        updateCat.setSortId(0);
        updateCat.setArchive("yes");
        categoriesDao.save(updateCat);

        for (Types findTypes : typesDao.findAll()) {
            if (findTypes.getCategoryId() == categoryId) {
                findTypes.setCatArchive("yes");
                typesDao.save(findTypes);
            }
        }

        return "redirect:/admin/categories";
    }

}
