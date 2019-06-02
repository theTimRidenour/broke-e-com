package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.*;
import com.brokeshirts.ecom.models.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menus {

//// SORTING ORDER OF DATA

    // SORT ORDER OF CATEGORIES FOR CUSTOMERS
    public static ArrayList<Categories> sortCat(CategoriesDao categoriesDao) {

        ArrayList<Categories> unsortedCat = sortCatAdmin(categoriesDao);
        ArrayList<Categories> sortedCat = new ArrayList<>();

        for (Categories cat : unsortedCat) {
            if (cat.getHidden().equals("no")) {
                sortedCat.add(cat);
            }
        }

        return sortedCat;
    }

    // SORT ORDER OF CATEGORIES FOR ADMIN
    public static ArrayList<Categories> sortCatAdmin(CategoriesDao categoriesDao) {

        ArrayList<Categories> unsortedCat = new ArrayList<>();
        ArrayList<Categories> sortedCat = new ArrayList<>();
        int sortMaxId = 0;
        int sortId = 1;

        for (Categories maxCat : categoriesDao.findAll()) {
            if (sortMaxId < maxCat.getSortId()) {
                sortMaxId = maxCat.getSortId();
            }
        }

        for (Categories cat : categoriesDao.findAll()) {
            unsortedCat.add(cat);
        }

        while (sortId <= sortMaxId) {
            for (Categories cat : unsortedCat) {
                if (cat.getSortId() == sortId) {
                    sortedCat.add(cat);
                    sortId++;
                }
            }
        }

        return sortedCat;

    }

    // SORT ORDER OF COLORS FOR CUSTOMERS
    public static ArrayList<Colors> sortColors(ColorsDao colorsDao) {

        ArrayList<Colors> sortedColors = new ArrayList<>();

        for (Colors color : sortColorsAdmin(colorsDao)) {
            if (color.getHidden().equals("no")) {
                sortedColors.add(color);
            }
        }

        return sortedColors;
    }

    // SORT ORDER OF COLORS FOR ADMIN
    public static ArrayList<Colors> sortColorsAdmin(ColorsDao colorsDao) {

        ArrayList<Colors> sortedColors = new ArrayList<>();

        for (Colors color : colorsDao.findAll()) {
            if (color.getArchive().equals("no")) {
                sortedColors.add(color);
            }
        }

        return sortedColors;
    }

    // SORT ORDER OF INVENTORY FOR CUSTOMERS
    public static ArrayList<Inventory> sortInventory(InventoryDao inventoryDao, ProductsDao productsDao, CategoriesDao categoriesDao, TypesDao typesDao, SizesDao sizesDao, ColorsDao colorsDao) {

        ArrayList<Inventory> sortedInventory = new ArrayList<>();

        for (Inventory item : sortInventoryAdmin(inventoryDao, productsDao, categoriesDao, typesDao, sizesDao, colorsDao)) {
            if (item.getHidden().equals("no")) {
                sortedInventory.add(item);
            }
        }

        return sortedInventory;
    }

    // SORT ORDER OF INVENTORY FOR ADMIN
    public static ArrayList<Inventory> sortInventoryAdmin(InventoryDao inventoryDao, ProductsDao productsDao, CategoriesDao categoriesDao, TypesDao typesDao, SizesDao sizesDao, ColorsDao colorsDao) {

        ArrayList<Inventory> sortedInventory = new ArrayList<>();

        for (Colors color : sortColorsAdmin(colorsDao)) {
            for (Sizes size : sortSizes(sizesDao)) {
                for (Products product : sortProductsAdmin(productsDao, categoriesDao, typesDao)) {
                    for (Inventory item : inventoryDao.findAll()) {
                        if (item.getProducts().getId() == product.getId() && item.getSizeId() == size.getId() && item.getColorId() == color.getId() && item.getArchive().equals("no")) {
                            sortedInventory.add(item);
                        }
                    }
                }
            }
        }

        return sortedInventory;
    }

    // SORT ORDER OF PRODUCTS FOR CUSTOMERS
    public static ArrayList<Products> sortProducts(ProductsDao productsDao, CategoriesDao categoriesDao, TypesDao typesDao) {

        ArrayList<Products> sortedProducts = new ArrayList<>();

        for (Products product : sortProductsAdmin(productsDao, categoriesDao, typesDao)) {
            if (product.getHidden().equals("no")) {
                sortedProducts.add(product);
            }
        }

        return sortedProducts;
    }

    // SORT ORDER OF PRODUCTS FOR ADMIN
    public static ArrayList<Products> sortProductsAdmin(ProductsDao productsDao, CategoriesDao categoriesDao, TypesDao typesDao) {

        ArrayList<Products> sortedProducts = new ArrayList<>();

        for (Categories cat : categoriesDao.findAll()) {
            for (Types type : typesDao.findAll()) {
                for (Products product : productsDao.findAll()) {
                    if (product.getCategoryId() == cat.getId() && product.getType() == type && product.getArchive().equals("no")) {
                        sortedProducts.add(product);
                    }
                }
            }
        }

        return sortedProducts;
    }

    // SORT ORDER OF SUB-CATEGORIES FOR CUSTOMERS
    public static ArrayList<Types> sortTypes(CategoriesDao categoriesDao, TypesDao typesDao) {

        ArrayList<Types> unsortedTypes = sortTypesAdmin(categoriesDao, typesDao);
        ArrayList<Types> sortedTypes = new ArrayList<>();

        for (Types type : unsortedTypes) {
            if (type.getHidden().equals("no")) {
                sortedTypes.add(type);
            }
        }

        return sortedTypes;
    }

    // SORT ORDER OF SUB-CATEGORIES FOR ADMIN
    public static ArrayList<Types> sortTypesAdmin(CategoriesDao categoriesDao, TypesDao typesDao) {

        HashMap<Integer, Integer> categoryTypeCount = catTypeCnt(categoriesDao, typesDao);
        ArrayList<Types> unsortedTypes = new ArrayList<>();
        ArrayList<Types> sortedTypes = new ArrayList<>();
        int maxTypeCnt = 0;
        int sortId = 1;

        for (Map.Entry catTypeCnt : categoryTypeCount.entrySet()) {
            if ((int)catTypeCnt.getValue() > maxTypeCnt) {
                maxTypeCnt = (int)catTypeCnt.getValue();
            }
        }

        for (Types type : typesDao.findAll()) {
            unsortedTypes.add(type);
        }

        while (sortId <= maxTypeCnt) {
            for (Types type : unsortedTypes) {
                if (type.getSortId() == sortId) {
                    sortedTypes.add(type);
                }
            }
            sortId++;
        }

        return sortedTypes;
    }

    // SORT ORDER OF STYLES FOR CUSTOMERS
    public static ArrayList<Styles> sortStyles(CategoriesDao categoriesDao, StylesDao stylesDao) {

        ArrayList<Styles> unsortedStyles = sortStylesAdmin(categoriesDao, stylesDao);
        ArrayList<Styles> sortedStyles = new ArrayList<>();

        for (Styles style : unsortedStyles) {
            if (style.getHidden().equals("no")) {
                sortedStyles.add(style);
            }
        }

        return sortedStyles;
    }

    // SORT ORDER OF STYLES FOR ADMIN
    public static ArrayList<Styles> sortStylesAdmin(CategoriesDao categoriesDao, StylesDao stylesDao) {

        HashMap<Integer, Integer> categoryStyleCount = catStyleCnt(categoriesDao, stylesDao);
        ArrayList<Styles> unsortedStyles = new ArrayList<>();
        ArrayList<Styles> sortedStyles = new ArrayList<>();
        int maxStyleCnt = 0;
        int sortId = 1;

        for (Map.Entry catStyleCnt : categoryStyleCount.entrySet()) {
            if ((int)catStyleCnt.getValue() > maxStyleCnt) {
                maxStyleCnt = (int)catStyleCnt.getValue();
            }
        }

        for (Styles style : stylesDao.findAll()) {
            unsortedStyles.add(style);
        }

        while (sortId <= maxStyleCnt) {
            for (Styles style : unsortedStyles) {
                if (style.getSortId() == sortId) {
                    sortedStyles.add(style);
                }
            }
            sortId++;
        }

        return sortedStyles;
    }

    // SORT ORDER OF SIZES
    public static ArrayList<Sizes> sortSizes(SizesDao sizesDao) {

        ArrayList<Sizes> sortedSizes = new ArrayList<>();
        int sortId = 1;
        int maxSortId = 0;

        for (Sizes size : sizesDao.findAll()) {
            if (maxSortId < size.getSortId()) {
                maxSortId = size.getSortId();
            }
        }

        while (sortId <= maxSortId) {
            for (Sizes size : sizesDao.findAll()) {
                if (size.getSortId() == sortId && size.getArchive().equals("no")) {
                    sortedSizes.add(size);
                }
            }
            sortId++;
        }

        return sortedSizes;
    }

//// EXTRA ADMIN DATA

    // PRODUCTS TOTAL INVENTORY COUNT
    public static HashMap<Integer, Integer> productInventoryCnt(ProductsDao productsDao, InventoryDao inventoryDao) {

        HashMap<Integer, Integer> totalProductCnt = new HashMap<>();

        int count = 0;

        for (Products product : productsDao.findAll()) {
            if (product.getArchive().equals("no")) {
                count = 0;
                for (Inventory item : inventoryDao.findAll()) {
                    if (item.getArchive().equals("no") && item.getProducts() == product) {
                        count = count + item.getQuantity();
                    }
                }
                totalProductCnt.put(product.getId(), count);
            }
        }

        return totalProductCnt;
    }

    // SUB-CATEGORY TYPE COUNTS FOR ADMIN FORMS
    public static HashMap<Integer, Integer> catTypeCnt(CategoriesDao categoriesDao, TypesDao typesDao) {

        HashMap<Integer, Integer> categoryTypeCount = new HashMap<>();
        int count = 0;
        int maxTypeCnt = 0;

        for (Categories cat : categoriesDao.findAll()) {
            count = 0;
            for (Types type : typesDao.findAll()) {
                if (type.getCategory() == cat) {
                    if (type.getSortId() != 0) {
                        count++;
                    }
                }
            }
            categoryTypeCount.put(cat.getId(), count);
            if (maxTypeCnt < count) {
                maxTypeCnt = count;
            }
        }

        return categoryTypeCount;
    }

    // STYLE TYPE COUNTS FOR ADMIN FORMS
    public static HashMap<Integer, Integer> catStyleCnt(CategoriesDao categoriesDao, StylesDao stylesDao) {

        HashMap<Integer, Integer> categoryStyleCount = new HashMap<>();
        int count = 0;
        int maxTypeCnt = 0;

        for (Categories cat : categoriesDao.findAll()) {
            count = 0;
            for (Styles style : stylesDao.findAll()) {
                if (style.getCategory() == cat) {
                    if (style.getSortId() != 0) {
                        count++;
                    }
                }
            }
            categoryStyleCount.put(cat.getId(), count);
            if (maxTypeCnt < count) {
                maxTypeCnt = count;
            }
        }

        return categoryStyleCount;
    }
}
