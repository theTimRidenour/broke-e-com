package com.brokeshirts.ecom.models;

public class Types {

    private int typeId;
    private String name;
    private int categoryId;

    private static int nextId = 1;

    public Types(String name, int categoryId) {
        this();
        this.name = name;
        this.categoryId = categoryId;
    }

    public Types() {
        this.typeId = nextId;
        nextId++;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
