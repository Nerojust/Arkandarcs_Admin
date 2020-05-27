package com.nerojust.arkandarcsadmin.web_services;

import com.nerojust.arkandarcsadmin.models.orders.OrdersResponse;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductsSendObject;
import com.nerojust.arkandarcsadmin.models.transactions.TransactionResponse;
import com.nerojust.arkandarcsadmin.models.users.UsersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetInterface {

    @GET("products")
    Call<ProductsResponse> getAllProducts();

    @PUT("products/{productId}")
    Call<UpdateProductResponse> editOneProduct(@Body UpdateProductsSendObject updateProductsSendObject, @Path("productId") String productId);

    @GET("users")
    Call<UsersResponse> getAllUsers();

    @GET("users/{userId}")
    Call<UsersResponse> getOneUser(@Path("userId") String userId);

    @GET("orders")
    Call<OrdersResponse> getAllOrders();

    @GET("transactions")
    Call<TransactionResponse> getAllTransactions();

    @GET("orders/")
    Call<OrdersResponse> getOneStatus(@Query("orderStatus") String status);

}
