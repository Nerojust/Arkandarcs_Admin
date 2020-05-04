package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.products.DeleteProductResponse;

public interface DeleteInterfaceR {

    void onSuccess(DeleteProductResponse deleteProductResponse);

    void onError(String error);

    void onErrorCode(int errorCode);
}
