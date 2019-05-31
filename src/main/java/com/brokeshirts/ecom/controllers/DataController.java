package com.brokeshirts.ecom.controllers;

import com.brokeshirts.ecom.functions.Data;
import com.brokeshirts.ecom.functions.Menus;
import com.brokeshirts.ecom.models.data.*;
import com.brokeshirts.ecom.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
@RequestMapping(value="data")
public class DataController {

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

    @Autowired
    private StylesDao stylesDao;

//// ADD DATA

    // ADD CATEGORY
    @RequestMapping(value="add/category", method = RequestMethod.POST)
    public String addCategory(@RequestParam("categoryName") String categoryName) {

        if (!categoryName.isEmpty()) {
            Data.addCat(categoryName, categoriesDao);
        }
        return "redirect:/admin/categories";
    }

    // ADD COLOR
    @RequestMapping(value="add/color", method = RequestMethod.POST)
    public String addColor(@RequestParam("file") MultipartFile file, @RequestParam String hex, @RequestParam String name) {

        if (!hex.isEmpty() && !name.isEmpty()) {
            Data.addColor(file, hex, name, colorsDao);
        }
        return "redirect:/admin/colors";
    }

    // ADD PRODUCT
    @RequestMapping(value="add/product", method = RequestMethod.POST)
    public String addProduct(@RequestParam int categoryId, @RequestParam int typeId, @RequestParam(value="styleIds", required = false) int[] styleIds, @RequestParam("file") MultipartFile file, String name) {

        Data.addProduct(categoryId, typeId, styleIds, stylesDao, name, file, photosDao, productsDao, typesDao);

        return "redirect:/admin/products";
    }

    // ADD SIZE
    @RequestMapping(value="add/size", method = RequestMethod.POST)
    public String addSize(@RequestParam String longName, @RequestParam String shortName) {

        if (!longName.isEmpty() && !shortName.isEmpty()) {
            Data.addSize(longName, shortName, sizesDao);
        }
        return "redirect:/admin/sizes";
    }

    // ADD STYLE
    @RequestMapping(value="add/style", method = RequestMethod.POST)
    public String addStyle(@RequestParam("styleName") String styleName, @RequestParam("categoryId") int categoryId) {

        if (!styleName.isEmpty() && categoryId != 0) {
            Data.addStyle(styleName, categoryId, stylesDao, categoriesDao);
        }
        return "redirect:/admin/categories";
    }

    // ADD SUBCATEGORY
    @RequestMapping(value="add/subcategory", method = RequestMethod.POST)
    public String addType(@RequestParam("typeName") String typeName, @RequestParam("categoryId") int categoryId) {

        if (!typeName.isEmpty() && categoryId != 0) {
            Data.addType(typeName, categoryId, typesDao, categoriesDao);
        }
        return "redirect:/admin/categories";
    }


//// DELETE DATA

    // DELETE CATEGORY AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/cat/{id}")
    public String delCat(@PathVariable int id) {

        Data.delCat(id, categoriesDao, typesDao, stylesDao);
        return "redirect:/admin/archive";
    }

    // DELETE COLOR AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/color/{id}")
    public String delColor(@PathVariable int id) {

        Data.delColor(id, colorsDao);
        return "redirect:/admin/archive";
    }

    // DELETE PRODUCT AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/product/{id}")
    public String delProduct(@PathVariable int id) {

        Data.delProduct(id, productsDao, photosDao);
        return "redirect:/admin/archive";
    }

    // DELETE SIZE AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/size/{id}")
    public String delSize(@PathVariable int id) {

        Data.delSize(id, sizesDao);
        return "redirect:/admin/archive";
    }

    // DELETE STYLE AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/style/{id}")
    public String delStyle(@PathVariable int id) {

        Data.delStyle(id, stylesDao);
        return "redirect:/admin/archive";
    }

    // DELETE SUBCATEGORY AND ALL ASSOCIATED FILES
    @RequestMapping(value="delete/type/{id}")
    public String delType(@PathVariable int id) {

        Data.delType(id, typesDao);
        return "redirect:/admin/archive";
    }
}
