package com.nerojust.arkandarcsadmin.admin.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.textfield.TextInputEditText;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.admin.views.AdminDashBoardActivity;
import com.nerojust.arkandarcsadmin.admin.views.register.AdminRegisterActivity;
import com.nerojust.arkandarcsadmin.models.login.LoginResponse;
import com.nerojust.arkandarcsadmin.models.login.LoginSendObject;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.utils.SessionManager;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.LoginInterface;

import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {
    private TextInputEditText emailEdittext, passwordEdittext;
    private Button loginButton, registerButton;
    private TextView resetPasswordTextview;
    private String retrievedEmail;
    private String retrievedPassword;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        sessionManager = AppUtils.getSessionManagerInstance();
        String createProduct = AppUtils.getSessionManagerInstance().getAddProductJson();

        initAds();
        initViews();
        initListeners();
    }

    private void initAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3223394163127231/2951862866");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void initViews() {
        emailEdittext = findViewById(R.id.email);
        passwordEdittext = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        resetPasswordTextview = findViewById(R.id.resetPasswordTextview);
    }

    private void initListeners() {
        loginButton.setOnClickListener(v -> {
            if (isValidFields()) {
                retrievedEmail = emailEdittext.getText().toString().trim();
                retrievedPassword = passwordEdittext.getText().toString().trim();

                performNetworkRequest();
            }
        });
        registerButton.setOnClickListener(v -> gotoRegisterActivity());
    }

    private void gotoRegisterActivity() {
        Intent intent = new Intent(this, AdminRegisterActivity.class);
        startActivity(intent);
    }

    private void performNetworkRequest() {
        AppUtils.initLoadingDialog(this);

        LoginSendObject loginSendObject = new LoginSendObject();
        loginSendObject.setEmail(retrievedEmail);
        loginSendObject.setPassword(retrievedPassword);
//        loginSendObject.setEmail("nerojust2@gmail.com");
//        loginSendObject.setPassword("12345");

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.loginInUser(loginSendObject, new LoginInterface() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                //Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                gotoDashboardActivity(loginResponse);

                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AdminLoginActivity.this, error, Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(AdminLoginActivity.this, String.valueOf(errorCode), Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }
        });
    }

    private void gotoDashboardActivity(LoginResponse response) {
        Intent intent = new Intent(this, AdminDashBoardActivity.class);
        sessionManager.setFirstNameFromLogin(response.getResults().get(0).getFirstName());
        sessionManager.setToken(response.getResults().get(0).getToken());
        startActivity(intent);
    }

    private boolean isValidFields() {
        if (Objects.requireNonNull(emailEdittext.getText()).toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.username_is_required), Toast.LENGTH_SHORT).show();
            emailEdittext.requestFocus();
            return false;
        }
        if (emailEdittext.getText().toString().length() < 3) {
            Toast.makeText(this, getResources().getString(R.string.username_is_too_short), Toast.LENGTH_SHORT).show();
            emailEdittext.requestFocus();
            return false;
        }
        if (passwordEdittext.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.password_is_required), Toast.LENGTH_SHORT).show();
            passwordEdittext.requestFocus();
            return false;
        }

        if (passwordEdittext.getText().toString().trim().length() < 5) {
            Toast.makeText(this, getResources().getString(R.string.password_is_too_short), Toast.LENGTH_SHORT).show();
            passwordEdittext.requestFocus();
            return false;
        }

        return true;
    }

    //    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(getResources().getString(R.string.do_you_want_to_exit))
//                .setCancelable(false)
//                .setPositiveButton(getResources().getString(R.string.yes), (dialog, id) -> {
//                    finishAffinity();
//                })
//                .setNegativeButton(getResources().getString(R.string.no), (dialog, id) -> dialog.cancel());
//        AlertDialog alert = builder.create();
//        alert.show();
//
//    }
//
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_out);
    }
}
