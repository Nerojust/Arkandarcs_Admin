package com.nerojust.arkandarcsadmin.models.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductsSendObject {

    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productCategory")
    @Expose
    private String productCategory;
    @SerializedName("productAmount")
    @Expose
    private String productAmount;
    @SerializedName("productColor")
    @Expose
    private String productColor;
    @SerializedName("numberInStock")
    @Expose
    private String numberInStock;
    @SerializedName("productDiscountedAmount")
    @Expose
    private String productDiscountedAmount;
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("isProductActive")
    @Expose
    private Boolean isProductActive;
    @SerializedName("isOnPromo")
    @Expose
    private Boolean isOnPromo;
    @SerializedName("productImages")
    @Expose
    private List<ProductImage> productImages = null;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(String numberInStock) {
        this.numberInStock = numberInStock;
    }

    public String getProductDiscountedAmount() {
        return productDiscountedAmount;
    }

    public void setProductDiscountedAmount(String productDiscountedAmount) {
        this.productDiscountedAmount = productDiscountedAmount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Boolean getProductActive() {
        return isProductActive;
    }

    public void setProductActive(Boolean productActive) {
        isProductActive = productActive;
    }

    public Boolean getOnPromo() {
        return isOnPromo;
    }

    public void setOnPromo(Boolean onPromo) {
        isOnPromo = onPromo;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }
}
