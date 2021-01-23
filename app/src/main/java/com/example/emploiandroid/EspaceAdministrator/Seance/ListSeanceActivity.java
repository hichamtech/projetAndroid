package com.example.emploiandroid.EspaceAdministrator.Seance;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emploiandroid.EspaceAdministrator.Moniteur.ListMoniteurActivity;
import com.example.emploiandroid.Models.Personne;
import com.example.emploiandroid.Models.Seance;
import com.example.emploiandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListSeanceActivity extends AppCompatActivity implements WeekView.EventClickListener,WeekView.EmptyViewClickListener, MonthLoader.MonthChangeListener {
    private static final String DEBUGTAG = ListSeanceActivity.class.getCanonicalName();
    private static String URL_BASE = "http://192.168.1.12:8000/api/seances";
    ArrayList<Seance> dataModelArrayList = new ArrayList<Seance>();
    Calendar clickedTime;
      WeekView mWeekView;
    JSONObject dataobj;
    JSONArray dataArray;
    private Seance seances;

    Calendar startTime;
    Calendar endTime;
    WeekViewEvent event;
    List<WeekViewEvent> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_seance);

      //  WeekView mWeekView = (WeekView) findViewById(R.id.weekView);

     //   mWeekView=findViewById(R.id.weekView);
        mWeekView.setAccessibilityLiveRegion(MODE_APPEND);
        mWeekView.setNumberOfVisibleDays(7);

        events= new ArrayList<WeekViewEvent>();
        clickedTime=Calendar.getInstance();

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));

        mWeekView.setMonthChangeListener(this);
        mWeekView.setEmptyViewClickListener(this);
        mWeekView.setOnEventClickListener(this);
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
        getSeance();
    }

  /*  public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


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
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

                            for (int i = 0; i < dataArray.length(); i++)
                            {
                                seances = new Seance();

                                dataobj = dataArray.getJSONObject(i);
                                seances.setId(dataobj.getInt("id"));
                                seances.setDateDebut(dateFormat.parse(dataobj.getString("DateDebut")));
                                seances.setDateFin(dateFormat.parse(dataobj.getString("DateFin")));
                                seances.setNbrRep(dataobj.getInt("NbrRep"));

                                dataModelArrayList.add(seances);

                                List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();


                           /*     startTime = Calendar.getInstance();
                                startTime.set(seances.getDateDebut().getYear(), seances.getDateDebut().getMonth(), seances.getDateDebut().getDay(), 6, 00);
                                endTime = Calendar.getInstance();
                                endTime.set(seances.getDateDebut().getYear(),  seances.getDateDebut().getMonth(),  seances.getDateDebut().getDay(), 9, 00);
                                WeekViewEvent event2 = new WeekViewEvent(0,"SEANCE",startTime, endTime);
                                event2.setColor(getResources().getColor(R.color.blue));

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


        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
         if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;

















      /*  Calendar startTime = (Calendar) clickedTime.clone();
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        event = new WeekViewEvent(1, "Event Name", startTime, endTime);
        //event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);



        return events;









    }*/

  /*  public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
      // Populate the week view with some events.
      events = new ArrayList<WeekViewEvent>();
        Calendar startTime = (Calendar) clickedTime.clone();
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
     /* startTime = Calendar.getInstance();
      startTime.set(Calendar.DAY_OF_MONTH, 1);
      startTime.set(Calendar.HOUR_OF_DAY, 5);
      startTime.set(Calendar.MINUTE, 0);
      startTime.set(Calendar.MONTH, 04);
      startTime.set(Calendar.YEAR, 2020);
      endTime = (Calendar) startTime.clone();
      endTime.set(Calendar.DAY_OF_MONTH, 30);
      endTime.set(Calendar.HOUR_OF_DAY, 5);
      endTime.set(Calendar.MINUTE, 0);
      endTime.set(Calendar.MONTH, 04);
      endTime.set(Calendar.YEAR, 2020);
       event = new WeekViewEvent(0, "Epoca", startTime, endTime);
     event.setColor(getResources().getColor(R.color.blue));
      events.add(event);


      startTime = Calendar.getInstance();
      startTime.set(2020, 04,13, 6, 00);
      endTime = Calendar.getInstance();
        endTime.set(2020, 04,13, 8, 00);
      WeekViewEvent event2 = new WeekViewEvent(0,"SEANCE",startTime, endTime);
      event2.setColor(getResources().getColor(R.color.blue));
      events.add(event2);



      return events;
  }*/
  /*public List<WeekViewDisplayable<Event>> onMonthChange(Calendar startDate, Calendar endDate) {

      List<WeekViewDisplayable<Event>> weekViewEvents = new ArrayList<>();

      final int color1 = getResources().getColor(R.color.event_color_01);
      final int color2 = getResources().getColor(R.color.event_color_02);
      final int color3 = getResources().getColor(R.color.event_color_03);
      final int color4 = getResources().getColor(R.color.event_color_04);

      if (events != null){
          infoPrint("event size : " + events.size());
          for (int i = 0; i < events.size(); i++){
              Event e = events.get(i);
              WeekViewEvent wve = new WeekViewEvent();

              // Set ID (not the Google Calendar ID. I guess. Long.parseLong(e.getId()))
              wve.setId(i);

              // Set Title
              wve.setTitle(e.getSummary());

              //Set dates and times
              //IMPORTANT: Get the current year and month that the event is supposed to be displayed in.
              final int newYear = startDate.get(Calendar.YEAR);
              final int newMonth = startDate.get(Calendar.MONTH);

              // Start Time
              Calendar startCal = Calendar.getInstance();
              startCal.setTimeInMillis(e.getStart().getDateTime().getValue());
              //IMPORTANT: Call Calendar.set() as such, and it'll fix everything.
              startCal.set(Calendar.MONTH, newMonth);
              startCal.set(Calendar.YEAR, newYear);
              wve.setStartTime(startCal);

              // End Time
              Calendar endCal = Calendar.getInstance();
              endCal.setTimeInMillis(e.getEnd().getDateTime().getValue());
              //IMPORTANT: Call Calendar.set() as such, and it'll fix everything.
              endCal.set(Calendar.MONTH, newMonth);
              endCal.set(Calendar.YEAR, newYear);
              wve.setEndTime(endCal);

              wve.setColor(color1);

              wve.setIsAllDay(false);

              weekViewEvents.add(wve);
          }
      }
      return weekViewEvents;
  }*/
    /**
     * Checks if an event falls into a specific year and month.
     * @param event The event to check for.
     * @param year The year.
     * @param month The month.
     * @return True if the event matches the year and month.
     */
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
        clickedTime=(Calendar) time.clone();
        mWeekView.notifyDatasetChanged();
        Log.i("msg","Empty box has been filled successfully.");
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

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
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

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


                                String [] dateParts = dataobj.getString("DateDebut").split("-");
                                String year = dateParts[0];
                                String month = dateParts[1];
                                String day = dateParts[2];

                                Log.d(" date", "month:"  + month + "day:" + day  + "year:" + year);


                                dataModelArrayList.add(seances);

                             /*  startTime = Calendar.getInstance();
                                startTime.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 6, 00);
                                endTime = Calendar.getInstance();
                                endTime.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 6, 00);
                                WeekViewEvent event2 = new WeekViewEvent(0,"SEANCE",startTime, endTime);
                                event2.setColor(getResources().getColor(R.color.blue));*/

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

   /* @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        Calendar startTime = (Calendar) clickedTime.clone();
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth);
        startTime.set(Calendar.YEAR, newYear);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);

        event = new WeekViewEvent(1, "Event Name", startTime, endTime);
        Log.d("start time",startTime.toString());

        events.add(event);



        return events;
    }*/

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {



        // Return only the events that matches newYear and newMonth.
        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

}
