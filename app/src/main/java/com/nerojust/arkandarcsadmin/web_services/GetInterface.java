package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.login_users.LoginUsersResponse;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetInterface {

    @GET("login/{loginId}")
    Call<List<LoginUsersResponse>> getAllLoggedInUsers(@Path("loginId") String loginId);

    @GET("products")
    Call<ProductsResponse> getAllProducts();

    @GET("products/{productId}")
    Call<List<LoginUsersResponse>> getSingleProduct(@Path("productId") String productId);

    @GET("admins")
    Call<List<ProductsResponse>> getAllAdmins();

}
