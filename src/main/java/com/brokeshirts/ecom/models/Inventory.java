package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Inventory {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String sku;

    @NotNull
    private int productId;

    @NotNull
    private int sizeId;

    @NotNull
    private int colorId;

    @NotNull
    private double price;

    @NotNull
    private int quantity;

    public Inventory(String sku, int productId, int sizeId, int colorId, double price, int quantity) {
        this.sku = sku;
        this.productId = productId;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.price = price;
        this.quantity = quantity;
    }

    public Inventory() {}

    public int getId() { return id; }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
