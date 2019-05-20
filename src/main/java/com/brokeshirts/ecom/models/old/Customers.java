package com.brokeshirts.ecom.models.old;

import java.util.Date;

public class Customers {

    private int customerId;
    private String googleId;
    private String email;
    private String firstName;
    private String lastName;
    private int phoneArea;
    private int phonePrefix;
    private int phoneLine;

    private static int nextId = 1;

    public Customers(String email, String firstName, String lastName, int phoneArea, int phonePrefix, int phoneLine) {
        this();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneArea = phoneArea;
        this.phonePrefix = phonePrefix;
        this.phoneLine = phoneLine;
    }

    public Customers() {
        customerId = nextId;
        nextId++;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(int phoneArea) {
        this.phoneArea = phoneArea;
    }

    public int getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(int phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public int getPhoneLine() {
        return phoneLine;
    }

    public void setPhoneLine(int phoneLine) {
        this.phoneLine = phoneLine;
    }

}
