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

        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("title","ADMIN");
        model.addAttribute("adminMenu", "orders");

        return "admin/index";
    }

    // CATEGORIES
    @RequestMapping(value="categories")
    public String adminCategories(Model model) {

        model.addAttribute("categories", Menus.sortCatAdmin(categoriesDao));
        model.addAttribute("categoryTypeCount", Menus.catTypeCnt(categoriesDao, typesDao));
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("title", "ADMIN");
        model.addAttribute("types", Menus.sortTypesAdmin(categoriesDao, typesDao));
        model.addAttribute("adminMenu", "categories");

        return "admin/categories";
    }

    // ARCHIVE
    @RequestMapping(value="archive")
    public String adminArchive(Model model) {

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems", Menus.sortCat(categoriesDao));
        model.addAttribute("categories", categoriesDao.findAll());
        model.addAttribute("types", typesDao.findAll());
        model.addAttribute("sizes", sizesDao.findAll());

        return "admin/archive";
    }

    // SIZES
    @RequestMapping(value="sizes")
    public String adminSizes(Model model) {

        model.addAttribute("title", "ADMIN");
        model.addAttribute("menuItems", Menus.sortCat((categoriesDao)));
        model.addAttribute("sizes", Menus.sortSizes(sizesDao));
        model.addAttribute("adminMenu", "sizes");

        return "admin/sizes";
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

    // MOVE SIZE UP LIST
    @RequestMapping(value="sizes/moveup/{sortId}")
    public String moveSizeUp(@PathVariable int sortId) {

        for (Sizes size : sizesDao.findAll()) {
            if (size.getSortId() == sortId) {
                size.setSortId(sortId - 1);
                sizesDao.save(size);
            } else if (size.getSortId() == sortId - 1) {
                size.setSortId(sortId);
                sizesDao.save(size);
            }
        }

        return "redirect:/admin/sizes";
    }

    // MOVE SIZE DOWN LIST
    @RequestMapping(value="sizes/movedown/{sortId}")
    public String moveSizeDown(@PathVariable int sortId) {

        for (Sizes size : sizesDao.findAll()) {
            if (size.getSortId() == sortId) {
                size.setSortId(sortId + 1);
                sizesDao.save(size);
            } else if (size.getSortId() == sortId + 1) {
                size.setSortId(sortId);
                sizesDao.save(size);
            }
        }

        return "redirect:/admin/sizes";
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

    // HIDE SIZE FROM CUSTOMERS
    @RequestMapping(value="sizes/hidden/{id}/{choice}")
    public String changeSizeHiddenStatus(@PathVariable int id, @PathVariable String choice) {

        Sizes hiddenSize = sizesDao.findOne(id);
        hiddenSize.setHidden(choice);
        sizesDao.save(hiddenSize);

        return "redirect:/admin/sizes";
    }

    // ARCHIVE CATEGORY
    @RequestMapping(value="categories/archive/{categoryId}")
    public String archiveCat(@PathVariable int categoryId) {

        Categories updateCat = categoriesDao.findOne(categoryId);

        for (Categories sortCat : categoriesDao.findAll()) {
            if (sortCat.getSortId() > updateCat.getSortId()) {
                sortCat.setSortId(sortCat.getSortId() - 1);
                categoriesDao.save(sortCat);
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

    // ARCHIVE SUBCATEGORY
    @RequestMapping(value="categories/{typeId}/archive/{categoryId}")
    public String archiveType(@PathVariable int typeId, @PathVariable int categoryId) {

        Types updateType = typesDao.findOne(typeId);

        for (Types sortType : typesDao.findAll()) {
            if (sortType.getCategoryId() == categoryId) {
                if (sortType.getSortId() > updateType.getSortId()) {
                    sortType.setSortId(sortType.getSortId() - 1);
                    typesDao.save(sortType);
                }
            }
        }

        updateType.setSortId(0);
        updateType.setArchive("yes");
        typesDao.save(updateType);

        return "redirect:/admin/categories";
    }

    // ARCHIVE SIZE
    @RequestMapping(value="sizes/archive/{id}")
    public String archiveSize(@PathVariable int id) {

        Sizes updateSize = sizesDao.findOne(id);

        for (Sizes sortSize : sizesDao.findAll()) {
            if (sortSize.getSortId() > updateSize.getSortId()) {
                sortSize.setSortId(sortSize.getSortId() - 1);
                sizesDao.save(sortSize);
            }
        }

        updateSize.setSortId(0);
        updateSize.setArchive("yes");
        sizesDao.save(updateSize);

        return "redirect:/admin/sizes";
    }

    // REACTIVATE ARCHIVED CATEGORY
    @RequestMapping(value="categories/reactivate/cat/{id}")
    public String reactivateCat(@PathVariable int id) {

        Categories reactivate = categoriesDao.findOne(id);
        int maxSortId = 0;

        for (Categories cat : categoriesDao.findAll()) {
            if (maxSortId < cat.getSortId()) {
                maxSortId = cat.getSortId();
            }
        }

        reactivate.setArchive("no");
        reactivate.setHidden("yes");
        reactivate.setSortId(maxSortId + 1);
        categoriesDao.save(reactivate);

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == reactivate.getId()) {
                type.setCatArchive("no");
                typesDao.save(type);
            }
        }

        return "redirect:/admin/archive";
    }

    // REACTIVATE ARCHIVED SUBCATEGORY
    @RequestMapping(value="categories/reactivate/type/{id}")
    public String reactivateType(@PathVariable int id) {

        Types reactivate = typesDao.findOne(id);
        int maxSortId = 0;

        for (Types type : typesDao.findAll()) {
            if (type.getCategoryId() == reactivate.getCategoryId()) {
                if (maxSortId < type.getSortId()) {
                    maxSortId = type.getSortId();
                }
            }
        }

        reactivate.setArchive("no");
        reactivate.setHidden("yes");
        reactivate.setSortId(maxSortId + 1);
        typesDao.save(reactivate);

        return "redirect:/admin/archive";
    }

    // REACTIVATE SIZE
    @RequestMapping(value="sizes/reactivate/{id}")
    public String reactivateSize(@PathVariable int id) {

        Sizes reactivate = sizesDao.findOne(id);
        int maxSortId = 0;

        for (Sizes size : sizesDao.findAll()) {
            if (maxSortId < size.getSortId()) {
                maxSortId = size.getSortId();
            }
        }

        reactivate.setArchive("no");
        reactivate.setHidden("yes");
        reactivate.setSortId(maxSortId + 1);
        sizesDao.save(reactivate);

        return "redirect:/admin/archive";
    }
}
