package com.example.emploiandroid.EspaceAdministrator.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emploiandroid.EspaceAdministrator.ListAdapter;
import com.example.emploiandroid.Models.Personne;
import com.example.emploiandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListClientActivity extends AppCompatActivity {
    private static final String DEBUGTAG = ListClientActivity.class.getCanonicalName();
    private static String URL_BASE = "http://0dddb164b7d5.ngrok.io/api/personnes?roles=ROLE_CLIENT";
    private static ProgressDialog mProgressDialog;
    private ListView listView;
    ArrayList<Personne> dataModelArrayList;
    private ListAdapter listAdapter;
    Personne client ;
    Button btnAddClient;
    int lockClikedItemIndex;
    JSONArray dataArray;
    private static  final  int EDIT = 0,DElETE=1, DETAIL =2;
    JSONObject dataobj;
    int idclient;

    //TO SEND DATE to the OTHERS ACTIVITY SEE FUNCTION ItemLongClick
   private String nom,prenom,email,cin,adresse,tele;
   private Date dateN;
    //Start RCYCLE METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);

        listView = findViewById(R.id.lv);
        btnAddClient = findViewById(R.id.btnAddClient);
        registerForContextMenu(listView);
        getListClients();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                idclient= dataModelArrayList.get(position).getId();
                nom = dataModelArrayList.get(position).getNom();
                prenom = dataModelArrayList.get(position).getPrenom();
                email = dataModelArrayList.get(position).getEmail();
                cin = dataModelArrayList.get(position).getCin();
                adresse = dataModelArrayList.get(position).getAdresse();
                tele = dataModelArrayList.get(position).getNumTelephone();
                dateN = dataModelArrayList.get(position).getDate_naissance();
                lockClikedItemIndex = position;
                return false;
            }
        });


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
    //END RECYCLE METHODS


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);
        // menu.setHeaderIcon(R.drawable.editicon);
        menu.setHeaderTitle("Options");
        menu.add(Menu.NONE, EDIT, menu.NONE,"Modifier");
        menu.add(Menu.NONE, DElETE, menu.NONE,"Supprimer");
        menu.add(Menu.NONE, DETAIL,menu.NONE,"Detail");
    }

    //CRUD CLIENT
    //Function To the list of Client
    private void getListClients(){
        AfficherProgressDialog(this, "Chargement..","Recherch client",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        dataModelArrayList = new ArrayList<>();

                        try {
                            //if(obj.optString("status").equals("true")){ //TODO:ADD STATUS OF RESPONSE IN API
                            JSONObject obj = new JSONObject(response);
                           dataArray  = obj.getJSONArray("hydra:member");
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

                            for (int i = 0; i < dataArray.length(); i++)
                            {
                                client = new Personne();

                                dataobj = dataArray.getJSONObject(i);
                                client.setId(dataobj.getInt("id"));
                                client.setNom(dataobj.getString("nom"));
                                client.setPrenom(dataobj.getString("prenom"));
                                client.setCin(dataobj.getString("cin"));
                                client.setAdresse(dataobj.getString("adresse"));
                                client.setEmail(dataobj.getString("email"));
                                client.setNumTelephone(dataobj.getString("NumTelephone"));
                                client.setDate_naissance(dateFormat.parse(dataobj.getString("dateNaissance")));
                                dataModelArrayList.add(client);

                            }
                            setupListview();
                            // }
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
    //Function To Edit Client
    public void EditClient(int idClient){
        Intent intent = new Intent(ListClientActivity.this, EditClientFormActivity.class);
        intent.putExtra("idClient",idClient);
        intent.putExtra("nom",nom);
        intent.putExtra("prenom",prenom);
        intent.putExtra("email",email);
        intent.putExtra("cin",cin);
        intent.putExtra("adresse",adresse);
        intent.putExtra("tel",tele);
        intent.putExtra("dateN",dateN);


        startActivity(intent);

    }
    //Function To Delete Client
    public void DeleteClient(int idClient){

        String url = "http://192.168.1.12:8000/api/personnes/"+idClient;
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getApplicationContext(), "Supprimer avec succès", Toast.LENGTH_LONG).show();
                        getListClients();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        Toast.makeText(getApplicationContext(), "Ajouté avec Succès", Toast.LENGTH_LONG).show();

                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(dr);


    }
    //Function to get Detail CLient
    public void DetailClient(int idClient){
        Intent intent = new Intent(ListClientActivity.this, DetailClientActivity.class);
        intent.putExtra("idClient",idClient);
        intent.putExtra("nom",nom);
        intent.putExtra("prenom",prenom);
        intent.putExtra("email",email);
        intent.putExtra("cin",cin);
        intent.putExtra("adresse",adresse);
        intent.putExtra("tel",tele);
        intent.putExtra("dateN",dateN);

        //TODO:ADD API TOKEN
        startActivity(intent);




    }
    //END CRUD CLIENT


    // Start Intent And ListView Setup
    //Function to Select item for EDIT/DELETE/DETAILS
    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()) {

            case EDIT:
                EditClient(idclient);
                break;

            case DElETE:
                AlertDialog diaBox = AskOption();
                diaBox.show();

                break;

            case DETAIL:

                DetailClient(idclient);


        }

        return super.onContextItemSelected(item);
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                //.setIcon(R.drawable.delete) //TODO add icon

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DeleteClient(idclient);
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
    //FUNCTION TO SETUP THE LIST VIEW
    private void setupListview(){
        supprimerSimpleProgressDialog();
        listAdapter = new ListAdapter(this, dataModelArrayList);
        listView.setAdapter(listAdapter);
    }
    //FUNCTION TO DELETE PROGRESS DIALOG
    public static void supprimerSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //FUNCTION TO DISPLAY PROGRESS DIAALOG
    public static void AfficherProgressDialog(Context context, String title, String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //FUNCTION TO OPEN FORM CLIENT
    public void OpenAddForm(){

        Intent intent = new Intent(ListClientActivity.this, AddClientFormActivity.class);
        //TODO:SEND TOKEN
        startActivity(intent);

    }
    //OnClick BtnAdd
    public void btnAddClient(View view) {

        if (view ==btnAddClient){
            OpenAddForm();
        }

    }
    //End Start Intent ListView Setup


}
