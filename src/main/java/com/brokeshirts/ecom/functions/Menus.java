package com.brokeshirts.ecom.functions;

import com.brokeshirts.ecom.models.Categories;
import com.brokeshirts.ecom.models.data.CategoriesDao;
import com.brokeshirts.ecom.models.Types;
import com.brokeshirts.ecom.models.data.TypesDao;

import java.util.ArrayList;
import java.util.HashMap;

public class Menus {

    public static ArrayList<Categories> sortedCat(CategoriesDao categoriesDao) {

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
                    if (cat.getHidden().equals("no")) {
                        sortedCat.add(cat);
                    }
                    sortId++;
                }
            }
        }

        return sortedCat;

    }

    public static ArrayList<Categories> sortedCatAdmin(CategoriesDao categoriesDao) {

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

    public static ArrayList<Types> sortedTypes(CategoriesDao categoriesDao, TypesDao typesDao) {

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
        ArrayList<Types> sortedTypesMain = new ArrayList<>();

        int sortId = 1;

        for (Types type : typesDao.findAll()) {
            unsortedTypes.add(type);
        }

        while (sortId <= maxTypeCnt) {
            for (Types type : unsortedTypes) {
                if (type.getSortId() == sortId) {
                    if (type.getArchive().equals("no")) {
                        sortedTypesMain.add(type);
                    }
                }
            }
            sortId++;
        }

        return sortedTypesMain;

    }

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
