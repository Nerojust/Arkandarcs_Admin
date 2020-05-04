package com.nerojust.arkandarcsadmin.views.register;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.textfield.TextInputEditText;
import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.models.registration.Location;
import com.nerojust.arkandarcsadmin.models.registration.RegistrationResponse;
import com.nerojust.arkandarcsadmin.models.registration.RegistrationSendObject;
import com.nerojust.arkandarcsadmin.utils.AppUtils;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.RegisterInterface;

public class RegisterActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private TextInputEditText firstName, lastName, address, nearestBustop,
            emailAddress, username, password, re_enterpassword, phoneNumber;
    private TextView backToLoginTextview;
    private Button signupButton;
    private String gender = "";
    private Spinner countrySpinner,
            stateOfOriginSpinner, lgaOfOriginSpinner;
    private ArrayAdapter lgaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
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
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        radioGroup = findViewById(R.id.radioGroup);
        address = findViewById(R.id.address);
        nearestBustop = findViewById(R.id.landmark);
        username = findViewById(R.id.username);
        countrySpinner = findViewById(R.id.countrySpinner);
        stateOfOriginSpinner = findViewById(R.id.stateSpinner);
        lgaOfOriginSpinner = findViewById(R.id.lgaSpinner);
        countryStateLgaArray();
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phoneNunber);
        re_enterpassword = findViewById(R.id.reenterpassword);
        signupButton = findViewById(R.id.signupButton);
        backToLoginTextview = findViewById(R.id.loginTextview);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.maleRadioButton:
                    gender = "male";
                    break;
                case R.id.femaleRadioButton:
                    gender = "female";
                    break;
            }

        });
    }

    private void initListeners() {
        signupButton.setOnClickListener(v -> {
            if (isValidFields()) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        AppUtils.initLoadingDialog(this);

        RegistrationSendObject registrationSendObject = new RegistrationSendObject();
        registrationSendObject.setFirstName(firstName.getText().toString().trim());
        registrationSendObject.setLastName(lastName.getText().toString().trim());
        registrationSendObject.setUsername(username.getText().toString().trim());
        registrationSendObject.setEmail(emailAddress.getText().toString().trim());
        registrationSendObject.setPassword(password.getText().toString().trim());
        registrationSendObject.setPhoneNumber(phoneNumber.getText().toString().trim());
        registrationSendObject.setGender(gender);

        Location location = new Location();
        location.setAddress(address.getText().toString().trim());
        location.setCountry(countrySpinner.getSelectedItem().toString());
        location.setState(stateOfOriginSpinner.getSelectedItem().toString());
        location.setLga(lgaOfOriginSpinner.getSelectedItem().toString());
        location.setNearestBusStop(nearestBustop.getText().toString().trim());

        registrationSendObject.setLocation(location);

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.registerUser(registrationSendObject, new RegisterInterface() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse) {
                Toast.makeText(RegisterActivity.this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(RegisterActivity.this, errorCode + "", Toast.LENGTH_SHORT).show();
                AppUtils.dismissLoadingDialog();
            }
        });

    }

    private void countryStateLgaArray() {
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);
        countrySpinner.setAdapter(adapter1);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (countrySpinner.getSelectedItem().equals("NIGERIA")) {
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(RegisterActivity.this,
                            R.array.state, android.R.layout.simple_spinner_item);
                    stateOfOriginSpinner.setAdapter(adapter2);
                    stateOfOriginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (stateOfOriginSpinner.getSelectedItem().equals("Abia")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.abia_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Abuja")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.abuja_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Adamawa")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.adamawa_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Akwa Ibom")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.akwaIbom_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Anambra")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.anambra_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Bauchi")) {
                                lgaAdapter = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.bauchi_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(lgaAdapter);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Bayelsa")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.bayelsa_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Benue")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.benue_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Borno")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.borno_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Cross River")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.crossriver_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Delta")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.delta_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Ebonyi")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.ebonyi_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Edo")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.edo_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Ekiti")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.ekiti_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Enugu")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.enugu_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Gombe")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.gombe_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Imo")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.imo_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Jigawa")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.jigawa_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Kaduna")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.kaduna_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Kano")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.kano_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Katsina")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.katsina_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Kebbi")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.kebbi_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Kogi")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.kogi_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Kwara")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.kwara_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Lagos")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.lagos_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Nasarawa")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.nasarawa_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Niger")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.niger_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Ogun")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.ogun_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Ondo")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.ondo_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Osun")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.osun_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Oyo")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.oyo_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Plateau")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.plateau_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Rivers")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.rivers_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Sokoto")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.sokoto_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Taraba")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.taraba_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Yobe")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.yobe_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            } else if (stateOfOriginSpinner.getSelectedItem().equals("Zamfara")) {
                                ArrayAdapter adapter21 = ArrayAdapter.createFromResource(RegisterActivity.this,
                                        R.array.zamfara_lga, android.R.layout.simple_spinner_item);
                                lgaOfOriginSpinner.setAdapter(adapter21);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private boolean isValidFields() {
        if (firstName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "First name is required", Toast.LENGTH_SHORT).show();
            firstName.requestFocus();
            return false;
        }
        if (lastName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Last name is required", Toast.LENGTH_SHORT).show();
            lastName.requestFocus();
            return false;
        }
        if (address.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
            address.requestFocus();
            return false;
        }
        if (nearestBustop.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Landmark is required", Toast.LENGTH_SHORT).show();
            nearestBustop.requestFocus();
            return false;
        }
        if (username.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return false;
        }
        if (username.getText().toString().trim().length() < 4) {
            Toast.makeText(this, "username must be above 3 characters", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return false;
        }
        if (phoneNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Number is required", Toast.LENGTH_SHORT).show();
            phoneNumber.requestFocus();
            return false;
        }
        if (phoneNumber.getText().toString().trim().length() < 11) {
            Toast.makeText(this, "Number must be 11 digits", Toast.LENGTH_SHORT).show();
            phoneNumber.requestFocus();
            return false;
        }
        if (emailAddress.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            emailAddress.requestFocus();
            return false;
        }
        if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return false;
        }
        if (password.getText().toString().trim().length() < 5) {
            Toast.makeText(this, "Password must be above 4 characters", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return false;
        }
        if (re_enterpassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            re_enterpassword.requestFocus();
            return false;
        }
        if (re_enterpassword.getText().toString().trim().length() < 5) {
            Toast.makeText(this, "Password must be above 4 characters", Toast.LENGTH_SHORT).show();
            re_enterpassword.requestFocus();
            return false;
        }
        if (!re_enterpassword.getText().toString().trim().equals(password.getText().toString().trim())) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            re_enterpassword.requestFocus();
            return false;
        }


        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fade_enter, R.anim.fade_out);
    }
}
