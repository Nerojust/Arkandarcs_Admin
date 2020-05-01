package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.users.UsersResponse;

public interface UsersInterface {
    void onSuccess(UsersResponse loginUsersResponse);

    void onError(String error);

    void onErrorCode(int errorCode);

}
