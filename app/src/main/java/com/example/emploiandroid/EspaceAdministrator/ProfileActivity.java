package com.example.emploiandroid.EspaceAdministrator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.emploiandroid.Models.LoginModel;
import com.example.emploiandroid.Models.SharedPrefManager;
import com.example.emploiandroid.R;

public class ProfileActivity extends AppCompatActivity {

    TextView  textViewUsername, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        LoginModel user = SharedPrefManager.getInstance(this).getUser();

        //if the user is not logged in
        //starting the login activity
     /*  if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/


        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);


        //getting the current user
        textViewEmail.setText(user.getEmail());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}
