package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String googleId;

    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private int phoneArea;

    @NotNull
    private int phonePrefix;

    @NotNull
    private int phoneLine;

    public Customers(String email, String firstName, String lastName, int phoneArea, int phonePrefix, int phoneLine) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneArea = phoneArea;
        this.phonePrefix = phonePrefix;
        this.phoneLine = phoneLine;
    }

    public Customers() {}

    public int getId() { return id; }

    public String getGoogleId() { return googleId; }

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
