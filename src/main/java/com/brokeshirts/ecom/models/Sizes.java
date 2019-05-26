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

    private String archive;

    private String hidden;

    private int sortId;

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

    public String getArchive() { return archive; }

    public void setArchive( String archive ) { this.archive = archive; }

    public int getSortId() { return sortId; }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getHidden() { return hidden; }

    public void setHidden(String hidden) { this.hidden = hidden; }
}
