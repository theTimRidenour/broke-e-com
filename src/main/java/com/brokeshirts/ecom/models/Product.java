package com.brokeshirts.ecom.models;

public class Product {

    private int productId;
    private String name;
    private String description;

    private static int nextId = 1;

    public Product(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public Product() {
        productId = nextId;
        nextId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}
