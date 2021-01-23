package com.example.emploiandroid.EspaceAdministrator.Moniteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.auth0.android.jwt.JWT;

import com.example.emploiandroid.Models.Personne;

import com.example.emploiandroid.Models.URLs;
import com.example.emploiandroid.Models.VolleySingleton;
import com.example.emploiandroid.R;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class EditMoniteurActivity extends AppCompatActivity {

    private static final String DEBUGTAG = EditMoniteurActivity.class.getCanonicalName();

    private EditText txtNom, txtPrenom, txtCin, txtAdresse, txtTele, txtEmail, txtPassword, txtDateN;
    private static String URL_BASE ;
    private Button btnUpdate;
    private JWT jwt;
    private int idMoniteur;
    private URLs urLs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_moniteur);

        jwt = (JWT) getIntent().getParcelableExtra("jwt");
        urLs = new URLs();
        txtNom = findViewById(R.id.txtName); //TODO:DELETE txtNom form string file

        txtPrenom = findViewById(R.id.txtprenom);
        txtCin = findViewById(R.id.txtcin);
        txtAdresse = findViewById(R.id.txtadresse);
        txtTele = findViewById(R.id.txtPhone); //TODO:DELETE txtNumform string file
        txtEmail = findViewById(R.id.txtMail);//TODO:DELETE txtEmail string file
        btnUpdate = findViewById(R.id.btnUpdate);



        Intent intent = getIntent();
        idMoniteur =intent.getExtras().getInt("idMoniteur");
        txtNom.setText(intent.getExtras().getString("nom"));
        txtEmail.setText(intent.getExtras().getString("email"));
        txtPrenom.setText(intent.getExtras().getString("prenom"));
        txtCin.setText(intent.getExtras().getString("cin"));
        txtAdresse.setText(intent.getExtras().getString("adresse"));
        txtTele.setText(intent.getExtras().getString("tel"));



    }

    private void UpdateMoniteur() {

        URL_BASE  =urLs.URL_BASE_PERSONNE +idMoniteur;

        try {
            JSONObject jsonBody = new JSONObject();

            Log.d(DEBUGTAG,"Modifier un MONITEUR");
            String nom = txtNom.getText().toString();
            String prenom = txtPrenom.getText().toString();
            String cin = txtCin.getText().toString();
            String adresse = txtAdresse.getText().toString();
            String tele = txtTele.getText().toString();
            String email = txtEmail.getText().toString();



            jsonBody.put("nom", nom);
            jsonBody.put("prenom", prenom);
            jsonBody.put("cin", cin);
            jsonBody.put("adresse", adresse);
            jsonBody.put("NumTelephone", tele);
            jsonBody.put("email", email);


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PUT, URL_BASE.trim(), jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d(DEBUGTAG,response.toString());
                    Intent intent = new Intent(EditMoniteurActivity.this, ListMoniteurActivity.class);
                    startActivity(intent);

                    finish();
                    Toast.makeText(getApplicationContext(), "Modifier avec Succès", Toast.LENGTH_LONG).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_LONG).show();

                    onBackPressed();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + jwt);
                    return headers;
                }
            };
            VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonOblect);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void btnUpdate(View view) {
        if (view == btnUpdate) {
            UpdateMoniteur();

        }
    }
}
