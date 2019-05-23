package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Types {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @NotNull
    private int categoryId;

    private int sortId;

    private String hidden;

    private String archive;

    private String catArchive;

    public Types(String name, int categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public Types() {}

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public String getCatArchive() {
        return catArchive;
    }

    public void setCatArchive(String catArchive) {
        this.catArchive = catArchive;
    }

}
