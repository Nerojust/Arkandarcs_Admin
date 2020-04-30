package com.nerojust.arkandarcsadmin.views.products;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.adapters.ProductAdapter;
import com.nerojust.arkandarcsadmin.models.products.ProductImage;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.models.products.ProductsSendObject;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.views.login.LoginActivity;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.AddProductInterface;
import com.nerojust.arkandarcsadmin.web_services.interfaces.ProductInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ProductsActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 123;
    private RecyclerView recyclerView;
    private TextInputEditText dialogproductName;
    private TextInputEditText dialogproductCategory;
    private TextInputEditText dialogproductColor;
    private TextInputEditText dialogproductAmount;
    private TextInputEditText dialogproductDiscountedAmount;
    private TextInputEditText dialogproductDescription;
    private TextInputEditText dialogProductQuantity;
    private Switch dialogSwitch;
    private TextView addImagesTextview, imageCountTextview;
    private Button chooseButton, uploadButton, nextButton;
    private TextView alertTextview;
    private EditText tableNameEditext;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private int uploadCount = 0;
    private boolean isLive;
    private AlertDialog alertDialog;
    private List<ProductImage> productImageList;
    private ProductImage productImages;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        initViews();
    }

    private void initViews() {
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getAllProducts);

        recyclerView = findViewById(R.id.recycler_products);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        productImages = new ProductImage();
        productImageList = new ArrayList<>();

        getAllProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_product:
                addNewProduct();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NewApi")
    private void addNewProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.add_product_layout, viewGroup, false);
        builder.setView(view);
        alertDialog = builder.create();

        Button btnOk = view.findViewById(R.id.saveButton);

        dialogproductName = view.findViewById(R.id.productName);
        dialogproductCategory = view.findViewById(R.id.productCategory);
        dialogproductColor = view.findViewById(R.id.productColor);
        dialogproductAmount = view.findViewById(R.id.productAmount);
        dialogProductQuantity = view.findViewById(R.id.numberInStock);
        dialogproductDiscountedAmount = view.findViewById(R.id.productDiscountedAmount);
        dialogproductDescription = view.findViewById(R.id.productDescription);
        dialogSwitch = view.findViewById(R.id.liveSwitch);
        addImagesTextview = view.findViewById(R.id.imageAddTextview);
        imageCountTextview = view.findViewById(R.id.numberImageTextview);

        dialogSwitch.setChecked(isLive);
        dialogSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> isLive = isChecked);

        addImagesTextview.setOnClickListener(v -> openGallery());

        btnOk.setOnClickListener(v -> {

            if (isValidFields()) {
                uploadImagesToServer();
            }
        });
        alertDialog.show();

    }

    private void storeLinkUrl(String url, String imageName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(dialogproductName.getText().toString().trim());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("imageLink", url);
        hashMap.put("imageName", imageName);
        databaseReference.push().setValue(hashMap);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri imageUri;
                if (Objects.requireNonNull(data).getClipData() != null) {
                    int dataClipCount = Objects.requireNonNull(data.getClipData()).getItemCount();

                    int currentImageSelected = 0;
                    while (currentImageSelected < dataClipCount) {
                        imageUri = data.getClipData().getItemAt(currentImageSelected).getUri();
                        uriArrayList.add(imageUri);
                        currentImageSelected++;
                    }

                    imageCountTextview.setVisibility(View.VISIBLE);
                    imageCountTextview.setText(uriArrayList.size() + " images selected ");
                    //addImagesTextview.setVisibility(View.GONE);
                } else if (data.getData() != null) {
                    imageUri = data.getData();
                    uriArrayList.add(imageUri);
                    imageCountTextview.setVisibility(View.VISIBLE);
                    imageCountTextview.setText(uriArrayList.size() + " images selected ");
                    //addImagesTextview.setVisibility(View.GONE);
                }
            }
        }
    }

    private void sendDetailsToServer() {
        ProductsSendObject productsSendObject = new ProductsSendObject();

        productsSendObject.setProductName(dialogproductName.getText().toString().trim());
        productsSendObject.setProductCategory(dialogproductCategory.getText().toString().trim());
        productsSendObject.setProductColor(dialogproductColor.getText().toString().trim());
        productsSendObject.setProductAmount(dialogproductAmount.getText().toString().trim());
        productsSendObject.setNumberInStock(dialogProductQuantity.getText().toString().trim());
        productsSendObject.setProductDiscountedAmount(dialogproductDiscountedAmount.getText().toString().trim());
        productsSendObject.setProductDescription(dialogproductDescription.getText().toString().trim());
        productsSendObject.setProductImages(productImageList);
        productsSendObject.setProductActive(isLive);
        if (dialogproductDiscountedAmount.getText().toString().trim().isEmpty()) {
            productsSendObject.setOnPromo(false);
        } else {
            productsSendObject.setOnPromo(true);
        }

        Gson gson = new Gson();
        String jsonObject = gson.toJson(productsSendObject);
        AppUtils.getSessionManagerInstance().setAddProductJson(jsonObject);

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.addNewProduct(productsSendObject, new AddProductInterface() {

            @Override
            public void onSuccess(ProductsResponse productsResponse) {
                Toast.makeText(ProductsActivity.this, productsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ProductsActivity.this, error, Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(ProductsActivity.this, errorCode + "", Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }
        });
    }

    private void uploadImagesToServer() {
        AppUtils.initLoadingDialog(this);

        StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("images");

        for (uploadCount = 0; uploadCount < uriArrayList.size(); uploadCount++) {
            Uri individualImage = uriArrayList.get(uploadCount);

            Cursor cursor = getContentResolver().query(individualImage, null, null, null, null);
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            final StorageReference imageName = imageFolder.child(cursor.getString(nameIndex));

            imageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            storeLinkUrl(url, cursor.getString(nameIndex));

                            productImages.setImageName(cursor.getString(nameIndex));
                            productImages.setImageUrl(url);
                            productImageList.add(productImages);
                            Toast.makeText(ProductsActivity.this, "upload count is " + uploadCount + " and array is " + uriArrayList.size(), Toast.LENGTH_SHORT).show();
                            if (uploadCount == uriArrayList.size()) {
                                Toast.makeText(ProductsActivity.this, "Image/s Uploaded successfully", Toast.LENGTH_SHORT).show();

                                new Handler().postDelayed(ProductsActivity.this::sendDetailsToServer, 1200);

                            }
                        }
                    });
                }
            }).addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }

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


    private void getAllProducts() {
        AppUtils.initLoadingDialog(this);

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.getAllProducts(new ProductInterface() {
            @Override
            public void onSuccess(ProductsResponse productsResponse) {
                ProductAdapter productAdapter = new ProductAdapter(productsResponse, getApplicationContext());
                recyclerView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                AppUtils.showDialog(error, ProductsActivity.this);
                Toast.makeText(ProductsActivity.this, error, Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(ProductsActivity.this, errorCode + "", Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                AppUtils.dismissLoadingDialog();
            }
        });
    }


    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.do_you_want_to_logout))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), (dialog, id) -> {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    AppUtils.getSessionManagerInstance().logout();
                    startActivity(intent);
                    ProductsActivity.this.onSuperBackPressed();
                })
                .setNegativeButton(getResources().getString(R.string.no), (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();

    }

    private void onSuperBackPressed() {
        super.onBackPressed();
    }


}
