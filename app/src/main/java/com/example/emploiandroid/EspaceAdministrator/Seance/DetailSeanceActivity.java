package com.example.emploiandroid.EspaceAdministrator.Seance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.emploiandroid.Models.Seance;
import com.example.emploiandroid.R;

import java.util.Date;

public class DetailSeanceActivity extends AppCompatActivity {

    private  int idClient;
    private String title;
    private String start,end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_seance);

     //   Seance seance=  getIntent().getParcelableExtra("seance");

        Intent intent = getIntent();
        idClient =intent.getExtras().getInt("idClient");
        title =intent.getExtras().getString("title");
        start = intent.getExtras().getString("start");
        end = intent.getExtras().getString("end");

        Log.d("Detail seance",idClient + title + start + end);




    }
}
