package com.brokeshirts.ecom.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Products {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @NotNull
    private int categoryId;

    @ManyToOne
    private Types types;

    @OneToMany(mappedBy = "products")
    private List<Inventory> inventory;

    @ElementCollection
    private List<Integer> styleIds = new ArrayList<>();

    private int imageId;

    private String hidden;

    private String archive;

    private String archiveCat;

    private String archiveType;

    private String archiveStyle;

    public Products(String name, int typeId, int categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public Products() {}

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getStyleIds() { return styleIds; }

    public void addStyleId(int styleId) { styleIds.add((Integer) styleId); }

    public void removeStyleId(int styleId) { styleIds.remove(styleId); }

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

    public int getImageId() { return imageId; }

    public void setImageId(int imageId) { this.imageId = imageId; }

    public Types getType() { return types; }

    public void setType(Types types) { this.types = types; }

    public void setStyleIds(List<Integer> styleIds) { this.styleIds = styleIds; }

    public List<Inventory> getInventory() { return inventory; }

    public void setInventory(List<Inventory> inventory) { this.inventory = inventory; }
}
