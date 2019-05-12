package com.brokeshirts.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Customers {

  @Id
  @GeneratedValue
  private int id;

  private String google_id;

  @NotNull
  private String email;

  @NotNull
  private String first_name;

  @NotNull
  private String last_name;

  @NotNull
  private String address_1;

  private String address_2;

  @NotNull
  private String city;

  @NotNull
  private String state;

  @NotNull
  private int zip;

  @NotNull
  private int phone_area;

  @NotNull
  private int phone_prefix;

  @NotNull
  private int phone_line;

  public Customers(String email) {
    this.email = email;
  }

  public Customers() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGoogle_id() {
    return google_id;
  }

  public void setGoogle_id(String google_id) {
    this.google_id = google_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getAddress_1() {
    return address_1;
  }

  public void setAddress_1(String address_1) {
    this.address_1 =  address_1;
  }

  public String getAddress_2() {
    return address_2;
  }

  public void setAddress_2(String address_2) {
    this.address_2 = address_2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getPhone_area() {
    return phone_area;
  }

  public void setPhone_area(int phone_area) {
    this.phone_area = phone_area;
  }

  public int getPhone_prefix() {
    return phone_prefix;
  }

  public void setPhone_prefix(int phone_prefix) {
    this.phone_prefix = phone_prefix;
  }

  public int getPhone_line() {
    return phone_line;
  }

  public void setPhone_line(int phone_line) {
    this.phone_line = phone_line;
  }

}
