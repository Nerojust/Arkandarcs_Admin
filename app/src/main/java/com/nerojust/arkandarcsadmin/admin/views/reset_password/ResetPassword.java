package com.nerojust.arkandarcsadmin.admin.views.reset_password;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.utils.AppUtils;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> {
            AppUtils.initLoadingDialog(this);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Toast.makeText(this, "An error has occurred, please try again later", Toast.LENGTH_SHORT).show();
            AppUtils.dismissLoadingDialog();
        });
    }
}