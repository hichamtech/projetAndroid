package com.example.emploiandroid.Models;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.emploiandroid.LoginActivity;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ROLE = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_TOKEN = "keyid";
    private static final String KEY_STATUS = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(LoginModel user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_ROLE, user.getRole());
        editor.putString(KEY_TOKEN, user.getToken());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public LoginModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LoginModel(
                //sharedPreferences.getInt(KEY_ID, Integer.parseInt(String.valueOf(-1))),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ROLE, null),
                sharedPreferences.getString(KEY_TOKEN, null),
                sharedPreferences.getString(KEY_STATUS, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
