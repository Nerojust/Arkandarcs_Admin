package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.login.LoginResponse;

public interface LoginInterface {

    void onSuccess(LoginResponse loginResponse);

    void onError(String error);

    void onErrorCode(int errorCode);

}
