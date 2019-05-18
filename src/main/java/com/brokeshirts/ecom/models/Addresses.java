package com.brokeshirts.ecom.models;

public class Addresses {

    private int addressId;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String stateCode;
    private int zipCode;
    private int customerId;

    private static int nextId = 1;

    public Addresses(String addressOne, String addressTwo, String city, String stateCode, int zipCode, int customerId) {

        this();
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.city = city;
        this.stateCode = stateCode;
        this.zipCode = zipCode;
        this.customerId = customerId;
    }

    public Addresses() {
        this.addressId = nextId;
        nextId++;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
