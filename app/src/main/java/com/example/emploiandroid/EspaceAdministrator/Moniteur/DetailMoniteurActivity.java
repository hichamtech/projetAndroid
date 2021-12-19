package com.example.emploiandroid.EspaceAdministrator.Moniteur;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.example.emploiandroid.R;

public class DetailMoniteurActivity extends AppCompatActivity {
    TextView txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_moniteur);


        txtId = findViewById(R.id.txtid);
        Intent intent = getIntent();
        txtId.setText("this is my id" + intent.getExtras().getInt("idMoniteur") );
    }

}
