package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.Categories;
import com.brokeshirts.ecom.models.data.CategoriesDao;
import com.brokeshirts.ecom.models.Types;
import com.brokeshirts.ecom.models.data.TypesDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menus {

// SORTING ORDER OF DATA

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

    // SORTING ORDER OF CATEGORIES FOR ADMIN
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

    // SORT ORDER OF SUBCATEGORIES FOR CUSTOMERS
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

    // SORT ORDER OF SUBCATEGORIES FOR ADMIN
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

// EXTRA ADMIN DATA

    // SUBCATEGORY TYPE COUNTS FOR ADMIN FORMS
    public static HashMap<Integer, Integer> catTypeCnt(CategoriesDao categoriesDao, TypesDao typesDao) {

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

        return categoryTypeCount;
    }

}
