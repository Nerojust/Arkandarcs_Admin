package com.nerojust.arkandarcsadmin.models.products;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProductsSendObject {

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
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("productDiscountedAmount")
    @Expose
    private String productDiscountedAmount;
    @SerializedName("isProductActive")
    @Expose
    private Boolean isProductActive;
    @SerializedName("isOnPromo")
    @Expose
    private Boolean isOnPromo;
    @SerializedName("productImages")
    @Expose
    private ProductImage productImages;

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

    public ProductImage getProductImages() {
        return productImages;
    }

    public void setProductImages(ProductImage productImages) {
        this.productImages = productImages;
    }

}
