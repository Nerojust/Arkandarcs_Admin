package com.nerojust.arkandarcsadmin.views.products;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.nerojust.arkandarcsadmin.R;

public class ProductDetailsActivity extends AppCompatActivity {
    private String productName;
    private String productCategory;
    private String productAmount;
    private String productColor;
    private String productDiscountedAmount;
    private String productDescription;
    private String numberInStock;
    private boolean isLive;
    private Button editButton;
    private String productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getExtrasFromIntent();
        initViews();
        initListeners();
    }

    private void initViews() {
        TextView productNameTextview = findViewById(R.id.productName);
        TextView productCategoryTextview = findViewById(R.id.productCategory);
        TextView productAmountTextview = findViewById(R.id.productAmount);
        productAmountTextview.setPaintFlags((productAmountTextview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));
        TextView numberInStockTextview = findViewById(R.id.numberInStock);
        TextView productColorTextview = findViewById(R.id.color);
        TextView productDescriptionTextview = findViewById(R.id.productDescription);
        TextView productDiscountedAmountTextview = findViewById(R.id.productDiscountedAmount);
        TextView productStatusTextview = findViewById(R.id.isLive);
        editButton = findViewById(R.id.editProductButton);
        ImageView productImageView = findViewById(R.id.productImage);

        productNameTextview.setText(productName);
        productCategoryTextview.setText(productCategory);
        productColorTextview.setText(productColor);
        productAmountTextview.setText(productAmount);
        productDescriptionTextview.setText(productDescription);
        productDiscountedAmountTextview.setText(productDiscountedAmount);
        if (numberInStock == null) {
            numberInStock = "None";
        }
        numberInStockTextview.setText(numberInStock + " in stock");
        if (isLive == false) {
            productStatusTextview.setText("Inactive");
            productStatusTextview.setTextColor(getResources().getColor(R.color.arkandarcs_gray_color));

        } else {
            productStatusTextview.setText("Live");
            productStatusTextview.setTextColor(getResources().getColor(R.color.arkandarcs_green));
        }

        Glide.with(this)
                .load(productImage)
                .placeholder(R.drawable.load)
                .into(productImageView);
    }


    private void initListeners() {
        editButton.setOnClickListener(v -> Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show());
    }

    private void getExtrasFromIntent() {
        Intent intent = getIntent();

        productName = intent.getStringExtra("productName");
        productCategory = intent.getStringExtra("productCategory");
        productAmount = intent.getStringExtra("productAmount");
        productColor = intent.getStringExtra("productColor");
        productDescription = intent.getStringExtra("productDescription");
        productDiscountedAmount = intent.getStringExtra("productDiscountedAmount");
        numberInStock = intent.getStringExtra("numberInStock");
        isLive = intent.getBooleanExtra("isLive", false);
        productImage = intent.getStringExtra("productImage");

    }
}
