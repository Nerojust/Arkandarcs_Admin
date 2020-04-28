package com.nerojust.arkandarcsadmin.views.products;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.products.ProductImages;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductResponse;
import com.nerojust.arkandarcsadmin.models.products.UpdateProductsSendObject;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.UpdateProductInterface;
import com.rengwuxian.materialedittext.MaterialEditText;

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
    private MaterialEditText dialogproductName;
    private MaterialEditText dialogproductCategory;
    private MaterialEditText dialogproductColor;
    private MaterialEditText dialogproductAmount;
    private MaterialEditText dialogproductDiscountedAmount;
    private MaterialEditText dialogproductDescription;
    private MaterialEditText dialogProductQuantity;

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
        View view = LayoutInflater.from(this).inflate(R.layout.edit_product_layout, null);
        new MaterialStyledDialog.Builder(this)
                .setTitle("Edit Product")
                .setDescription("Edit details of this product")
                .setCustomView(view)
                .setIcon(R.drawable.ic_launcher_background)
                .setNegativeText("Cancel")
                .onNegative((dialog, which) -> dialog.dismiss())
                .setPositiveText("Submit")
                .onPositive((dialog, which) -> {
                    dialogproductName = view.findViewById(R.id.productName);
                    dialogproductCategory = view.findViewById(R.id.productCategory);
                    dialogproductColor = view.findViewById(R.id.productColor);
                    dialogproductAmount = view.findViewById(R.id.productAmount);
                    dialogProductQuantity = view.findViewById(R.id.numberInStock);
                    dialogproductDiscountedAmount = view.findViewById(R.id.productDiscountedAmount);
                    dialogproductDescription = view.findViewById(R.id.productDescription);
                    if (isValidFields()) {
                        sendDetailsToServer();
                    }
                }).show();
    }

    private void sendDetailsToServer() {
        AppUtils.initLoadingDialog(this);
        ProductImages productImages = new ProductImages();
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
        productsSendObject.setIsProductActive(true);
        productsSendObject.setIsOnPromo(true);

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
            return false;
        }
        if (dialogproductCategory.getText().toString().trim().isEmpty()) {
            dialogproductCategory.requestFocus();
            return false;
        }
        if (dialogproductColor.getText().toString().trim().isEmpty()) {
            dialogproductColor.requestFocus();
            return false;
        }
        if (dialogProductQuantity.getText().toString().trim().isEmpty()) {
            dialogProductQuantity.requestFocus();
            return false;
        }
        if (dialogproductAmount.getText().toString().trim().isEmpty()) {
            dialogproductAmount.requestFocus();
            return false;
        }
        if (dialogproductDiscountedAmount.getText().toString().trim().isEmpty()) {
            dialogproductDiscountedAmount.requestFocus();
            return false;
        }
        if (dialogproductDescription.getText().toString().trim().isEmpty()) {
            dialogproductDescription.requestFocus();
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
