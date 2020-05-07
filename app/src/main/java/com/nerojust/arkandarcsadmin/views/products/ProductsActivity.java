package com.nerojust.arkandarcsadmin.views.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.adapters.ProductAdapter;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.utils.SessionManager;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.ProductInterface;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);

        initViews();
    }

    private void initViews() {
        sessionManager = AppUtils.getSessionManagerInstance();
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
                startActivity(new Intent(this, AddProductActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                finish();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(ProductsActivity.this, errorCode + "", Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                AppUtils.dismissLoadingDialog();
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        if (sessionManager.getFromAddProducts()) {
            getAllProducts();
            sessionManager.setFromAddProducts(false);
        } else if (sessionManager.getFromDetailsProducts()) {
            getAllProducts();
            sessionManager.setFromDetailsProducts(false);
        }
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_out);
        super.onStart();
    }
}
