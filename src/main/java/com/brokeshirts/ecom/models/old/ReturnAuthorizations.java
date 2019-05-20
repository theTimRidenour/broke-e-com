package com.brokeshirts.ecom.models.old;

import java.util.Date;

public class ReturnAuthorizations {

    private int returnAuthorizationId;
    private int customerId;
    private int invoiceId;
    private Date purchaseDate;
    private int[] inventoryIdsReturned;
    private String reason;
    private Date returnDate;
    private int refundAmount;
    private Date requestDate;
    private String completed;

    private static int nextId = 1;

    public ReturnAuthorizations(int customerId, int invoiceId, Date purchaseDate, int[] inventoryIdsReturned, String reason, int refundAmount, Date requestDate) {
        this();
        this.customerId = customerId;
        this.invoiceId = invoiceId;
        this.purchaseDate = purchaseDate;
        this.inventoryIdsReturned = inventoryIdsReturned;
        this.reason = reason;
        this.refundAmount = refundAmount;
        this.requestDate = requestDate;
        this.completed = "no";
    }

    public ReturnAuthorizations() {
        this.returnAuthorizationId = nextId;
        nextId++;
    }

    public int getReturnAuthorizationId() {
        return returnAuthorizationId;
    }

    public void setReturnAuthorizationId(int returnAuthorizationId) {
        this.returnAuthorizationId = returnAuthorizationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int[] getInventoryIdsReturned() {
        return inventoryIdsReturned;
    }

    public void setInventoryIdsReturned(int[] inventoryIdsReturned) {
        this.inventoryIdsReturned = inventoryIdsReturned;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

}
