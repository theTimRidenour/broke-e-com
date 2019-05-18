package com.brokeshirts.ecom.models;

public class Cart {

    private int customerId;
    private int[] itemIds;

    public Cart(int customerId, int[] itemIds) {
        this.customerId = customerId;
        this.itemIds = itemIds;
    }

    public Cart() {

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(int[] itemIds) {
        this.itemIds = itemIds;
    }

}
