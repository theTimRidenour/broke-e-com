package com.brokeshirts.ecom.models.old;

public class Inventory {

    private int itemId;
    private int productId;
    private String sku;
    private int sizeId;
    private int colorId;
    private int quantity;
    private int price;

    private static int nextId = 1;

    public Inventory(int price, int productId, String sku, int sizeId, int colorId, int quantity) {
        this();
        this.productId = productId;
        this.sku = sku;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.quantity = quantity;
        this.price = price;
    }

    public Inventory() {
        this.itemId = nextId;
        nextId++;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

}
