package com.nerojust.arkandarcsadmin.models.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionSendObject {

    @SerializedName("transactionRefId")
    @Expose
    private String transactionRefId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("person")
    @Expose
    private Person person;

    public String getTransactionRefId() {
        return transactionRefId;
    }

    public void setTransactionRefId(String transactionRefId) {
        this.transactionRefId = transactionRefId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
