package com.example.emploiandroid.EspaceAdministrator.Seance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emploiandroid.Models.Seance;
import com.example.emploiandroid.R;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarManager;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.github.tibolte.agendacalendarview.models.WeekItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AgendaCalandarView extends AppCompatActivity {



    AgendaCalendarView mAgendaCalendarView;
    private static final String DEBUGTAG = CalendarActivity.class.getCanonicalName();
    private static String URL_BASE = "http://0dddb164b7d5.ngrok.io/api/seances";
    ArrayList<Seance> dataModelArrayList=null;
    private ListAdapterSeance listAdapter;
    private ListView listView;
    private Seance seances;
    JSONObject dataobj;
    JSONArray dataArray;
    BaseCalendarEvent event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_calandar_view);

        mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUGTAG, "OnResume");

        List<CalendarEvent> eventList = new ArrayList<>();
        getSeance(eventList);



    }


    public void getSeance(final List<CalendarEvent> eventList){
        //    AfficherProgressDialog(this, "Chargement..","Recherche seances",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("strrrrr", ">>" + response);
                        dataModelArrayList = new ArrayList<>();

                        Calendar minDate = Calendar.getInstance();
                        Calendar maxDate = Calendar.getInstance();

                        minDate.add(Calendar.MONTH, -2);
                        minDate.set(Calendar.DAY_OF_MONTH, 1);
                        maxDate.add(Calendar.YEAR, 1);


                        try {
                            //if(obj.optString("status").equals("true")){ //TODO:ADD STATUS OF RESPONSE IN API
                            JSONObject obj = new JSONObject(response);
                            dataArray = obj.getJSONArray("hydra:member");
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            for (int i = 0; i < dataArray.length(); i++)
                            {
                                seances = new Seance();

                                dataobj = dataArray.getJSONObject(i);
                                seances.setId(dataobj.getInt("id"));
                                seances.setDateDebut(dateFormat.parse(dataobj.getString("DateDebut")));
                                seances.setDateFin(dateFormat.parse(dataobj.getString("DateFin")));
                                seances.setNbrRep(dataobj.getInt("NbrRep"));
                                dataModelArrayList.add(seances);


                                Date date = sdf.parse(dataobj.getString("DateDebut"));
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(date);
                                event = new BaseCalendarEvent("seance", "A beautiful small town", "Nombre repetition " +seances.getNbrRep() ,
                                        Color.RED, cal, cal, true);
                                Log.d(DEBUGTAG, "cal " +  String.valueOf(cal));
                                eventList.add(event);


                            }
                            CalendarPickerController calendarPickerController = new CalendarPickerController() {
                                @Override
                                public void onDaySelected(DayItem dayItem) {

                                }

                                @Override
                                public void onEventSelected(CalendarEvent event) {

                                }

                                @Override
                                public void onScrollToDate(Calendar calendar) {

                                }
                            };

                            mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), calendarPickerController);




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


}
