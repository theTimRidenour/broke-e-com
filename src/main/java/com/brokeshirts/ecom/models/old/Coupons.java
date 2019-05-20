package com.brokeshirts.ecom.models.old;

import java.util.Date;

public class Coupons {

    private int couponId;
    private String code;
    private Date startDate;
    private Date endDate;
    private String description;
    private int percent_off;
    private int dollar_off;
    private int bogo;
    private int[] productIdReq;
    private int amountReq;
    private int maxUses;
    private int currentUses;

    private static int nextId = 1;

    public Coupons(String code) {
        this();
        this.code = code;
        this.currentUses = 0;
    }

    public Coupons() {
        couponId = nextId;
        nextId++;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPercent_off() {
        return percent_off;
    }

    public void setPercent_off(int percent_off) {
        this.percent_off = percent_off;
    }

    public int getDollar_off() {
        return dollar_off;
    }

    public void setDollar_off(int dollar_off) {
        this.dollar_off = dollar_off;
    }

    public int getBogo() {
        return bogo;
    }

    public void setBogo(int bogo) {
        this.bogo = bogo;
    }

    public int[] getProductIdReq() {
        return productIdReq;
    }

    public void setProductIdReq(int[] productIdReq) {
        this.productIdReq = productIdReq;
    }

    public int getAmountReq() {
        return amountReq;
    }

    public void setAmountReq(int amountReq) {
        this.amountReq = amountReq;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getCurrentUses() {
        return currentUses;
    }

    public void setCurrentUses(int currentUses) {
        this.currentUses = currentUses;
    }
}
