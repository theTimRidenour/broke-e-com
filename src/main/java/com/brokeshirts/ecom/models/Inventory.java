package com.brokeshirts.ecom.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String sku;

    @ManyToOne
    private Products products;

    private int imageId;

    private int sizeId;

    private int colorId;

    @NotNull
    private double price;

    @NotNull
    private int quantity;

    private String hidden;

    private String archive;

    private String archiveSize;

    private String archiveColor;

    public Inventory(String sku, int sizeId, int colorId, double price, int quantity) {
        this.sku = sku;
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

    public String getHidden() { return hidden; }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public String getArchiveSize() {
        return archiveSize;
    }

    public void setArchiveSize(String archiveSize) {
        this.archiveSize = archiveSize;
    }

    public String getArchiveColor() {
        return archiveColor;
    }

    public void setArchiveColor(String archiveColor) {
        this.archiveColor = archiveColor;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}