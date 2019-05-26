package com.brokeshirts.ecom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Products {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    private int typeId;

    private int styleId;

    private int itemId;

    private int categoryId;

    private String hidden;

    private String archive;

    private String archiveCat;

    private String archiveType;

    private String archiveStyle;

    public Products(String name, int typeId) {
        this.name = name;
        this.typeId = typeId;
    }

    public Products() {}

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getArchiveCat() {
        return archiveCat;
    }

    public void setArchiveCat(String archiveCat) {
        this.archiveCat = archiveCat;
    }

    public String getArchiveType() {
        return archiveType;
    }

    public void setArchiveType(String archiveType) {
        this.archiveType = archiveType;
    }

    public String getArchiveStyle() {
        return archiveStyle;
    }

    public void setArchiveStyle(String archiveStyle) {
        this.archiveStyle = archiveStyle;
    }
}
