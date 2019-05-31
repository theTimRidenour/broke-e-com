package com.brokeshirts.ecom.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String name;

    private int sortId;

    private String hidden;

    private String archive;

    @OneToMany(mappedBy = "category")
    private List<Types> types;

    @OneToMany(mappedBy = "category")
    private List<Styles> styles;

    public Categories(String name) {
        this.name = name;
    }

    public Categories() {}

    public int getId() { return id; }

    public List<Types> getTypes() { return types; }

    public void setTypes(List<Types> types) { this.types = types; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortId() { return sortId; }

    public void setSortId(int sortId) { this.sortId = sortId; }

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

    public List<Styles> getStyles() { return styles; }

    public void setStyles(List<Styles> styles) { this.styles = styles; }
}