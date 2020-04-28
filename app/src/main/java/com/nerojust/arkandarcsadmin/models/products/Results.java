package com.nerojust.arkandarcsadmin.models.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {

    @SerializedName("productImages")
    @Expose
    private ProductImages productImages;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productCategory")
    @Expose
    private String productCategory;
    @SerializedName("productAmount")
    @Expose
    private String productAmount;
    @SerializedName("isProductActive")
    @Expose
    private Boolean isProductActive;
    @SerializedName("isOnPromo")
    @Expose
    private Boolean isOnPromo;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("numberInStock")
    @Expose
    private String numberInStock;
    @SerializedName("productColor")
    @Expose
    private String productColor;
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("productDiscountedAmount")
    @Expose
    private String productDiscountedAmount;

    public ProductImages getProductImages() {
        return productImages;
    }

    public void setProductImages(ProductImages productImages) {
        this.productImages = productImages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getIsProductActive() {
        return isProductActive;
    }

    public void setIsProductActive(Boolean isProductActive) {
        this.isProductActive = isProductActive;
    }

    public Boolean getIsOnPromo() {
        return isOnPromo;
    }

    public void setIsOnPromo(Boolean isOnPromo) {
        this.isOnPromo = isOnPromo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(String numberInStock) {
        this.numberInStock = numberInStock;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDiscountedAmount() {
        return productDiscountedAmount;
    }

    public void setProductDiscountedAmount(String productDiscountedAmount) {
        this.productDiscountedAmount = productDiscountedAmount;
    }

}
