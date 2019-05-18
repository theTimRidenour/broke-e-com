package com.brokeshirts.ecom.models;

public class Categories {

    private int categoryId;
    private String name;

    private static int nextId = 1;

    public Categories(String name) {
        this();
        this.name = name;
    }

    public Categories() {
        categoryId = nextId;
        nextId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


}
