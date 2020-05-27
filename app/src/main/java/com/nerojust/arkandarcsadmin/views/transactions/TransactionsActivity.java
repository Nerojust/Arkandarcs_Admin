package com.nerojust.arkandarcsadmin.views.transactions;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.adapters.TransactionAdapter;
import com.nerojust.arkandarcsadmin.models.transactions.TransactionResponse;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.TransactionsInterface;

public class TransactionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        initViews();
        getAllTransactions();
    }

    private void getAllTransactions() {
        AppUtils.initLoadingDialog(this);

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.getAllTransactions(new TransactionsInterface() {
            @Override
            public void onSuccess(TransactionResponse response) {
                TransactionAdapter transactionAdapter = new TransactionAdapter(response, TransactionsActivity.this);
                recyclerView.setAdapter(transactionAdapter);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(TransactionsActivity.this, error, Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(TransactionsActivity.this, errorCode + "", Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                AppUtils.dismissLoadingDialog();
            }
        });
    }

    private void initViews() {
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getAllTransactions);

        recyclerView = findViewById(R.id.recycler_transaction);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_out);
    }
}
