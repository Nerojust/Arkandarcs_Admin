package com.nerojust.arkandarcsadmin.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nerojust.arkandarcsadminapp.R;
import com.nerojust.arkandarcsadminapp.models.login.LoginResponse;
import com.nerojust.arkandarcsadminapp.models.login.LoginSendObject;
import com.nerojust.arkandarcsadminapp.utils.AppUtils;
import com.nerojust.arkandarcsadminapp.views.DashBoardActivity;
import com.nerojust.arkandarcsadminapp.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadminapp.web_services.interfaces.LoginInterface;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText emailEdittext, passwordEdittext;
    private Button loginButton;
    private String retrievedEmail;
    private String retrievedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initViews();
        initListeners();
    }

    private void initViews() {
        emailEdittext = findViewById(R.id.email);
        passwordEdittext = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
    }

    private void initListeners() {

        loginButton.setOnClickListener(v -> {
            if (isValidFields()) {
                retrievedEmail = emailEdittext.getText().toString().trim();
                retrievedPassword = passwordEdittext.getText().toString().trim();

                performNetworkRequest();

            }
        });
    }

    private void performNetworkRequest() {
        AppUtils.initLoadingDialog(this);

        LoginSendObject loginSendObject = new LoginSendObject();
        loginSendObject.setEmail(retrievedEmail);
        loginSendObject.setPassword(retrievedPassword);

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.loginInUser(loginSendObject, new LoginInterface() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                gotoDashboardActivity();

                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(LoginActivity.this, String.valueOf(errorCode), Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }
        });
    }

    private void gotoDashboardActivity() {
        Intent intent = new Intent(this, DashBoardActivity.class);
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
}
