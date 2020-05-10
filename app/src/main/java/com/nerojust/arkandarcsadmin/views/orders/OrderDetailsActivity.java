package com.nerojust.arkandarcsadmin.views.orders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.nerojust.arkandarcsadmin.R;

import static com.nerojust.arkandarcsadmin.utils.AppUtils.decodeStringToImage;

public class OrderDetailsActivity extends AppCompatActivity {

    private Button processButton;
    private String itemId;
    private String quantity;
    private String amount;
    private String orderNumber;
    private String productId;
    private String productName;
    private String productCategory;
    private String productColor;
    private String productQuantity;
    private String status;
    private String date;
    private String paymentMethod;
    private String tax;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String country;
    private String imageString;
    private String state;
    private String lga;
    private ImageView imageView;
    private TextView productNameTextview, productQuantityTextview, productAmountTextview, statusTextview,
            taxTextview, orderNumberTextview, dateTextview, paymentMethodTextview, nameTextview, phoneNumberTextview,
            emailAddressTextview, addressTextview, busstopTextview, lgaTextview, stateTextview, countryTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        getExtras();
        initViews();
        initListeners();
    }

    private void getExtras() {
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        quantity = intent.getStringExtra("quantity");
        amount = intent.getStringExtra("amount");
        orderNumber = intent.getStringExtra("orderNumber");
        productId = intent.getStringExtra("productId");
        productName = intent.getStringExtra("productName");
        productCategory = intent.getStringExtra("productCategory");
        productColor = intent.getStringExtra("productColor");
        productQuantity = intent.getStringExtra("productQuantity");
        imageString = intent.getStringExtra("imageString");
        status = intent.getStringExtra("status");
        date = intent.getStringExtra("date");
        paymentMethod = intent.getStringExtra("paymentMethod");
        tax = intent.getStringExtra("tax");
        name = intent.getStringExtra("name");
        phoneNumber = intent.getStringExtra("phoneNumber");
        emailAddress = intent.getStringExtra("emailAddress");
        address = intent.getStringExtra("address");
        country = intent.getStringExtra("country");
        state = intent.getStringExtra("state");
        lga = intent.getStringExtra("lga");
    }

    private void initViews() {
        imageView = findViewById(R.id.image);
        productNameTextview = findViewById(R.id.productName_tv);
        productQuantityTextview = findViewById(R.id.quantity_tv);
        productAmountTextview = findViewById(R.id.amount_tv);
        statusTextview = findViewById(R.id.status_tv);
        taxTextview = findViewById(R.id.tax_tv);
        orderNumberTextview = findViewById(R.id.orderNumber_tv);
        dateTextview = findViewById(R.id.date_tv);
        paymentMethodTextview = findViewById(R.id.paymentMethod_tv);
        nameTextview = findViewById(R.id.name_tv);
        phoneNumberTextview = findViewById(R.id.phoneNumber_tv);
        emailAddressTextview = findViewById(R.id.email_tv);
        addressTextview = findViewById(R.id.address_tv);
        busstopTextview = findViewById(R.id.busstop_tv);
        lgaTextview = findViewById(R.id.lga_tv);
        stateTextview = findViewById(R.id.state_tv);
        countryTextview = findViewById(R.id.country_tv);
        processButton = findViewById(R.id.processButton);


        productNameTextview.setText(productName);
        productQuantityTextview.setText(productQuantity);
        productAmountTextview.setText(amount);
        statusTextview.setText(status);
        taxTextview.setText(tax);
        orderNumberTextview.setText(orderNumber);
        dateTextview.setText(date);
        paymentMethodTextview.setText(paymentMethod);
        nameTextview.setText(name);
        phoneNumberTextview.setText(phoneNumber);
        emailAddressTextview.setText(emailAddress);
        addressTextview.setText(address);
        lgaTextview.setText(lga);
        stateTextview.setText(state);
        countryTextview.setText(country);

        if (imageString !=null){
            if (imageString != null || imageString != "") {
                Bitmap bitmap = decodeStringToImage(imageString);
                if (bitmap != null) {
                    Glide.with(this)
                            .load(bitmap)
                            .placeholder(R.drawable.load)
                            .into(imageView);
                }
            }
        }

    }


    private void initListeners() {
        processButton.setOnClickListener(v -> {

        });
    }


}
