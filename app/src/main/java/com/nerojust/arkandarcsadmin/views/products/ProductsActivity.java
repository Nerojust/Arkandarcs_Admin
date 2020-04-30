package com.nerojust.arkandarcsadmin.views.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.adapters.ProductAdapter;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.ProductInterface;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextInputEditText dialogproductName;
    private TextInputEditText dialogproductCategory;
    private TextInputEditText dialogproductColor;
    private TextInputEditText dialogproductAmount;
    private TextInputEditText dialogproductDiscountedAmount;
    private TextInputEditText dialogproductDescription;
    private TextInputEditText dialogProductQuantity;
    private Switch dialogSwitch;

    private boolean isLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        initViews();
        initListeners();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_products);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

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

    private void addNewProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.add_product_layout, viewGroup, false);
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


        btnOk.setOnClickListener(v -> {

            if (isValidFields()) sendDetailsToServer();
        });
        alertDialog.show();

    }

    private void sendDetailsToServer() {

    }

    private boolean isValidFields() {
        return false;
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

                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                AppUtils.showDialog(error, ProductsActivity.this);
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

    private void initListeners() {

    }
}
