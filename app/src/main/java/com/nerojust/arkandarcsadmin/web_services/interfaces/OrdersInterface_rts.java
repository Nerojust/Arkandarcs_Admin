package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.orders.rts.RTSOrdersResponse;

public interface OrdersInterface_rts {
    void onSuccess(RTSOrdersResponse rtsOrdersResponse);

    void onError(String error);

    void onErrorCode(int errorCode);

}
