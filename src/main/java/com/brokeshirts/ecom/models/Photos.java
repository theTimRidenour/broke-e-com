package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Photos {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private int productId;

    private int colorId;

    @NotNull
    private String url;

    public Photos(int productId, int colorId, String url) {
        this.productId = productId;
        this.colorId = colorId;
        this.url = url;
    }

    public Photos() {}

    public int getId() { return id; }

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
