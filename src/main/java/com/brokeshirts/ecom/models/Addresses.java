package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Addresses {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String addressOne;

    private String addressTwo;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private int zipCode;

    @NotNull
    private int customerId;

    private String archive;

    public Addresses(String addressOne, String city, String state, int zipCode, int customerId) {
        this.addressOne = addressOne;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.customerId = customerId;
    }

    public Addresses() {}

    public int getId() {
        return id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getArchive() { return archive; }

    public void setArchive(String archive) { this.archive = archive; }
}
