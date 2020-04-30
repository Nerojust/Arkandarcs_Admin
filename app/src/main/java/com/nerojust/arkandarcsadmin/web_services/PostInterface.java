package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.login.LoginResponse;
import com.nerojust.arkandarcsadmin.models.login.LoginSendObject;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.models.products.ProductsSendObject;
import com.nerojust.arkandarcsadmin.models.registration.RegistrationResponse;
import com.nerojust.arkandarcsadmin.models.registration.RegistrationSendObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostInterface {
    @POST("register")
    Call<RegistrationResponse> createNewUser(@Body RegistrationSendObject registrationSendObject);

    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginSendObject loginSendObject);

    @POST("products")
    Call<ProductsResponse> addNewProduct(@Body ProductsSendObject productsSendObject);

}
