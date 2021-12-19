package com.example.emploiandroid.EspaceClient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emploiandroid.LoginActivity;
import com.example.emploiandroid.Models.LoginModel;
import com.example.emploiandroid.Models.SharedPrefManager;
import com.example.emploiandroid.R;

public class ClientActivity extends AppCompatActivity {

    TextView txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
      /*  if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/
        LoginModel user =  SharedPrefManager.getInstance(this).getUser();
        txtEmail = findViewById(R.id.textView2);
        txtEmail.setText( " Espace Client Bonjour" + user.getEmail());


    }
}
