package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.products.UpdateProductResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductsSendObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PutInterface {
    @PUT("products/{productId}")
    Call<UpdateProductResponse> editOneProduct(@Body UpdateProductsSendObject updateProductsSendObject, @Path("productId") String productId);
}
