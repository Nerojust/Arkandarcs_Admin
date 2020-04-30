package com.nerojust.arkandarcsadmin.utils;

import android.content.SharedPreferences;

import static com.nerojust.arkandarcsadmin.utils.MyApplication.getSharedPreferences;

public class SessionManager {

    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String AMOUNT = "AMOUNT";
    private static final String LONG = "LONG";
    private static final String LAT = "LAT";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD_ = "PASSWORD_";
    private static final String TOKEN = "TOKEN";
    private static final String PRODUCT_ID = "PRODUCT_ID";
    private static final String ADD_PRODUCT = "ADD_PRODUCT";


    private final SharedPreferences pref = getSharedPreferences();

    public void clearSharedPreferences() {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(FIRST_NAME);
        editor.remove(LAST_NAME);
        editor.remove(EMAIL_ADDRESS);
        editor.remove(USERNAME);
        editor.apply();
    }

    private void setIntPreference(String name, int value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    private void setBooleanPreference(String name, boolean value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    private long getLongPreference(String name) {
        if (pref.contains(name)) {
            return pref.getLong(name, 0);
        } else {
            return 0;
        }
    }

    private void setLongPreference(String name, long value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(name, value);
        editor.apply();
    }


    private void setStringPreference(String name, String value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        editor.apply();
    }

    private void setFloatPreference(String name, float value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(name, value);
        editor.apply();
    }

    private Integer getIntPreference(String name) {
        if (pref.contains(name)) {
            return pref.getInt(name, 0);
        } else {
            return 0;
        }
    }

    private boolean getBooleanPreference(String name) {
        return pref.contains(name) && pref.getBoolean(name, false);
    }

    private float getFloatPreference(String name) {
        if (pref.contains(name)) {
            return pref.getFloat(name, 0);
        } else {
            return 0;
        }
    }

    private String getStringPreference(String name) {
        if (pref.contains(name)) {
            return pref.getString(name, "");
        } else {
            return null;
        }
    }

    public void startLoginSession() {
        setBooleanPreference(IS_LOGIN, true);
    }

    public boolean isLogin() {
        return getBooleanPreference(IS_LOGIN);
    }

    public void logout() {
        setBooleanPreference(IS_LOGIN, false);
    }


    public String getAmount() {
        return getStringPreference(AMOUNT);
    }

    public void setAmount(String amount) {
        setStringPreference(AMOUNT, amount);
    }

    public String getLongitude() {
        return getStringPreference(LONG);
    }

    public void setLongitude(String longitude) {
        setStringPreference(LONG, longitude);
    }

    public String getLatitude() {
        return getStringPreference(LAT);
    }

    public void setLatitude(String latitude) {
        setStringPreference(LAT, latitude);
    }

    public String getFirstName() {
        return getStringPreference(FIRST_NAME);
    }

    public void setFirstNameFromLogin(String firstname) {
        setStringPreference(FIRST_NAME, firstname);
    }

    public String getLastNameFromLogin() {
        return getStringPreference(LAST_NAME);
    }

    public void setLastName(String lastName) {
        setStringPreference(LAST_NAME, lastName);
    }

    public void setUsername(String username) {
        setStringPreference(USERNAME, username);
    }

    public String getUserame() {
        return getStringPreference(USERNAME);
    }

    public String getPassword() {
        return getStringPreference(PASSWORD_);
    }

    public void setPassword(String password) {
        setStringPreference(PASSWORD_, password);
    }

    public String getEmailAddress() {
        return getStringPreference(EMAIL_ADDRESS);
    }

    public void setEmailAddress(String emailAddress) {
        setStringPreference(EMAIL_ADDRESS, emailAddress);
    }

    public String getToken() {
        return getStringPreference(TOKEN);
    }

    public void setToken(String token) {
        setStringPreference(TOKEN, token);
    }

    public String getProductId(){
        return getStringPreference(PRODUCT_ID);
    }

    public void setProductId(String productId) {
        setStringPreference(PRODUCT_ID,productId);
    }

    public String getAddProductJson(){
        return getStringPreference(ADD_PRODUCT);
    }

    public void setAddProductJson(String addproduct) {
        setStringPreference(ADD_PRODUCT, addproduct);
    }
}
