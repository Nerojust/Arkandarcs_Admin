package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.login_users.LoginUsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetInterface {

    @GET("api/login/{loginId}")
    Call<List<LoginUsersResponse>> getAllLoggedInUsers(@Path("loginId") String loginId);

}
