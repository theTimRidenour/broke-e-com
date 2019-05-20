package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Sizes {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String longName;

    @NotNull
    private String shortName;

    public Sizes(String longName, String shortName) {
        this.longName = longName;
        this.shortName = shortName;
    }

    public Sizes() {}

    public int getId() { return id; }

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
