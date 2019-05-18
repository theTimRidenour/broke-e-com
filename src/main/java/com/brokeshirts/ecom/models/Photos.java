package com.brokeshirts.ecom.models;

public class Photos {

    private int photoId;
    private int productId;
    private int colorId;
    private String url;

    private static int nextId = 1;

    public Photos(int productId, int colorId, String url) {
        this();
        this.productId = productId;
        this.colorId = colorId;
        this.url = url;
    }

    public Photos() {
        this.photoId = nextId;
        nextId++;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
