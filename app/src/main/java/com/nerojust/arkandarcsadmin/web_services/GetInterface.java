package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.login_users.LoginUsersResponse;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductsSendObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GetInterface {

    @GET("login/{loginId}")
    Call<List<LoginUsersResponse>> getAllLoggedInUsers(@Path("loginId") String loginId);

    @GET("products")
    Call<ProductsResponse> getAllProducts();

    @PUT("products/{productId}")
    Call<UpdateProductResponse> editOneProduct(@Body UpdateProductsSendObject updateProductsSendObject, @Path("productId") String productId);

    @GET("admins")
    Call<List<LoginUsersResponse>> getAllAdmins();

}
