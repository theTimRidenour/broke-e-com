package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Categories {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    public Categories(String name) {
        this.name = name;
    }

    public Categories() {}

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

}
