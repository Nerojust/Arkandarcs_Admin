package com.nerojust.arkandarcsadmin.admin.views.reset_password;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.utils.AppUtils;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {

    private EditText resetPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPasswordEt = findViewById(R.id.resetPasswordEt);
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> {
            if (isValidFields()) {
                AppUtils.initLoadingDialog(this);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Toast.makeText(this, "An error has occurred, please try again later", Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }
        });
    }

    private boolean isValidFields() {
        if (Objects.requireNonNull(resetPasswordEt.getText()).toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.username_is_required), Toast.LENGTH_SHORT).show();
            resetPasswordEt.requestFocus();
            return false;
        }
        if (resetPasswordEt.getText().toString().length() < 3) {
            Toast.makeText(this, getResources().getString(R.string.username_is_too_short), Toast.LENGTH_SHORT).show();
            resetPasswordEt.requestFocus();
            return false;
        }

        return true;
    }
}