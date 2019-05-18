package com.brokeshirts.ecom.models;

import java.util.ArrayList;

public class ProductsData {

    static ArrayList<Products> products = new ArrayList<>();

    public static ArrayList<Products> getAll() {
        return products;
    }

    public static Products getById(int id) {
        Products oneProduct = null;

        for (Products candidateProduct : products) {
            if (candidateProduct.getProductId() == id) {
                oneProduct = candidateProduct;
            }
        }

        return oneProduct;
    }

    public static void add(Products newProduct) {
        products.add(newProduct);
    }

    public static void remove(int id) {
        Products productToRemove = getById(id);
        products.remove(productToRemove);
    }

}
