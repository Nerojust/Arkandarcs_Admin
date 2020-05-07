package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.orders.OrdersResponse;

public interface OrdersInterface {
    void onSuccess(OrdersResponse ordersResponse);

    void onError(String error);

    void onErrorCode(int errorCode);

}
