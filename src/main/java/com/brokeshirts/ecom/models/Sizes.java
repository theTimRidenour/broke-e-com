package com.brokeshirts.ecom.models;

public class Sizes {

    private int sizeId;
    private String longName;
    private String shortName;

    private static int nextId = 1;

    public Sizes(String longName, String shortName) {
        this();
        this.longName = longName;
        this.shortName = shortName;
    }

    public Sizes() {
        this.sizeId = nextId;
        nextId++;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
