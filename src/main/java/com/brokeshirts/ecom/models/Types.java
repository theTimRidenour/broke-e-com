package com.brokeshirts.ecom.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Types {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @ManyToOne
    private Categories category;

    @OneToMany(mappedBy = "types")
    private List<Products> products;

    private int sortId;

    private String hidden;

    private String archive;

    private String catArchive;

    public Types(String name) {
        this.name = name;
    }

    public Types() {}

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) { this.category = category; }

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

    public List<Products> getProducts() { return products; }

    public void setProducts(List<Products> products) { this.products = products; }
}
