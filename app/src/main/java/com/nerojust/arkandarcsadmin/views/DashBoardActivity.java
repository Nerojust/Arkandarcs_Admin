package com.nerojust.arkandarcsadmin.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.views.login.LoginActivity;
import com.nerojust.arkandarcsadmin.views.orders.OrdersActivity;
import com.nerojust.arkandarcsadmin.views.products.ProductsActivity;
import com.nerojust.arkandarcsadmin.views.users.UsersActivity;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        TextView nameTextview = findViewById(R.id.nameTextview);
        String firstName = getIntent().getStringExtra("first_name");
        nameTextview.setText("Welcome " + firstName);

        findViewById(R.id.productsButton).setOnClickListener(v -> startActivity(new Intent(this, ProductsActivity.class)));
        findViewById(R.id.usersButton).setOnClickListener(v -> startActivity(new Intent(this, UsersActivity.class)));
        findViewById(R.id.ordersButton).setOnClickListener(v -> startActivity(new Intent(this, OrdersActivity.class)));
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
                    this.onSuperBackPressed();
                })
                .setNegativeButton(getResources().getString(R.string.no), (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();

    }

    private void onSuperBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_out);
    }
}
