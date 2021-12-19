package com.example.emploiandroid.EspaceAdministrator.Client;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.auth0.android.jwt.JWT;
import com.example.emploiandroid.Models.Personne;
import com.example.emploiandroid.R;
import com.example.emploiandroid.Models.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import android.widget.DatePicker;
import java.util.Calendar;

public class AddClientFormActivity extends AppCompatActivity {

    private static final String DEBUGTAG = AddClientFormActivity.class.getCanonicalName();

    private EditText txtNom, txtPrenom, txtCin, txtAdresse, txtTele, txtEmail, txtPassword, txtDateN;
    private static String URL_BASE = "http://0dddb164b7d5.ngrok.io/api/personnes";

    private Calendar calendar;
    private Button btnAdd;
    private Personne client;
    private int year, month, day;

    private JWT jwt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client_form);

        jwt = (JWT) getIntent().getParcelableExtra("jwt");

        txtNom = findViewById(R.id.txtName); //TODO:DELETE txtNom form string file
        txtPrenom = findViewById(R.id.txtTitle);
        txtCin = findViewById(R.id.txtCin);
        txtAdresse = findViewById(R.id.txtAdresse);
        txtTele = findViewById(R.id.txtPhone); //TODO:DELETE txtNumform string file
        txtEmail = findViewById(R.id.txtMail);//TODO:DELETE txtEmail string file
        txtPassword = findViewById(R.id.txtPassword);
        txtDateN = findViewById(R.id.txtDateN);
        btnAdd = findViewById(R.id.btnSave);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);



    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {
        txtDateN.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    private void createClient() {

        try {
            JSONObject jsonBody = new JSONObject();

            Log.d(DEBUGTAG,"CREATE CLIENT");
           String nom = txtNom.getText().toString();
            String prenom = txtPrenom.getText().toString();
            String cin = txtCin.getText().toString();
            String adresse = txtAdresse.getText().toString();
            String tele = txtTele.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            String dateN = txtDateN.getText().toString();


           jsonBody.put("nom", nom);
            jsonBody.put("prenom", prenom);
            jsonBody.put("cin", cin);
            jsonBody.put("dateNaissance", dateN);
            jsonBody.put("adresse", adresse);
            jsonBody.put("NumTelephone", tele);
            jsonBody.put("email", email);
            jsonBody.put("roles", "ROLE_CLIENT");
            jsonBody.put("password", password);
            JSONArray  roles =new JSONArray();
            roles.put("ROLE_CLIENT");
            jsonBody.put("roles", roles);


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL_BASE, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d(DEBUGTAG,response.toString());
                    Intent intent = new Intent(AddClientFormActivity.this, ListClientActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Ajouté avec Succès", Toast.LENGTH_LONG).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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

    public void btnClick(View view) {
        if (view == btnAdd) {
            createClient();

        }
    }
}




