package com.brokeshirts.ecom.models;

public class Products {

    private int productId;
    private String name;
    private int typeId;
    private int categoryId;

    private static int nextId = 1;

    public Products(String name, int typeId, int categoryId) {
        this();
        this.name = name;
        this.typeId = typeId;
        this.categoryId = categoryId;
    }

    public Products() {
        productId = nextId;
        nextId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
