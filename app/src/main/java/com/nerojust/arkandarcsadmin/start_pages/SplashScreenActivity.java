package com.nerojust.arkandarcsadmin.start_pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.admin.views.login.AdminLoginActivity;
import com.nerojust.arkandarcsadmin.customer.login.CustomerLoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }, 2500);
    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
