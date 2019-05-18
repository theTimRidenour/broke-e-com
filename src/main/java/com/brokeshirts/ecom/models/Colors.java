package com.brokeshirts.ecom.models;

public class Colors {

    private int colorId;
    private String name;
    private String hex;
    private String url;

    private static int nextId = 1;

    public Colors(String name, String hex) {
        this();
        this.name = name;
        this.hex = hex;
    }

    public Colors() {
        colorId = nextId;
        nextId++;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
