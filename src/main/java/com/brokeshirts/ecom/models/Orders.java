package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String token;

    int userId;

    String inventory;

    Float shipping;

    int addressId;

    String orderPaid;

    String orderItemsMade;

    String orderItemsShipped;

    String orderComplete;

    public Orders() {
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getOrderPaid() {
        return orderPaid;
    }

    public void setOrderPaid(String orderPaid) {
        this.orderPaid = orderPaid;
    }

    public String getOrderItemsMade() {
        return orderItemsMade;
    }

    public void setOrderItemsMade(String orderItemsMade) {
        this.orderItemsMade = orderItemsMade;
    }

    public String getOrderItemsShipped() {
        return orderItemsShipped;
    }

    public void setOrderItemsShipped(String orderItemsShipped) {
        this.orderItemsShipped = orderItemsShipped;
    }

    public String getOrderComplete() {
        return orderComplete;
    }

    public void setOrderComplete(String orderComplete) {
        this.orderComplete = orderComplete;
    }

    public Float getShipping() {
        return shipping;
    }

    public void setShipping(Float shipping) {
        this.shipping = shipping;
    }
}
