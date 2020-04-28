package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.products.UpdateProductResponse;

public interface UpdateProductInterface {

    void onSuccess(UpdateProductResponse updateProductInterface);

    void onError(String error);

    void onErrorCode(int errorCode);
}
