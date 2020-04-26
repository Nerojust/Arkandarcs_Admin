package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.registration.RegistrationResponse;

public interface RegisterInterface {
    void onSuccess(RegistrationResponse registrationResponse);

    void onError(String error);

    void onErrorCode(int errorCode);
}
