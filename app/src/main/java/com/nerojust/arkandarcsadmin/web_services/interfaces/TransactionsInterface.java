package com.nerojust.arkandarcsadmin.web_services.interfaces;

import com.nerojust.arkandarcsadmin.models.transactions.TransactionResponse;

public interface TransactionsInterface {
    void onSuccess(TransactionResponse transactionResponse);

    void onError(String error);

    void onErrorCode(int errorCode);

}
