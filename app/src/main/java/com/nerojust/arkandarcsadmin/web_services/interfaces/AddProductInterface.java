package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;

public interface AddProductInterface {

    void onSuccess(ProductsResponse productsResponse);

    void onError(String error);

    void onErrorCode(int errorCode);
}
