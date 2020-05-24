package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.orders.OrdersResponse;
import com.nerojust.arkandarcsadmin.models.orders.OrdersSendObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PatchInterface {
    @PUT("products/{productId}")
    Call<OrdersResponse> editOrder(@Body OrdersSendObject ordersSendObject, @Path("orderId") String orderId);
}
