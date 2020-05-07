package com.nerojust.arkandarcsadmin.views.products;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.products.ProductImage;
import com.nerojust.arkandarcsadmin.models.products.ProductsResponse;
import com.nerojust.arkandarcsadmin.models.products.ProductsSendObject;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.AddProductInterface;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 123;
    private TextInputEditText dialogproductName;
    private TextInputEditText dialogproductCategory;
    private TextInputEditText dialogproductColor;
    private TextInputEditText dialogproductAmount;
    private TextInputEditText dialogproductDiscountedAmount;
    private TextInputEditText dialogproductDescription;
    private TextInputEditText dialogProductQuantity;
    private Switch dialogSwitch;
    private ImageView deleteImagesFromGallery;
    private TextView addImagesTextview, imageCountTextview;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private List<String> imageStringArrayList = new ArrayList<>();
    private boolean isLive;
    private List<ProductImage> productImageList;
    private Button btnOk;
    private String path;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);


        initViews();
        iniListeners();
    }
    private void askUserForPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        boolean allGranted = report.areAllPermissionsGranted();
                        boolean anyDenied = report.isAnyPermissionPermanentlyDenied();
                        if (!anyDenied) {
                            if (allGranted) {
                                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                sessionManagerCustomer = new SessionManagerCustomer();
                                getTime();
                                initViews();
                                initListeners();
                            } else {
                                askUserForPermission();
                            }
                        } else {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
        builder.setTitle(getResources().getString(R.string.need_permission));
        builder.setMessage(getResources().getString(R.string.settings_permission_request));
        builder.setPositiveButton(getResources().getString(R.string.goto_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    @SuppressLint("NewApi")
    private void iniListeners() {

        addImagesTextview.setOnClickListener(v -> openGallery());
        deleteImagesFromGallery.setOnClickListener(v -> {
            if (uriArrayList.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_image, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                TextView btnYes = dialogView.findViewById(R.id.btn_yes);
                TextView btnNo = dialogView.findViewById(R.id.btn_no);

                btnYes.setOnClickListener(v12 -> {
                    uriArrayList.clear();
                    imageStringArrayList.clear();
                    deleteImagesFromGallery.setVisibility(View.GONE);
                    imageCountTextview.setVisibility(View.GONE);
                    alertDialog.dismiss();
                });

                btnNo.setOnClickListener(v1 -> alertDialog.dismiss());
                alertDialog.show();

            }
        });
        btnOk.setOnClickListener(v -> {
            if (isValidFields()) {
                sendDetailsToServer();
            }
        });

    }

    private void initViews() {
        dialogproductName = findViewById(R.id.productName);
        dialogproductCategory = findViewById(R.id.productCategory);
        dialogproductColor = findViewById(R.id.productColor);
        dialogproductAmount = findViewById(R.id.productAmount);
        dialogProductQuantity = findViewById(R.id.numberInStock);
        dialogproductDiscountedAmount = findViewById(R.id.productDiscountedAmount);
        dialogproductDescription = findViewById(R.id.productDescription);
        dialogSwitch = findViewById(R.id.liveSwitch);
        deleteImagesFromGallery = findViewById(R.id.deletePicture);
        addImagesTextview = findViewById(R.id.imageAddTextview);
        imageCountTextview = findViewById(R.id.numberImageTextview);
        btnOk = findViewById(R.id.saveButton);
        dialogSwitch.setChecked(isLive);
        dialogSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> isLive = isChecked);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
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
                        String rr = AppUtils.compressImage(this, imageUri);
                        Log.e("Images", "onActivityResult: " + rr);
                        uriArrayList.add(imageUri);
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (bitmap != null) {
                            byte[] imageBytes = imageToByteArray(bitmap);
                            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            //decodeStringToImage(encodedImage);

                            imageStringArrayList.add(encodedImage);

                            Log.e("products", "onActivityResult: " + encodedImage);
                        }

                        currentImageSelected++;
                    }

                    imageCountTextview.setVisibility(View.VISIBLE);
                    imageCountTextview.setText(uriArrayList.size() + " images selected ");
                    deleteImagesFromGallery.setVisibility(View.VISIBLE);

                } else if (data.getData() != null) {
                    imageUri = data.getData();
                    String rr = AppUtils.compressImage(this, imageUri);
                    Log.e("Images", "onActivityResult: " + rr);
                    uriArrayList.add(imageUri);
                    imageCountTextview.setVisibility(View.VISIBLE);
                    imageCountTextview.setText(uriArrayList.size() + " images selected ");
                    deleteImagesFromGallery.setVisibility(View.VISIBLE);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (bitmap != null) {
                        byte[] imageBytes = imageToByteArray(bitmap);
                        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        imageStringArrayList.add(encodedImage);
                    }
                }
            }
        }
    }

    private byte[] imageToByteArray(Bitmap bitmapImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        return baos.toByteArray();
    }

    private void sendDetailsToServer() {
        AppUtils.initLoadingDialog(this);

        ProductsSendObject productsSendObject = new ProductsSendObject();
        productsSendObject.setProductName(dialogproductName.getText().toString().trim());
        productsSendObject.setProductCategory(dialogproductCategory.getText().toString().trim());
        productsSendObject.setProductColor(dialogproductColor.getText().toString().trim());
        productsSendObject.setProductAmount(dialogproductAmount.getText().toString().trim());
        productsSendObject.setNumberInStock(dialogProductQuantity.getText().toString().trim());
        productsSendObject.setProductDiscountedAmount(dialogproductDiscountedAmount.getText().toString().trim());
        productsSendObject.setProductDescription(dialogproductDescription.getText().toString().trim());
        productsSendObject.setProductActive(isLive);
        if (dialogproductDiscountedAmount.getText().toString().trim().isEmpty()) {
            productsSendObject.setOnPromo(false);
        } else {
            productsSendObject.setOnPromo(true);
        }
        productImageList = new ArrayList<>();
        ProductImage productImage = new ProductImage();
        if (uriArrayList.size() > 0) {
            for (int i = 0; i < uriArrayList.size(); i++) {
                productImage.setImageString(imageStringArrayList.get(i));
                productImageList.add(productImage);
            }
        }

        productsSendObject.setProductImages(productImageList);

        Gson gson = new Gson();
        String jsonObject = gson.toJson(productsSendObject);
        AppUtils.getSessionManagerInstance().setAddProductJson(jsonObject);

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.addNewProduct(productsSendObject, new AddProductInterface() {

            @Override
            public void onSuccess(ProductsResponse productsResponse) {
                Toast.makeText(AddProductActivity.this, productsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                finish();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AddProductActivity.this, error, Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(AddProductActivity.this, errorCode + "", Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }
        });
    }


    private boolean isValidFields() {
        if (Objects.requireNonNull(dialogproductName.getText()).toString().trim().isEmpty()) {
            dialogproductName.requestFocus();
            Toast.makeText(this, "Product name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogproductCategory.getText().toString().trim().isEmpty()) {
            dialogproductCategory.requestFocus();
            Toast.makeText(this, "Product category is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogproductColor.getText().toString().trim().isEmpty()) {
            dialogproductColor.requestFocus();
            Toast.makeText(this, "Product color is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogProductQuantity.getText().toString().trim().isEmpty()) {
            dialogProductQuantity.requestFocus();
            Toast.makeText(this, "Product quantity is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dialogproductAmount.getText().toString().trim().isEmpty()) {
            dialogproductAmount.requestFocus();
            Toast.makeText(this, "Product amount is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dialogproductDescription.getText().toString().trim().isEmpty()) {
            dialogproductDescription.requestFocus();
            Toast.makeText(this, "Product description is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (imageStringArrayList.size() <= 0) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtils.getSessionManagerInstance().setFromAddProducts(true);
    }

    private String compressCustomerImageToLosslessFormatAgain(String imageUri) {
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(Objects.requireNonNull(scaledBitmap));
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case 6:
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                    break;
                case 3:
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                    break;
                case 8:
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                    break;
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out;
        String filename = path;
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }
}
