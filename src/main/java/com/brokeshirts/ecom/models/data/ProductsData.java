package com.brokeshirts.ecom.models.data;

import com.brokeshirts.ecom.models.Products;

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

    public static ArrayList<Products> getByType(int typeId) {
        ArrayList<Products> typeProducts = new ArrayList<>();

        for (Products candidateProduct : products) {
            if (candidateProduct.getTypeId() == typeId) {
                typeProducts.add(candidateProduct);
            }
        }

        return typeProducts;
    }

    public static void add(Products newProduct) {
        products.add(newProduct);
    }

    public static void remove(int id) {
        Products productToRemove = getById(id);
        products.remove(productToRemove);
    }

}
