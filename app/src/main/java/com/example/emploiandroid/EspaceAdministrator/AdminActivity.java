package com.example.emploiandroid.EspaceAdministrator;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.auth0.android.jwt.JWT;
import com.example.emploiandroid.EspaceAdministrator.Client.ListClientActivity;
import com.example.emploiandroid.EspaceAdministrator.Moniteur.ListMoniteurActivity;
import com.example.emploiandroid.EspaceAdministrator.Seance.AgendaCalandarView;
import com.example.emploiandroid.EspaceAdministrator.Seance.CalendarActivity;
import com.example.emploiandroid.EspaceAdministrator.Seance.ListSeanceActivity;
import com.example.emploiandroid.Models.LoginModel;
import com.example.emploiandroid.Models.SharedPrefManager;
import com.example.emploiandroid.R;

public class AdminActivity extends AppCompatActivity {
    private static final String DEBUGTAG = AdminActivity.class.getCanonicalName();


    private TextView txtUserInfo;
    private Button btnClients,btnMoniteurs,bntProfil,btnSeances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(DEBUGTAG,"onCreate");
        setContentView(R.layout.activity_admin);
        LoginModel user =  SharedPrefManager.getInstance(this).getUser();

        JWT jwt = (JWT) getIntent().getParcelableExtra("jwt");
       /* if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/

       //
       //Log.d(DEBUGTAG,"my id is" +  Integer.valueOf(user.getId()));


         //txtUserInfo.setText(user.getId());
        txtUserInfo = findViewById(R.id.txtUserInfo);
        txtUserInfo.setText(getString(R.string.Bonjour) + user.getEmail());

       /* Intent intent = getIntent();
        txtUserInfo.setText("Bienvenue " + intent.getExtras().getString("email") );*/

           // txtUserInfo.setText(user.getEmail());


        btnClients = findViewById(R.id.btnClients);
        btnMoniteurs = findViewById(R.id.btnMoniteur);
        bntProfil = findViewById(R.id.btnProfil);
        btnSeances =  findViewById(R.id.btnSeances);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUGTAG, "OnStart");

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(DEBUGTAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(DEBUGTAG, "onStop");

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUGTAG, "onRestart");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DEBUGTAG, "onDestroy");

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUGTAG, "OnResume");

    }



    public void btnSeances(View view) { openSeanceList(); }
    public void btnClients(View view) {
        openClientList();
    }
    public void btnMoniteurs(View view) {
        openMoniteurList();
    }
    public void btnProfil(View view) {
        openProfil();
    }

    public void openSeanceList(){
        Intent intent = new Intent(AdminActivity.this, AgendaCalandarView.class);

        startActivity(intent);
    }
    public void openClientList(){
        Intent intent = new Intent(AdminActivity.this, ListClientActivity.class);
       // intent.putExtra("token",loginModel.getToken());// TODO:Implement TOKEN
        startActivity(intent);
    }
    public void openMoniteurList(){
        Intent intent = new Intent(AdminActivity.this, ListMoniteurActivity.class);
        // intent.putExtra("token",loginModel.getToken());// TODO:Implement TOKEN
        startActivity(intent);
    }
    public void openProfil(){
        Intent intent = new Intent(AdminActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

}
