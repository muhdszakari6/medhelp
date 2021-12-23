package com.salim.medhelp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Alarm_pojo;
import com.salim.medhelp.pojo.Rem_pojo;
import com.salim.medhelp.pojo.Signup_pojo;
import com.salim.medhelp.pojo.Trigger_pojo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabletReminderForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabletReminderForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabletReminderForm extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    CheckBox monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    String freq = "";

    private static final String initial_trig = "inittrig";
    private static final String trig_alarm_id = "alarm_id";



    ListView listView;
    Button createrem;
    EditText remtime,dose;
    TabRemAdapter adapter;
    TextView remdate;
    AlarmManager alarmManager;
    long tabId;
    public TabletReminderForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabletReminderForm.
     */
    // TODO: Rename and change types and number of parameters
    public static TabletReminderForm newInstance(String param1, String param2) {
        TabletReminderForm fragment = new TabletReminderForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tablet_reminder_form, container, false);

        TimePickerDialog.OnTimeSetListener tlistener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (i <= 9) {
                    remtime.setText("0" + i + ":" + String.format("%02d",i1));
                } else
                    remtime.setText(i + ":" +String.format("%02d",i1));

            }
        };
        final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),0,tlistener,20,18,true);

        // Inflate the layout for this fragment

        listView = view.findViewById(android.R.id.list);
        dose = view.findViewById(R.id.remdose);

        monday = view.findViewById(R.id.monday);
        tuesday = view.findViewById(R.id.tuesday);
        wednesday = view.findViewById(R.id.wednesday);
        thursday = view.findViewById(R.id.thursday);
        friday = view.findViewById(R.id.friday);
        saturday = view.findViewById(R.id.saturday);
        sunday = view.findViewById(R.id.sunday);

        createrem = view.findViewById(R.id.createrem);
        remdate = view.findViewById(R.id.remdate);
        Calendar todaysdate = new GregorianCalendar();
        int todays_year = todaysdate.get(Calendar.YEAR);
        int todays_month = todaysdate.get(Calendar.MONTH);
        int todays_day = todaysdate.get(Calendar.DAY_OF_MONTH);
        remdate.setText(todays_day+"/"+todays_month+"/"+todays_year);
        alarmManager = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);

        final Bundle bundle = this.getArguments();

        remdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        remtime = view.findViewById(R.id.remtime);
        remtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
        createrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date, time,dos;
                date = remdate.getText().toString();
                time = remtime.getText().toString();
                dos = dose.getText().toString();

                Calendar datePicker = new GregorianCalendar();

                int year = datePicker.get(Calendar.YEAR);
                int month = datePicker.get(Calendar.MONTH);
                int day = datePicker.get(Calendar.DAY_OF_MONTH);

                remdate.setText(day+"/"+month+"/"+year);


                if (time.isEmpty()) {
                    remtime.setError("Enter a time");
                }
                else if (dos.isEmpty()){
                    dose.setError("Enter the number of dosage");
                }

                else {




                    int hour = Integer.parseInt(time.substring(0, 2));
                    int minute = Integer.parseInt(time.substring(3));

                    if (hour <= 9) {
                        time = "0" + hour + ":" + String.format("%02d", minute);
                    }
                    else
                        time = (hour + ":" + String.format("%02d", minute));


                    int type = AlarmManager.RTC;


                    GregorianCalendar calendar = new GregorianCalendar(year,month,day,hour,minute);

                    GregorianCalendar calendar2 = new GregorianCalendar();

                    long miliisnow = calendar2.getTimeInMillis();
                    long millis = calendar.getTimeInMillis();

                    if (millis < miliisnow) {
                        Toast.makeText(getContext(), "Invalid date or time", Toast.LENGTH_LONG).show();
                    }

                    else {


                        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                        long user_id = preferences.getLong("id", 1092L);
                        Alarm_pojo result = new Alarm_pojo();

                        tabId = 0;
                        if (bundle != null) {
                            tabId = bundle.getLong("id");
                        }

                        result.setTime(remtime.getText().toString());
                        result.setDate(remdate.getText().toString());
                        result.setTrig(millis);
                        result.setUser_id(user_id);
                        result.setRem_id(tabId);
                        result.setDose(Integer.parseInt(dose.getText().toString()));


                        Signup_pojo signup_pojo = new Signup_pojo();
                        signup_pojo.setId(user_id);

                        Rem_pojo rem_pojo = new Rem_pojo();
                        rem_pojo.setId(tabId);

                        Signup_crud crud = new Signup_crud(getContext());

                        Alarm_pojo pojo = crud.addAlarm(result, signup_pojo, rem_pojo);



                        if (pojo.getMessage() == "success") {

                            Intent itActivity = new Intent();
                            Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);

                            itActivity.setAction("com.salim.medhelp.rem");


                            freq = "";

                            if (monday.isChecked()){
                                freq += "Mon ";


                                Calendar moncal = Calendar.getInstance();


                                moncal.set(year,month,day);

                                moncal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                                moncal.set(Calendar.HOUR_OF_DAY, hour);
                                moncal.set(Calendar.MINUTE, minute);
                                moncal.set(Calendar.SECOND, 0);
                                moncal.set(Calendar.MILLISECOND, 0);

                                if(moncal.getTimeInMillis()<System.currentTimeMillis()){
                                    moncal.add(Calendar.DAY_OF_YEAR,new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1);
                                }


                                PendingIntent piActivity1 = PendingIntent.getActivity(getContext(),(int) moncal.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService1 = PendingIntent.getService(getContext(),(int)moncal.getTimeInMillis(), itService, 0);



                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        moncal.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piActivity1);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        moncal.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piService1);

                                Trigger_pojo monpojo = new Trigger_pojo();
                                monpojo.setInitialtrig(moncal.getTimeInMillis());
                                monpojo.setTrig(moncal.getTimeInMillis());
                                monpojo.setAlarm_id(pojo.getId());
                                monpojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);

                                int mon = crud.updateAlarm(pojo, signup_pojo, rem_pojo);

                                Trigger_pojo monpojo1 = crud.addTrig(monpojo,pojo);





                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();


                            }

                            if (tuesday.isChecked()) {
                                freq +="Tue ";

                                Calendar calendar3 = Calendar.getInstance();

                                calendar3.set(year,month,day);


                                calendar3.set(Calendar.DAY_OF_WEEK, 3);
                                calendar3.set(Calendar.HOUR_OF_DAY, hour);
                                calendar3.set(Calendar.MINUTE, minute);
                                calendar3.set(Calendar.SECOND, 0);
                                calendar3.set(Calendar.MILLISECOND, 0);

                                if(calendar3.getTimeInMillis()<System.currentTimeMillis()){
                                    calendar3.add(Calendar.DAY_OF_YEAR,new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1);
                                }



                                PendingIntent piActivity2 = PendingIntent.getActivity(getContext(), (int)calendar3.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService2 = PendingIntent.getService(getContext(), (int)calendar3.getTimeInMillis(), itService, 0);



                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar3.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piActivity2);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar3.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piService2);




                                Trigger_pojo tuepojo = new Trigger_pojo();
                                tuepojo.setInitialtrig(calendar3.getTimeInMillis());
                                tuepojo.setTrig(calendar3.getTimeInMillis());
                                tuepojo.setAlarm_id(pojo.getId());
                                tuepojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);


                                Trigger_pojo tuepojo1 = crud.addTrig(tuepojo,pojo);

                                int tue = crud.updateAlarm(pojo, signup_pojo, rem_pojo);




                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();


                            }

                            if (wednesday.isChecked()) {
                                freq+="Wed ";
                                Calendar calendar4 = Calendar.getInstance();
                                calendar4.set(year,month,day);

                                calendar4.set(Calendar.DAY_OF_WEEK, 4);
                                calendar4.set(Calendar.HOUR_OF_DAY, hour);
                                calendar4.set(Calendar.MINUTE, minute);
                                calendar4.set(Calendar.SECOND, 0);
                                calendar4.set(Calendar.MILLISECOND, 0);

                                if(calendar4.getTimeInMillis()<System.currentTimeMillis()){
                                    calendar4.add(Calendar.DAY_OF_YEAR,new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1);
                                }




                                PendingIntent piActivity3 = PendingIntent.getActivity(getContext(), (int)calendar4.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService3 = PendingIntent.getService(getContext(), (int)calendar4.getTimeInMillis(), itService, 0);

                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar4.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piActivity3);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar4.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piService3);

                                Trigger_pojo wedpojo = new Trigger_pojo();
                                wedpojo.setInitialtrig(calendar4.getTimeInMillis());
                                wedpojo.setTrig(calendar4.getTimeInMillis());
                                wedpojo.setAlarm_id(result.getId());
                                wedpojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);


                                Trigger_pojo wedpojo1 = crud.addTrig(wedpojo,pojo);

                                int tue = crud.updateAlarm(pojo, signup_pojo, rem_pojo);




                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();


                            }


                            if (thursday.isChecked()) {
                                freq+="Thur ";
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(year,month,day);

                                calendar1.set(Calendar.DAY_OF_WEEK, 5);
                                calendar1.set(Calendar.HOUR_OF_DAY, hour);
                                calendar1.set(Calendar.MINUTE, minute);
                                calendar1.set(Calendar.SECOND, 0);
                                calendar1.set(Calendar.MILLISECOND, 0);

                                if(calendar1.getTimeInMillis()<System.currentTimeMillis()){
                                    calendar1.add(Calendar.DAY_OF_YEAR,new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1);
                                }



                                PendingIntent piActivity4 = PendingIntent.getActivity(getContext(), (int)calendar1.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService4 = PendingIntent.getService(getContext(), (int)calendar1.getTimeInMillis(), itService, 0);


                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar1.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piActivity4);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar1.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piService4);



                                Trigger_pojo thurspojo = new Trigger_pojo();
                                thurspojo.setInitialtrig(calendar1.getTimeInMillis());
                                thurspojo.setTrig(calendar1.getTimeInMillis());
                                thurspojo.setAlarm_id(result.getId());
                                thurspojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);


                                Trigger_pojo thurspojo1 = crud.addTrig(thurspojo,pojo);

                                int tue = crud.updateAlarm(pojo, signup_pojo, rem_pojo);


                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();


                            }


                            if (friday.isChecked()) {
                                freq+="Fri ";
                                Calendar Fcalendar = Calendar.getInstance();
                                Fcalendar.set(year,month,day);

                                Fcalendar.set(Calendar.DAY_OF_WEEK, 6);
                                Fcalendar.set(Calendar.HOUR_OF_DAY, hour);
                                Fcalendar.set(Calendar.MINUTE, minute);
                                Fcalendar.set(Calendar.SECOND, 0);
                                Fcalendar.set(Calendar.MILLISECOND, 0);

                                if(Fcalendar.getTimeInMillis()<System.currentTimeMillis()){
                                    Fcalendar.add(Calendar.DAY_OF_YEAR,new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1);
                                    if(Fcalendar.getTimeInMillis()==millis){
                                        Toast.makeText(getContext(), "it is millis ", Toast.LENGTH_LONG).show();


                                    }

                                }



                                PendingIntent piActivity5 = PendingIntent.getActivity(getContext(), (int)Fcalendar.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService5 = PendingIntent.getService(getContext(), (int)Fcalendar.getTimeInMillis(), itService, 0);

                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        Fcalendar.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piActivity5);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        Fcalendar.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piService5);



                                Trigger_pojo fripojo = new Trigger_pojo();
                                fripojo.setInitialtrig(Fcalendar.getTimeInMillis());
                                fripojo.setTrig(Fcalendar.getTimeInMillis());
                                fripojo.setAlarm_id(result.getId());
                                fripojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);


                                Trigger_pojo fripojo1 = crud.addTrig(fripojo,pojo);

                                int tue = crud.updateAlarm(pojo, signup_pojo, rem_pojo);



                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();


                            }


                            if (saturday.isChecked()) {
                                freq+="Sat ";

                                Calendar calendar5 = Calendar.getInstance();


                                calendar5.set(year,month,day);

                                calendar5.set(Calendar.DAY_OF_WEEK, 7);
                                calendar5.set(Calendar.HOUR_OF_DAY, hour);
                                calendar5.set(Calendar.MINUTE, minute);
                                calendar5.set(Calendar.SECOND, 0);
                                calendar5.set(Calendar.MILLISECOND, 0);

                                if(calendar5.getTimeInMillis()==millis){


                                }
                                if(calendar5.getTimeInMillis()<System.currentTimeMillis()){
                                    calendar5.add(Calendar.DAY_OF_YEAR,new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1);
                                }


                                PendingIntent piActivity6 = PendingIntent.getActivity(getContext(), (int)calendar5.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService6 = PendingIntent.getService(getContext(), (int)calendar5.getTimeInMillis(), itService, 0);


                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar5.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piActivity6);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar5.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piService6);



                                Trigger_pojo satpojo = new Trigger_pojo();
                                satpojo.setInitialtrig(calendar5.getTimeInMillis());
                                satpojo.setTrig(calendar5.getTimeInMillis());
                                satpojo.setAlarm_id(result.getId());
                                satpojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);


                                Trigger_pojo satpojo1 = crud.addTrig(satpojo,pojo);

                                int tue = crud.updateAlarm(pojo, signup_pojo, rem_pojo);

                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();



                            }

                            if (sunday.isChecked()) {
                                freq += "Sun ";


                                Calendar calendar6 = Calendar.getInstance();
                                calendar6.set(year,month,day);

                                calendar6.set(Calendar.DAY_OF_WEEK, 1);
                                calendar6.set(Calendar.HOUR_OF_DAY, hour);
                                calendar6.set(Calendar.MINUTE, minute);
                                calendar6.set(Calendar.SECOND, 0);
                                calendar6.set(Calendar.MILLISECOND, 0);

                                if(calendar6.getTimeInMillis()<System.currentTimeMillis()){
                                    calendar6.add(Calendar.DAY_OF_YEAR,new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-1);

                                }

                                if(calendar6.getTimeInMillis()==millis){

                                }


                                    PendingIntent piActivity7 = PendingIntent.getActivity(getContext(), (int)calendar6.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService7 = PendingIntent.getService(getContext(), (int)calendar6.getTimeInMillis(), itService, 0);


                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar6.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piActivity7);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar6.getTimeInMillis(), 24 * 60 * 60 * 1000 * 7, piService7);


                                Trigger_pojo sunpojo = new Trigger_pojo();
                                sunpojo.setInitialtrig(calendar6.getTimeInMillis());
                                sunpojo.setTrig(calendar6.getTimeInMillis());
                                sunpojo.setAlarm_id(result.getId());
                                sunpojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);


                                Trigger_pojo sunpojo1 = crud.addTrig(sunpojo,pojo);

                                int tue = crud.updateAlarm(pojo, signup_pojo, rem_pojo);


                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();


                            }


                            if(monday.isChecked() && tuesday.isChecked() && wednesday.isChecked() &&
                                    thursday.isChecked() && friday.isChecked() && saturday.isChecked() && sunday.isChecked()){
                                freq = "Everyday";

                                //PendingIntent piActivity = PendingIntent.getActivity(getContext(), (int)millis, itActivity, 0);
                                //PendingIntent piService = PendingIntent.getService(getContext(), (int)millis, itService, 0);


                               // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        //millis, 24*60*60*1000, piActivity);


                                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                       // millis , 24*60*60*1000, piService);


                               /* Trigger_pojo epojo = new Trigger_pojo();
                                epojo.setInitialtrig(millis);
                                epojo.setTrig(millis);
                                epojo.setAlarm_id(result.getId());
                                epojo.setRem_id(pojo.getRem_id());*/


                                pojo.setFreq(freq);


                                //Trigger_pojo epojo1 = crud.addTrig(epojo,pojo);

                                int e = crud.updateAlarm(pojo, signup_pojo, rem_pojo);



                            }



                            if (!monday.isChecked() && !tuesday.isChecked() && !wednesday.isChecked() &&
                                    !thursday.isChecked() && !friday.isChecked() && !saturday.isChecked() && !sunday.isChecked()) {
                                freq = "Everyday";

                                PendingIntent piActivity = PendingIntent.getActivity(getContext(), (int)calendar.getTimeInMillis(), itActivity, 0);
                                PendingIntent piService = PendingIntent.getService(getContext(), (int)calendar.getTimeInMillis(), itService, 0);


                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar.getTimeInMillis(), 24*60*60*1000, piActivity);


                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar.getTimeInMillis() , 24*60*60*1000, piService);


                                Trigger_pojo epojo = new Trigger_pojo();
                                epojo.setInitialtrig(millis);
                                epojo.setTrig(millis);
                                epojo.setAlarm_id(result.getId());
                                epojo.setRem_id(pojo.getRem_id());


                                pojo.setFreq(freq);


                                Trigger_pojo epojo1 = crud.addTrig(epojo,pojo);

                                int e = crud.updateAlarm(pojo, signup_pojo, rem_pojo);

                                Toast.makeText(getContext(), "Reminder set ", Toast.LENGTH_LONG).show();


                            }


                        }
                    }


                     tabId = 0;
                    if (bundle != null) {
                        tabId = bundle.getLong("id");
                    }


                    SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                    long user_id = preferences.getLong("id", 1092L);

                    Signup_pojo signup_pojo = new Signup_pojo();
                    signup_pojo.setId(user_id);

                    Rem_pojo rem_pojo = new Rem_pojo();
                    rem_pojo.setId(tabId);

                    Signup_crud scrud = new Signup_crud(getContext());

                    Cursor cursor = scrud.getAlarms(signup_pojo,rem_pojo);
                    adapter =new TabRemAdapter(getContext(),R.layout.alarmitem,cursor,0);

                    listView.setAdapter(adapter);



    };


            }
        });
         tabId = 0;
        if (bundle != null) {
            tabId = bundle.getLong("id");
        }
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        long user_id = preferences.getLong("id", 1092L);

        Signup_pojo signup_pojo = new Signup_pojo();
        signup_pojo.setId(user_id);

        Rem_pojo rem_pojo = new Rem_pojo();
        rem_pojo.setId(tabId);

        Signup_crud scrud = new Signup_crud(getContext());

        Cursor cursor = scrud.getAlarms(signup_pojo,rem_pojo);
        adapter =new TabRemAdapter(getContext(),R.layout.alarmitem,cursor,0);

        listView.setAdapter(adapter);




        return view;

    }



    public void swap(){


        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        long user_id = preferences.getLong("id", 1092L);

        Signup_pojo signup_pojo = new Signup_pojo();
        signup_pojo.setId(user_id);

        Rem_pojo rem_pojo = new Rem_pojo();
        rem_pojo.setId(tabId);

        Signup_crud scrud = new Signup_crud(getContext());

        Cursor cursor = scrud.getAlarms(signup_pojo,rem_pojo);
        adapter =new TabRemAdapter(getContext(),R.layout.alarmitem,cursor,0);

        listView.setAdapter(adapter);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int i, final long l) {
                //  Toast.makeText(getContext(), "List Item " + l, Toast.LENGTH_LONG).show();
                final PopupMenu menu = new PopupMenu(getActivity(), view);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();


                        switch (id) {
                            case R.id.deletealarmmenu:

                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("Dosage Reminder");
                                alert.setMessage("Do you want to delete this alarm?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        Alarm_pojo pojo = new Alarm_pojo();
                                        pojo.setId(l);

                                        Signup_crud signup_crud = new Signup_crud(getContext());

                                        Cursor cursor = signup_crud.getAlarmTrigs(pojo);

                                        if(cursor!=null&&cursor.getCount()>0){
                                            Alarm_pojo pojo1 = new Alarm_pojo();

                                            while(cursor.moveToNext()){


                                                long id = cursor.getLong(cursor.getColumnIndex(trig_alarm_id));
                                                int theid = (int)cursor.getLong(cursor.getColumnIndex(initial_trig));


                                                Intent itActivity = new Intent();
                                                itActivity.setAction("com.salim.medhelp.rem");
                                                Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);


                                                //PendingIntent piBroadcast = PendingIntent.getBroadcast(getContext(), theid, itBroadcast, 0);
                                                PendingIntent piActivity = PendingIntent.getActivity(getContext(), theid, itActivity, 0);
                                                PendingIntent piService = PendingIntent.getService(getContext(), theid, itService, 0);

                                                alarmManager.cancel(piActivity);
                                                //alarmManager.cancel(piBroadcast);
                                                alarmManager.cancel(piService);


                                            }
                                            pojo1.setId(l);


                                            signup_crud.delete_Alarm(pojo1);
                                            cursor.close();

                                        }
                                        else{
                                            Toast.makeText(getContext(),"Cursor Empty",Toast.LENGTH_LONG).show();
                                            cursor.close();

                                        }
                                        cursor.close();


                                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                                        swap();

                                    }

                                });

                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alert.show();
                                break;

                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.alarmmenu);
                menu.show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
