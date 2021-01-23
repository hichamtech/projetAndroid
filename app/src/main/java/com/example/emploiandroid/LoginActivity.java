package com.example.emploiandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.emploiandroid.EspaceAdministrator.AdminActivity;
import com.example.emploiandroid.EspaceClient.ClientActivity;
import com.example.emploiandroid.EspaceMoniteur.MoniteurActivity;
import com.example.emploiandroid.Models.LoginModel;
import com.example.emploiandroid.Models.SharedPrefManager;
import com.example.emploiandroid.Models.URLs;
import com.example.emploiandroid.Models.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    //DECLARATION
    private static final String DEBUGTAG = LoginActivity.class.getCanonicalName();

    private static String URL_BASE = "http://192.168.1.13:8000/api/login_check";
    LoginModel  loginModel;
    EditText username,password ;
    Button btnLogin;
    String role;
    URLs urLs;

    //END DECLARATION

    //RECYCLE METHODS
    protected void onCreate(Bundle savedInstanceState) {
        urLs = new URLs();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

       if (SharedPrefManager.getInstance(this).isLoggedIn() && role.equals("[\"ROLE_ADMIN\"]")) {
            finish();
            startActivity(new Intent(this, AdminActivity.class));
        }else if  (SharedPrefManager.getInstance(this).isLoggedIn() && role.equals("[\"ROLE_CLIENT\"]")){
            finish();
            startActivity(new Intent(this, ClientActivity.class));

        }else if  (SharedPrefManager.getInstance(this).isLoggedIn() && role.equals("[\"ROLE_MONITEUR\"]")){
            finish();
            startActivity(new Intent(this, MoniteurActivity.class));
        }

        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        btnLogin = findViewById(R.id.btnLogin);
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

    protected void onResume() {
        super.onResume();
        Log.d(DEBUGTAG, "OnResume");

    }
    //END RECYCLE METHOD

    //LOGIN FUNCTION
   public void Login() {
        Log.d(DEBUGTAG, "Login");
       final String userN = username.getText().toString();
       final String pass = password.getText().toString();

       if(TextUtils.isEmpty(userN) )  {
            username.setError("veuillez remplir le champ");
             username.requestFocus();

           return;
         }
        if (TextUtils.isEmpty((pass))){
            password.setError("veuillez remplir le champ");
            password.requestFocus();
            return;
        }

            Map<String, String> params = new HashMap<String, String>();
            params.put("username", userN);
            params.put("password", pass);

            JsonObjectRequest loginForm = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                    urLs.URL_LOGIN, new JSONObject(params),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(DEBUGTAG, response.toString());
                            try {

                                    loginModel = new LoginModel(
                                          //  response.getInt("id"),
                                            response.getString("email"),
                                            response.getString("role"),
                                            response.getString("status"),
                                            response.getString("token")
                                    );
                                role = loginModel.getRole();
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(loginModel);
                                finish();

                                if (loginModel.getRole().equals("[\"ROLE_ADMIN\"]") && loginModel.getStatus().equals("success")) {

                                   startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                                    openAdminProfile();
                                     Log.d(DEBUGTAG, "redirect to admin page");

                                    }  else if(loginModel.getRole().equals("[\"ROLE_CLIENT\"]") && loginModel.getStatus().equals("success")) {
                                        openClientProfile();
                                        Log.d(DEBUGTAG, "redirect to client page");
                                    finish();

                                    } else if (loginModel.getRole().equals("[\"ROLE_MONITEUR\"]") && loginModel.getStatus().equals("success")) {
                                        opentMoniteurProfile();
                                        Log.d(DEBUGTAG, "redirect to moniteur page");
                                    } else {
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                        if(response.has("message")){
                                            JSONArray ja = new JSONArray();
                                            for (int i= 0;i<ja.length();i++){

                                                Log.d(DEBUGTAG,response.getString("message"));
                                            }

                                        }

                                        }


                            } catch (JSONException e) {


                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Log.e(DEBUGTAG, "time out or connectionError");
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();


                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getApplicationContext(), "Adresse e-mail ou mot de passe invalide", Toast.LENGTH_LONG).show();
                        username.setText(null);
                        password.setText(null);
                        return;
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getApplicationContext(), "Probleme serveur", Toast.LENGTH_LONG).show();
                        return;
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(), "Probleme de reseau", Toast.LENGTH_LONG).show();

                        Log.e(DEBUGTAG, error.getMessage());
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parse erreur", Toast.LENGTH_LONG).show();

                        Log.e(DEBUGTAG, error.getMessage());
                    }
                }


            }){
               /* @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }*/

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    //HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Authorization", "Bearer " + loginModel.getToken()); //TODO::ADD TOKEN
                   // Map<String, String> params = new HashMap<>();
                    //headers.put("username", loginModel.getEmail());

                    //return headers;
                    Map<String, String> params = new HashMap<>();
                    params.put("username", userN);
                    params.put("password", pass);
                    return params;
                }
            };


            VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(loginForm);



    }



    //FUNCTION TO OPEN ADMIN DASH AND SENDING USER INFO
    public void openAdminProfile(){

        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        intent.putExtra("email",loginModel.getEmail());
        intent.putExtra("token",loginModel.getToken());
        intent.putExtra("id",loginModel.getId());

        startActivity(intent);

    }
    public void openClientProfile(){

        Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
        intent.putExtra("email",loginModel.getEmail());
        intent.putExtra("token",loginModel.getToken());
        intent.putExtra("id",loginModel.getId());
        startActivity(intent);


    }
    public void opentMoniteurProfile(){


        Intent intent = new Intent(getApplicationContext(), MoniteurActivity.class);
        intent.putExtra("email",loginModel.getEmail());
        intent.putExtra("token",loginModel.getToken());
        intent.putExtra("id",loginModel.getId());
        startActivity(intent);
    }



    public void btnLogin(View view) {
        if(view == btnLogin){
            Login();
        }
    }
}
