package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;

import java.util.List;

public interface ProductInterface {

    void onSuccess(List<ProductsResponse> productsResponse);

    void onError(String error);

    void onErrorCode(int errorCode);
}
