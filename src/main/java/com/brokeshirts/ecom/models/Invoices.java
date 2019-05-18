package com.brokeshirts.ecom.models;

import java.util.Date;

public class Invoices {

    private int invoiceId;
    private int customerId;
    private int[] inventoryIds;
    private int[] prices;
    private String couponCode;
    private int couponValue;
    private String paymentType;
    private Date orderDate;
    private String status;
    private int returnAuthorizationId;

    private static int nextId = 1;

    public Invoices(int customerId, int[] inventoryIds, int[] prices, String couponCode, int couponValue, String paymentType, Date orderDate, String status, int returnAuthorizationId) {
        this();
        this.customerId = customerId;
        this.inventoryIds = inventoryIds;
        this.prices = prices;
        this.couponCode = couponCode;
        this.couponValue = couponValue;
        this.paymentType = paymentType;
        this.orderDate = orderDate;
        this.status = status;
        this.returnAuthorizationId = returnAuthorizationId;
    }

    public Invoices() {
        this.invoiceId = nextId;
        nextId++;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int[] getInventoryIds() {
        return inventoryIds;
    }

    public void setInventoryIds(int[] inventoryIds) {
        this.inventoryIds = inventoryIds;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReturnAuthorizationId() {
        return returnAuthorizationId;
    }

    public void setReturnAuthorizationId(int returnAuthorizationId) {
        this.returnAuthorizationId = returnAuthorizationId;
    }

}
