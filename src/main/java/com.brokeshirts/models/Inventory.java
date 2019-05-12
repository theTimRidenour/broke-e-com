package com.brokeshirts.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Inventory {

  @Id
  @GeneratedValue
  private int id;

  private int product_id;

  @NotNull
  private String sku;

  @NotNull
  private int size_id;

  @NotNull
  private int color_id;

  @NotNull
  private int quantity;

  public Inventory(String sku) {
    this.sku = sku;
  }

  public Inventory() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProduct_id() {
    return product_id;
  }

  public void setProduct_id(int product_id) {
    this.product_id = product_id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public int getSize_id() {
    return size_id;
  }

  public void setSize_id(int size_id) {
    this.size_id = size_id;
  }

  public int getColor_id() {
    return color_id;
  }

  public void setColor_id(int color_id) {
    this.color_id = color_id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity =  quantity;
  }

}
