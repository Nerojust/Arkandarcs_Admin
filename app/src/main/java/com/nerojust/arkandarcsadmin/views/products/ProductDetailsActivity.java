package com.nerojust.arkandarcsadmin.views.products;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.products.ProductImage;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductsSendObject;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.UpdateProductInterface;

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
    private String productId;
    private TextInputEditText dialogproductName;
    private TextInputEditText dialogproductCategory;
    private TextInputEditText dialogproductColor;
    private TextInputEditText dialogproductAmount;
    private TextInputEditText dialogproductDiscountedAmount;
    private TextInputEditText dialogproductDescription;
    private TextInputEditText dialogProductQuantity;
    private Switch dialogSwitch;

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

    private void editProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.edit_product_layout, viewGroup, false);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        Button btnOk = view.findViewById(R.id.saveButton);

        dialogproductName = view.findViewById(R.id.productName);
        dialogproductCategory = view.findViewById(R.id.productCategory);
        dialogproductColor = view.findViewById(R.id.productColor);
        dialogproductAmount = view.findViewById(R.id.productAmount);
        dialogProductQuantity = view.findViewById(R.id.numberInStock);
        dialogproductDiscountedAmount = view.findViewById(R.id.productDiscountedAmount);
        dialogproductDescription = view.findViewById(R.id.productDescription);
        dialogSwitch = view.findViewById(R.id.liveSwitch);

        dialogSwitch.setChecked(isLive);
        dialogSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> isLive = isChecked);

        dialogproductName.setText(productName);
        dialogproductCategory.setText(productCategory);
        dialogproductColor.setText(productColor);
        dialogProductQuantity.setText(numberInStock);
        dialogproductDescription.setText(productDescription);
        dialogproductAmount.setText(productAmount);
        dialogproductDiscountedAmount.setText(productDiscountedAmount);

        btnOk.setOnClickListener(v -> {

            if (isValidFields()) sendDetailsToServer();
        });
        alertDialog.show();

    }

    private void sendDetailsToServer() {
        AppUtils.initLoadingDialog(this);
        ProductImage productImages = new ProductImage();
        productImages.setImageName("This_new_name.jpg");
        productImages.setImageUrl(productImage);

        UpdateProductsSendObject productsSendObject = new UpdateProductsSendObject();
        productsSendObject.setProductName(dialogproductName.getText().toString().trim());
        productsSendObject.setProductCategory(dialogproductCategory.getText().toString().trim());
        productsSendObject.setProductColor(dialogproductColor.getText().toString().trim());
        productsSendObject.setProductAmount(dialogproductAmount.getText().toString().trim());
        productsSendObject.setNumberInStock(dialogProductQuantity.getText().toString().trim());
        productsSendObject.setProductDiscountedAmount(dialogproductDiscountedAmount.getText().toString().trim());
        productsSendObject.setProductDescription(dialogproductDescription.getText().toString().trim());
        productsSendObject.setProductImages(productImages);
        productsSendObject.setIsProductActive(isLive);
        if (dialogproductDiscountedAmount.getText().toString().trim().isEmpty()){
            productsSendObject.setIsOnPromo(false);
        }else {
            productsSendObject.setIsOnPromo(true);
        }
        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.editOneProduct(productsSendObject, new UpdateProductInterface() {

            @Override
            public void onSuccess(UpdateProductResponse updateProductResponse) {
                Toast.makeText(ProductDetailsActivity.this, updateProductResponse.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(ProductDetailsActivity.this, errorCode + "", Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }
        });
    }

    private boolean isValidFields() {
        if (dialogproductName.getText().toString().trim().isEmpty()) {
            dialogproductName.requestFocus();
            Toast.makeText(this, "Product name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogproductCategory.getText().toString().trim().isEmpty()) {
            dialogproductCategory.requestFocus();
            Toast.makeText(this, "Product category is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogproductColor.getText().toString().trim().isEmpty()) {
            dialogproductColor.requestFocus();
            Toast.makeText(this, "Product color is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogProductQuantity.getText().toString().trim().isEmpty()) {
            dialogProductQuantity.requestFocus();
            Toast.makeText(this, "Product quantity is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogproductAmount.getText().toString().trim().isEmpty()) {
            dialogproductAmount.requestFocus();
            Toast.makeText(this, "Product amount is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dialogproductDescription.getText().toString().trim().isEmpty()) {
            dialogproductDescription.requestFocus();
            Toast.makeText(this, "Product description is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initListeners() {
        editButton.setOnClickListener(v -> {
            editProduct();
        });
    }

    private void getExtrasFromIntent() {
        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        AppUtils.getSessionManagerInstance().setProductId(productId);
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
