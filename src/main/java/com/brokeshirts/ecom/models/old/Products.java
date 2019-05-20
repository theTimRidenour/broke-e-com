package com.brokeshirts.ecom.models.old;

public class Products {

    private int productId;
    private String name;
    private int typeId;

    private static int nextId = 1;

    public Products(String name, int typeId) {
        this();
        this.name = name;
        this.typeId = typeId;
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

}
