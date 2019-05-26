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

    private String hidden;

    private String archive;

    private String archiveSize;

    private String archiveColor;

    private String archiveProduct;

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

    public String getHidden() {
        return hidden;
    }

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

    public String getArchiveProduct() {
        return archiveProduct;
    }

    public void setArchiveProduct(String archiveProduct) {
        this.archiveProduct = archiveProduct;
    }
}
