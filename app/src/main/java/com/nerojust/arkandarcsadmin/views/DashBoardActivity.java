package com.nerojust.arkandarcsadmin.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.views.login.LoginActivity;
import com.nerojust.arkandarcsadmin.views.orders.OrdersActivity;
import com.nerojust.arkandarcsadmin.views.products.ProductsActivity;
import com.nerojust.arkandarcsadmin.views.transactions.TransactionsActivity;
import com.nerojust.arkandarcsadmin.views.users.UsersActivity;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{

    private SliderLayout sliderLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        setupWindowAnimations();
        initSlider();

        TextView nameTextview = findViewById(R.id.nameTextview);
        String firstName = getIntent().getStringExtra("first_name");
        nameTextview.setText("Welcome " + firstName);

        findViewById(R.id.productCardview).setOnClickListener(v -> startActivity(new Intent(this, ProductsActivity.class)));
        findViewById(R.id.usersCardview).setOnClickListener(v -> startActivity(new Intent(this, UsersActivity.class)));
        findViewById(R.id.ordersCardview).setOnClickListener(v -> startActivity(new Intent(this, OrdersActivity.class)));
        findViewById(R.id.transactionCardview).setOnClickListener(v -> startActivity(new Intent(this, TransactionsActivity.class)));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning back to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT); // Use START if using right - to - left locale
        slideTransition.setDuration(600);
        getWindow().setReenterTransition(slideTransition);  // When MainActivity Re-enter the Screen
        getWindow().setExitTransition(slideTransition);     // When MainActivity Exits the Screen
        // For overlap of Re Entering Activity - MainActivity.java and Exiting TransitionActivity.java
        getWindow().setAllowReturnTransitionOverlap(false);
    }

    /**
     * initialize and customize the slider
     */
    @SuppressLint("CheckResult")
    private void initSlider() {
        sliderLayout = findViewById(R.id.slider);

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add("data:image/jpeg;base64,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");
        listName.add("Fits all styles and occassions");

        listUrl.add("https://www.dhresource.com/0x0/f2/albu/g10/M00/CC/A7/rBVaWVxyKEWAXL4YAAGb0e3wXRU554.jpg");
        listName.add("Stylish and fit");

        listUrl.add("https://www.onshopie.com/image/cache/catalog/x6watch/X6-smartwatch-watch-reloj-inteligente-relogio-Tracker-Monitor-Blood-watches-montre-smart-kids-akilli-saatler-relogios-450x450.jpg");
        listName.add("Very affordable");

        listUrl.add("https://5.imimg.com/data5/NU/VB/ZB/SELLER-50504593/smart-watch-500x500.jpeg");
        listName.add("Premium smartwatches");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerInside();

/*
loop through the urls and images and set them
 */
        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            if (sliderView != null) {
                sliderLayout.addSlider(sliderView);
            }
        }

        // set Slider Transition Animation
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Top);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        sliderLayout.stopCyclingWhenTouch(false);
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
    @Override
    protected void onResume() {
        super.onResume();
        sliderLayout.startAutoCycle();
    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderLayout.stopAutoCycle();
        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
//        Uri uri = Uri.parse(getString(R.string.ntel_url));
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
    }
}
