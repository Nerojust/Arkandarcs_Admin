package com.nerojust.arkandarcsadmin.views.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nerojust.arkandarcsadmin.R;

public class TransactionDetailsActivity extends AppCompatActivity {
    private TextView transactionidTextview, orderIdTextview, userIdTextview, productIdTextview,
            quantityTextview, amountTextview, dateTextview, nameTextview, phoneTextview,
            emailTextview, addressTextview, cityTextview, stateTextview;
    private String txnId;
    private String orderId;
    private String userId;
    private String productId;
    private String quantity;
    private String amount;
    private String name;
    private String phone;
    private String email;
    private String city;
    private String state;
    private String address;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        getExtras();
        initViews();
    }

    private void initViews() {
        transactionidTextview = findViewById(R.id.txn_idtv);
        orderIdTextview = findViewById(R.id.orderIdtv);
        userIdTextview = findViewById(R.id.userid_tv);
        productIdTextview = findViewById(R.id.productId_tv);
        quantityTextview = findViewById(R.id.quantity_tv);
        amountTextview = findViewById(R.id.amount_tv);
        dateTextview = findViewById(R.id.date_tv);
        nameTextview = findViewById(R.id.name_tv);
        phoneTextview = findViewById(R.id.phoneNumber_tv);
        emailTextview = findViewById(R.id.email_tv);
        addressTextview = findViewById(R.id.address_tv);
        cityTextview = findViewById(R.id.city_tv);
        stateTextview = findViewById(R.id.state_tv);

        transactionidTextview.setText(txnId);
        orderIdTextview.setText(orderId);
        userIdTextview.setText(userId);
        productIdTextview.setText(productId);
        quantityTextview.setText(quantity);
        amountTextview.setText(getResources().getString(R.string.naira) + amount);
        dateTextview.setText(date);
        nameTextview.setText(name);
        phoneTextview.setText(phone);
        emailTextview.setText(email);
        addressTextview.setText(address);
        cityTextview.setText(city);
        stateTextview.setText(state);
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            txnId = intent.getStringExtra("transactionId");
            orderId = intent.getStringExtra("orderId");
            userId = intent.getStringExtra("userId");
            productId = intent.getStringExtra("productId");
            quantity = intent.getStringExtra("quantity");
            amount = intent.getStringExtra("amount");
            name = intent.getStringExtra("name");
            phone = intent.getStringExtra("phoneNumber");
            email = intent.getStringExtra("emailAddress");
            city = intent.getStringExtra("city");
            state = intent.getStringExtra("state");
            address = intent.getStringExtra("address");
            date = intent.getStringExtra("date");
        }
    }
}
