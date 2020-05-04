package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.products.DeleteProductResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface DeleteInterface {

    @DELETE("products/{productId}")
    Call<DeleteProductResponse> deleteOneProduct(@Path("productId") String productId);

}
