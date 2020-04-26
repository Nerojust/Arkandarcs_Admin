package com.nerojust.arkandarcsadmin.models.add_product;

public class Product {
    private String productName, productAmount, productCategory, productDiscountedAmount;
    private boolean isOnPromo, isProductActive;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDiscountedAmount() {
        return productDiscountedAmount;
    }

    public void setProductDiscountedAmount(String productDiscountedAmount) {
        this.productDiscountedAmount = productDiscountedAmount;
    }

    public boolean isOnPromo() {
        return isOnPromo;
    }

    public void setOnPromo(boolean onPromo) {
        isOnPromo = onPromo;
    }

    public boolean isProductActive() {
        return isProductActive;
    }

    public void setProductActive(boolean productActive) {
        isProductActive = productActive;
    }
}

