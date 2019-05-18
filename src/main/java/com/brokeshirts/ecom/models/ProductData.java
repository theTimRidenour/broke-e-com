package com.brokeshirts.ecom.models;

import java.util.ArrayList;

public class ProductData {

    static ArrayList<Product> featured_products = new ArrayList<>();

    public static ArrayList<Product> getAll() {
        return featured_products;
    }

    public static Product getById(int id) {
        Product oneProduct = null;

        for (Product candidateProduct : featured_products) {
            if (candidateProduct.getProductId() == id) {
                oneProduct = candidateProduct;
            }
        }

        return oneProduct;
    }

    public static void add(Product newProduct) {
        featured_products.add(newProduct);
    }

    public static void remove(int id) {
        Product productToRemove = getById(id);
        featured_products.remove(productToRemove);
    }

}
