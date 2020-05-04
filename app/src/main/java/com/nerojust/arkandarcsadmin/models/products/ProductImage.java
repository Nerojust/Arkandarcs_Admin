package com.nerojust.arkandarcsadmin.models.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImage {

    @SerializedName("imageName")
    @Expose
    private String imageName;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("imageString")
    @Expose
    private String imageString;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
