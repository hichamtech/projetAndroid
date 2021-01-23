package com.example.emploiandroid.EspaceAdministrator.Seance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emploiandroid.Models.Seance;
import com.example.emploiandroid.Models.URLs;
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



    private AgendaCalendarView mAgendaCalendarView;
    private static final String DEBUGTAG = CalendarActivity.class.getCanonicalName();
    private static String URL_BASE = "http://192.168.1.13:8000/api/seances";
    private ArrayList<Seance> dataModelArrayList=null;
    private ListAdapterSeance listAdapter;
    private ListView listView;
    private Seance seances;
    private JSONObject dataobj;
    private JSONArray dataArray;
    private BaseCalendarEvent event;
    private static ProgressDialog mProgressDialog;
    private URLs urLs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_calandar_view);

        mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
        urLs = new URLs();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUGTAG, "OnResume");

        List<CalendarEvent> eventList = new ArrayList<>();
        getSeance(eventList);



    }


    public void getSeance(final List<CalendarEvent> eventList){
            AfficherProgressDialog(this, "Chargement..","Recherche seances",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urLs.URL_LISTESEANCE,
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
                            final JSONObject obj = new JSONObject(response);
                            dataArray = obj.getJSONArray("hydra:member");
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
                                /*event = new BaseCalendarEvent("seance", "A beautiful small town", "Nombre repetition " +seances.getNbrRep() ,
                                        Color.RED, cal, cal, true);*/

                                event = new BaseCalendarEvent(seances.getId(), Color.RED, "Seance", "Description", " Nombre repetition " + seances.getNbrRep(), sdf.parse(dataobj.getString("DateDebut")).getTime(), sdf.parse(dataobj.getString("DateDebut")).getTime(), 0, "");
                                Log.d(DEBUGTAG, "cal " +  String.valueOf(cal));
                                eventList.add(event);


                            }
                            CalendarPickerController calendarPickerController = new CalendarPickerController() {
                                @Override
                                public void onDaySelected(DayItem dayItem) {

                                }

                                @Override
                                public void onEventSelected(CalendarEvent event) {

                                    Log.d(DEBUGTAG,"this is my id" + event.getId());
                                   /* Intent intent = new Intent(AgendaCalandarView.this,DetailSeanceActivity.class);
                                    intent.putExtra("idSeance", event.getId());
                                    intent.putExtra("start", event.getStartTime());
                                    intent.putExtra("end", event.getEndTime());
                                    intent.putExtra("title", event.getTitle());
                                    startActivity(intent);*/
                                    if (event == null){
                                       return;
                                    }
                                    final Dialog dialog=new Dialog(AgendaCalandarView.this);
                                    dialog.setContentView(R.layout.activity_detail_seance);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setCancelable(false);
                                    LinearLayout closebtn=dialog.findViewById(R.id.btnRetour);
                                    TextView title = dialog.findViewById(R.id.rebooking_single_tv);
                                    title.setText(event.getTitle().toUpperCase());
                                    TextView  datedebut = dialog.findViewById(R.id.lblDateDebut);
                                    TextView  dateFin = dialog.findViewById(R.id.lblDateFin);
                                    TextView nbrR = dialog.findViewById(R.id.lblnbrR);
                                    datedebut.setText( event.getStartTime().getTime().toString() );
                                    dateFin.setText( event.getEndTime().getTime().toString() );
                                    nbrR.setText("Nombre Rep: 5 ");
                                    // TextView txtStart = findViewById(R.id.txtStart);
                                  //  TextView txtEnd = findViewById(R.id.txtEnd);

                                 //   txtStart.setText("date de debut");
                                   // txtEnd.setText("date de fin");



                                    closebtn.setOnClickListener(
                                            new View.OnClickListener(){
                                                @Override
                                                public void onClick(View v){
                                                    dialog.dismiss();
                                                }
                                            });
                                    dialog.show();

                                }

                                @Override
                                public void onScrollToDate(Calendar calendar) {

                                }
                            };

                            mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), calendarPickerController);

                            supprimerSimpleProgressDialog();



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


    public static void supprimerSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (Exception ie) {
            ie.printStackTrace();

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

        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }

}
