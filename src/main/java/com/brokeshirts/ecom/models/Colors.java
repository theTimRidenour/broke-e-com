package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Colors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String hex;

    private String url;

    private String hidden;

    private String archive;

    public Colors(String name, String hex) {
        this.name = name;
        this.hex = hex;
    }

    public Colors() {}

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() { return hex; }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) {
        this.url = url;
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
}
