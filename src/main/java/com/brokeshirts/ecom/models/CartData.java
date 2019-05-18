package com.brokeshirts.ecom.models;

import java.util.ArrayList;

public class CartData {

    static ArrayList<Cart> carts = new ArrayList<>();

    public static ArrayList<Cart> getAll() {
        return carts;
    }

    public static Cart getById(int id) {
        Cart oneCart = null;

        for (Cart candidateCart : carts) {
            if (candidateCart.getCustomerId() == id) {
                oneCart = candidateCart;
            }
        }

        return oneCart;
    }

    public static void add(Cart newCart) {
        carts.add(newCart);
    }

    public static void remove(int id) {
        Cart cartToRemove = getById(id);
        carts.remove(cartToRemove);
    }

}
