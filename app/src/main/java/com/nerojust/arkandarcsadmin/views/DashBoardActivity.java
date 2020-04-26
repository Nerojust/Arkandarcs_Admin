package com.nerojust.arkandarcsadmin.views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nerojust.arkandarcsadminapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DashBoardActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 123;
    private Button chooseButton, uploadButton;
    private TextView alertTextview;
    private EditText tableNameEditext;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private int uploadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        initViews();
        initListeners();
    }

    private void initViews() {
        chooseButton = findViewById(R.id.chooseButton);
        uploadButton = findViewById(R.id.uploadButton);
        alertTextview = findViewById(R.id.statusTextview);
        tableNameEditext = findViewById(R.id.editext);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading, please wait...");
    }

    private void initListeners() {
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tableNameEditext.getText().toString().isEmpty()) {
                    progressDialog.show();
                    StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("images");

                    for (uploadCount = 0; uploadCount < uriArrayList.size(); uploadCount++) {
                        Uri individualImage = uriArrayList.get(uploadCount);
                        final StorageReference imageName = imageFolder.child("image" + individualImage.getLastPathSegment());

                        imageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = String.valueOf(uri);
                                        storeLinkUril(url);
                                        progressDialog.dismiss();
                                        alertTextview.setText("Upload Successful");
                                        uploadButton.setVisibility(View.GONE);
                                        chooseButton.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DashBoardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(DashBoardActivity.this, "Table name for images is required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeLinkUril(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(tableNameEditext.getText().toString().trim());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("imageLink", url);
        databaseReference.push().setValue(hashMap);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //for 1 image remove below line
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
                    alertTextview.setVisibility(View.VISIBLE);
                    alertTextview.setText("You have selected " + uriArrayList.size() + " images ");
                    chooseButton.setVisibility(View.GONE);
                    uploadButton.setVisibility(View.VISIBLE);
                } else if (data.getData() != null) {
                    imageUri = data.getData();
                    uriArrayList.add(imageUri);
                    alertTextview.setVisibility(View.VISIBLE);
                    alertTextview.setText("You have selected " + uriArrayList.size() + " images ");
                    chooseButton.setVisibility(View.GONE);
                    uploadButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
