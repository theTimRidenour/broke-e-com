package com.brokeshirts.ecom.models;

import java.util.ArrayList;

public class InventoryData {

    static ArrayList<Inventory> inventory = new ArrayList<>();

    public static ArrayList<Inventory> getAll() {
        return inventory;
    }

    public static Inventory getById(int id) {
        Inventory oneItem = null;

        for (Inventory candidateItem : inventory) {
            if (candidateItem.getItemId() == id) {
                oneItem = candidateItem;
            }
        }

        return oneItem;
    }

    public static void add(Inventory newItem) {
        inventory.add(newItem);
    }

    public static void remove(int id) {
        Inventory itemToRemove = getById(id);
        inventory.remove(itemToRemove);
    }

}
