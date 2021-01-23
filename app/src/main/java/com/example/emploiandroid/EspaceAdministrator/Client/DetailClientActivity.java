package com.example.emploiandroid.EspaceAdministrator.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emploiandroid.EspaceAdministrator.ListAdapter;
import com.example.emploiandroid.EspaceAdministrator.Seance.ListAdapterSeance;
import com.example.emploiandroid.Models.Personne;
import com.example.emploiandroid.Models.Seance;
import com.example.emploiandroid.Models.URLs;
import com.example.emploiandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class DetailClientActivity extends AppCompatActivity {

    private static final String DEBUGTAG = DetailClientActivity.class.getCanonicalName();
    private ArrayList<Seance> dataModelArrayList;
    private ListView listView;
    private TextView txtId,txtNom,txtPrenom,txtCin,txtTel,txtAdresse,txtEmail;
    private Intent intent;
    private JSONArray dataArray;
    private Personne client;
    private JSONObject dataobj;
    private Seance seances;
    private ListAdapterSeance listAdapter;
    private int idClient;
    private static String URL_BASE;
    private static String URL_BASE_SEANCE;
    private URLs urLs;

    //Start RCYCLE METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_client);

        urLs = new URLs();
        txtId = findViewById(R.id.txtid);
        txtNom = findViewById(R.id.txtNom);
        txtPrenom = findViewById(R.id.txtPrenom);
        txtCin = findViewById(R.id.txtCin);
        txtTel = findViewById(R.id.txtTel);
        txtAdresse = findViewById(R.id.txtAdresse);
        txtEmail = findViewById(R.id.txtEmail);
        listView = findViewById(R.id.lvSeance);
        intent = getIntent();
        // txtId.setText("this is my id" + );
        idClient = intent.getExtras().getInt("idClient");
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
        getDetailClient();
        getSeanceClient();
    }
    //END RECYCLE METHODS


    private void getDetailClient() {
        //  AfficherProgressDialog(this, "Chargement..","Recherch client",false);
        URL_BASE = urLs.URL_BASE_PERSONNE+idClient;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BASE.trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        //dataModelArrayList = new ArrayList<>();

                        try {
                            //if(obj.optString("status").equals("true")){ //TODO:ADD STATUS OF RESPONSE IN API
                            JSONObject obj = new JSONObject(response);
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

                            client = new Personne();

                            // dataobj = dataArray.getJSONObject(i);
                            client.setId(obj.getInt("id"));
                            client.setNom(obj.getString("nom"));
                            client.setPrenom(obj.getString("prenom"));
                            client.setCin(obj.getString("cin"));
                            client.setAdresse(obj.getString("adresse"));
                            client.setEmail(obj.getString("email"));
                            client.setNumTelephone(obj.getString("NumTelephone"));
                            client.setDate_naissance(dateFormat.parse(obj.getString("dateNaissance")));
                            //dataModelArrayList.add(client);


                            txtNom.setText(client.getNom());
                            txtPrenom.setText(client.getPrenom());
                            txtCin.setText(client.getCin());
                            txtTel.setText(client.getNumTelephone());
                            txtAdresse.setText(client.getAdresse());
                            txtEmail.setText(client.getEmail());




                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, //TODO: ADD API TOKEN
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void getSeanceClient() {
        URL_BASE_SEANCE = urLs.URL_BASE_PERSONNE+idClient+"/seances";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BASE_SEANCE.trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        dataModelArrayList = new ArrayList<>();

                        try {
                            //if(obj.optString("status").equals("true")){ //TODO:ADD STATUS OF RESPONSE IN API
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

                            JSONObject obj = new JSONObject(response);

                            dataArray = obj.getJSONArray("hydra:member");
                            if(obj.getString("hydra:totalItems").equals("0")){
                                return;
                            }else {


                                for (int i = 0; i < dataArray.length(); i++) {
                                    seances = new Seance();

                                    dataobj = dataArray.getJSONObject(i);

                                    seances.setDateDebut(dateFormat.parse(dataobj.getString("DateDebut")));
                                    seances.setDateFin(dateFormat.parse(dataobj.getString("DateFin")));
                                    seances.setNbrRep(dataobj.getInt("NbrRep"));

                                    dataModelArrayList.add(seances);

                                }
                                setupListview();
                            } // }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, //TODO: ADD API TOKEN
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //FUNCTION TO SETUP THE LIST VIEW
    private void setupListview() {
        //    supprimerSimpleProgressDialog();

        listAdapter = new ListAdapterSeance(this, dataModelArrayList);
        listView.setAdapter(listAdapter);

    }
}