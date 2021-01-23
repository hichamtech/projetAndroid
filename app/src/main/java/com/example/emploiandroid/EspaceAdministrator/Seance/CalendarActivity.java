package com.example.emploiandroid.EspaceAdministrator.Seance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emploiandroid.Models.Seance;
import com.example.emploiandroid.Models.URLs;
import com.example.emploiandroid.R;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarActivity extends AppCompatActivity {
    private static final String DEBUGTAG = CalendarActivity.class.getCanonicalName();
    private static String URL_BASE = "http://192.168.1.13:8000/api/seances";
    private ArrayList<Seance> dataModelArrayList;
    private ListAdapterSeance listAdapter;
    private ListView listView;
    private Seance seances;
    private  JSONObject dataobj;
    private JSONArray dataArray;
    private URLs urls;
    CalenderEvent calenderEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
       calenderEvent = findViewById(R.id.calender_event);
        listView = findViewById(R.id.lvSeance);
        urls = new URLs();
        Calendar calendar = Calendar.getInstance();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls.URL_LISTESEANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        dataModelArrayList = new ArrayList<>();

                        try {
                            //if(obj.optString("status").equals("true")){ //TODO:ADD STATUS OF RESPONSE IN API
                            JSONObject obj = new JSONObject(response);
                            dataArray = obj.getJSONArray("hydra:member");
                            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
                            for (int i = 0; i < dataArray.length(); i++)
                            {
                                seances = new Seance();

                                dataobj = dataArray.getJSONObject(i);
                                seances.setId(dataobj.getInt("id"));
                                seances.setDateDebut(dateFormat.parse(dataobj.getString("DateDebut")));
                                seances.setDateFin(dateFormat.parse(dataobj.getString("DateFin")));
                                seances.setNbrRep(dataobj.getInt("NbrRep"));

                                Calendar c = Calendar.getInstance();
                                c.setTime(seances.getDateDebut());
                                //   System.out.println(c.get(Calendar.MONTH) + c.get(Calendar.DATE));


                              /*  String [] dateParts = dataobj.getString("DateDebut").split("-");
                                String year = dateParts[0];
                                String month = dateParts[1];
                                String day = dateParts[2];

                                Log.d(" date", "month:"  + month + "day:" + day  + "year:" + year);*/


                                dataModelArrayList.add(seances);

                                Log.d(DEBUGTAG, "date = " + String.valueOf(dateFormat.parse(dataobj.getString("DateDebut")).getTime()));
                                //  long date = dateFormat.parse(dataobj.getString("DateDebut")).getTime();

                              //  Event event = new Event(dateFormat.parse(dataobj.getString("DateDebut")).getTime(), "seance", Color.BLUE);
                               // calenderEvent.addEvent(new Event(dateFormat.parse(dataobj.getString("DateDebut")).getTime(), "Event", Color.RED));
                                //calenderEvent.addEvent(event);
                            }
                            //  setupListview();
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




        //SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
       /// long date = new Date("10 May 2020").getTime();
       // calenderEvent.addEvent(new Event(date, "Event", Color.GREEN));

       // Event event = new Event(calendar.getTimeInMillis(), "Test", Color.BLUE);
        //calenderEvent.addEvent(event);
       // calenderEvent.addEvent(event);
        /*calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                Log.d(DEBUGTAG, dayContainerModel.getDate() + dayContainerModel.getEvent().getEventText());

            }
        });*/

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUGTAG, "OnResume");
      //  getSeance();
    }

    public void getSeance(){
        //    AfficherProgressDialog(this, "Chargement..","Recherche Moniteur",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        dataModelArrayList = new ArrayList<>();

                        try {
                            //if(obj.optString("status").equals("true")){ //TODO:ADD STATUS OF RESPONSE IN API
                            JSONObject obj = new JSONObject(response);
                            dataArray = obj.getJSONArray("hydra:member");
                            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
                            for (int i = 0; i < dataArray.length(); i++)
                            {
                                seances = new Seance();

                                dataobj = dataArray.getJSONObject(i);
                                seances.setId(dataobj.getInt("id"));
                                seances.setDateDebut(dateFormat.parse(dataobj.getString("DateDebut")));
                                seances.setDateFin(dateFormat.parse(dataobj.getString("DateFin")));
                                seances.setNbrRep(dataobj.getInt("NbrRep"));

                                Calendar c = Calendar.getInstance();
                                c.setTime(seances.getDateDebut());
                                //   System.out.println(c.get(Calendar.MONTH) + c.get(Calendar.DATE));


                              /*  String [] dateParts = dataobj.getString("DateDebut").split("-");
                                String year = dateParts[0];
                                String month = dateParts[1];
                                String day = dateParts[2];

                                Log.d(" date", "month:"  + month + "day:" + day  + "year:" + year);*/


                                dataModelArrayList.add(seances);

                                Log.d(DEBUGTAG, "date = " + String.valueOf(dateFormat.parse(dataobj.getString("DateDebut")).getTime()));
                              //  long date = dateFormat.parse(dataobj.getString("DateDebut")).getTime();

                                calenderEvent.addEvent(new Event(dateFormat.parse(dataobj.getString("DateDebut")).getTime(), "Event", Color.GREEN));

                            }
                            //  setupListview();
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


    private void setupListview() {
        //    supprimerSimpleProgressDialog();

        listAdapter = new ListAdapterSeance(this, dataModelArrayList);
        listView.setAdapter(listAdapter);

    }

}
