package com.brokeshirts.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Coupons {

  @Id
  @GeneratedValue
  private int id;

  @NotNull
  private String code;

  @NotNull
  private String start_date;

  @NotNull
  private String end_date;

  private String description;

  private int percent_off;

  private double dollar_off;

  private int bogo;

  private String product_id_req;

  private double amount_req;

  private int max_uses;

  private int uses;

  public Coupons(String code) {
    this.code = code;
  }

  public Coupons() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getStartDate() {
    return start_date;
  }

  public void setStartDate(String start_date) {
    this.start_date = start_date;
  }

  public String getEndDate() {
    return end_date;
  }

  public void setEndDate(String end_date) {
    this.end_date = end_date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
