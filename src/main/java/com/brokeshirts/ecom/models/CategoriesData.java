package com.brokeshirts.ecom.models;

import java.util.ArrayList;

public class CategoriesData {

    static ArrayList<Categories> categories = new ArrayList<>();

    public static ArrayList<Categories> getAll() {
        return categories;
    }

    public static Categories getById(int id) {
        Categories oneCategory = null;

        for (Categories candidateCategory : categories) {
            if (candidateCategory.getCategoryId() == id) {
                oneCategory = candidateCategory;
            }
        }

        return oneCategory;
    }

    public static void add(Categories newCategory) {
        categories.add(newCategory);
    }

    public static void remove(int id) {
        Categories categoryToRemove = getById(id);
        categories.remove(categoryToRemove);
    }

}
