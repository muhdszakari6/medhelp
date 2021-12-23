package com.salim.medhelp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Ap_pojo;
import com.salim.medhelp.pojo.Signup_pojo;

import java.util.GregorianCalendar;
import java.util.Objects;
import com.google.android.libraries.places.api.Places;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocAppointment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocAppointment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    EditText hospitalloc,complain,apdate,aptime;
    ListView listView;
    double lat,lon;
    Button addap;
    AlarmManager alarmManager;
    DocApAdapter adapter;



    public DocAppointment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocAppointment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocAppointment newInstance(String param1, String param2) {
        DocAppointment fragment = new DocAppointment();
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
        View view = inflater.inflate(R.layout.fragment_doc_appointment, container, false);
        TimePickerDialog.OnTimeSetListener tlistener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (i <= 9) {
                    aptime.setText("0" + i + ":" + String.format("%02d",i1));
                } else
                    aptime.setText(i + ":" +String.format("%02d",i1));

            }
        };
        final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),0,tlistener,20,18,true);
        DatePickerDialog.OnDateSetListener dlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                apdate.setText(i2 + "/" + i1 + "/" + i);

            }
        };
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),0, dlistener, 2018, 11, 29);

        hospitalloc = view.findViewById(R.id.hospitalloc);
        complain = view.findViewById(R.id.complain);
        apdate = view.findViewById(R.id.docappointmentdate);
        apdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        aptime = view.findViewById(R.id.docappointmenttime);
        aptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        listView = view.findViewById(android.R.id.list);

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);


        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);

        final long id = preferences.getLong("id", 1092L);

        hospitalloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {

                    Intent intent = builder.build(Objects.requireNonNull(getActivity()));
                    startActivityForResult(intent,5);


                } catch (GooglePlayServicesRepairableException e) {
                    Log.e("e.printStackTrace()" ,e.getMessage());
                    Toast.makeText(getContext(), "Update google play services on play store so that to use a place picker.", Toast.LENGTH_LONG).show();

                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e("e.printStackTrace()" ,e.getMessage());
                    Toast.makeText(getContext(), "Update google play services on play store so that to use a place picker.", Toast.LENGTH_LONG).show();

                }

            }
        });


        addap = view.findViewById(R.id.addappointment);

        addap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loc,comp,date,time;
                loc = hospitalloc.getText().toString();
                comp = complain.getText().toString();
                date = apdate.getText().toString();
                time = aptime.getText().toString();
                if(loc.isEmpty()){
                    hospitalloc.setError("Please enter hospital location");
                }
                else if(comp.isEmpty()){
                    complain.setError("Please enter your complain");

                }
                else if(date.isEmpty()){
                    apdate.setError("Please enter a date");
                }
                else if(time.isEmpty()){
                    aptime.setError("Please enter a time");
                }
                else{
                    DatePicker datePicker = datePickerDialog.getDatePicker();


                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();

                    int hour = Integer.parseInt(time.substring(0, 2));
                    int minute = Integer.parseInt(time.substring(3));

                    if (hour <= 9) {
                        time = "0" + hour + ":" + String.format("%02d", minute);
                    }
                    else
                        time = (hour + ":" + String.format("%02d", minute));


                    int type = AlarmManager.RTC;


                    GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute);
                    GregorianCalendar calendar1 = new GregorianCalendar();
                    long miliisnow = calendar1.getTimeInMillis();
                    long millis = calendar.getTimeInMillis();
                    if (millis < miliisnow) {
                        Toast.makeText(getContext(), "Invalid date or time", Toast.LENGTH_LONG).show();
                    }
                    else {

                        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                        long user_id = preferences.getLong("id", 1092L);
                        final int id = (int) System.currentTimeMillis();
                        Ap_pojo pojo = new Ap_pojo();

                        pojo.setLocation(hospitalloc.getText().toString());
                        pojo.setComplain(complain.getText().toString());
                        pojo.setDate(apdate.getText().toString());
                        pojo.setTime(aptime.getText().toString());
                        pojo.setLat(lat);
                        pojo.setLon(lon);
                        pojo.setUser(user_id);
                        pojo.setTrig(id);

                        Signup_pojo signup_pojo = new Signup_pojo();
                        signup_pojo.setId(user_id);
                        Signup_crud crud = new Signup_crud(getContext());
                        Ap_pojo result = crud.addAp(pojo, signup_pojo);

                        if(result.getMessage()=="success") {

                            Intent itActivity = new Intent();
                            Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);
                            //Intent itBroadcast = new Intent(getContext(), ReminderReciever.class);


                            itActivity.setAction("com.salim.medhelp.ap");

                           // PendingIntent piBroadcast = PendingIntent.getBroadcast(getContext(), id, itBroadcast, 0);
                            PendingIntent piActivity = PendingIntent.getActivity(getContext(), id, itActivity, 0);
                            PendingIntent piService = PendingIntent.getService(getContext(), id, itService,  0);

                            alarmManager.set(type, millis, piActivity);
                            //alarmManager.set(type, millis, piBroadcast);
                            alarmManager.set(type, millis, piService);

                            Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();

                        }
                    }




                    }

                Signup_crud crud = new Signup_crud(getContext());
                Signup_pojo pojo = new Signup_pojo();
                pojo.setId(id);
                Cursor cursor = crud.getAps(pojo);
                adapter = new DocApAdapter(getContext(),R.layout.appointmentitem,cursor,0);
                listView.setAdapter(adapter);

            }
        });

        Signup_crud crud = new Signup_crud(getContext());
        Signup_pojo pojo = new Signup_pojo();
        pojo.setId(id);
        Cursor cursor = crud.getAps(pojo);
        adapter = new DocApAdapter(getContext(),R.layout.appointmentitem,cursor,0);
        listView.setAdapter(adapter);
        return view;
    }

    public void swap(){
        Signup_crud crud = new Signup_crud(getContext());
        Signup_pojo spojo = new Signup_pojo();
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
        long tid = preferences.getLong("id",1092L);
        spojo.setId(tid);

        adapter.swapCursor(crud.getAps(spojo));
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
                            case R.id.deleteappointment:
                                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("Appointment");
                                alert.setMessage("Do you want to delete this appointment?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        Intent itActivity = new Intent();
                                        Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);
                                        //Intent itBroadcast = new Intent(getContext(), ReminderReciever.class);

                                        Signup_crud crud = new Signup_crud(getContext());

                                        itActivity.setAction("com.salim.medhelp.ap");
                                        Ap_pojo ap = new Ap_pojo();
                                        ap.setId(l);

                                        Ap_pojo pojo = crud.getAP(ap);


                                        int theid = (int) pojo.getTrig();




                                        //PendingIntent piBroadcast = PendingIntent.getBroadcast(getContext(), theid, itBroadcast, 0);
                                        PendingIntent piActivity = PendingIntent.getActivity(getContext(), theid, itActivity, 0);
                                        PendingIntent piService = PendingIntent.getService(getContext(), theid, itService, 0);

                                        alarmManager.cancel(piActivity);
                                        //alarmManager.cancel(piBroadcast);
                                        alarmManager.cancel(piService);
                                        crud.delete_ap(pojo);
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
                            case R.id.gotomaps:
                                Signup_crud crud = new Signup_crud(getContext());

                                Ap_pojo ap = new Ap_pojo();
                                ap.setId(l);

                                Ap_pojo pojo = crud.getAP(ap);
                                double lat = pojo.getLat();
                                double lon = pojo.getLon();

                                if (lat==0||lon==0){
                                    Toast.makeText(getContext(), "No coordinates available", Toast.LENGTH_LONG).show();

                                }
                                else{
                                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat +"," + lon);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                try
                                {
                                    startActivity(mapIntent);
                                }
                                catch(ActivityNotFoundException ex)
                                {
                                    try
                                    {
                                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW,  Uri.parse("google.navigation:q=" + lat +"," + lon));
                                        startActivity(unrestrictedIntent);
                                    }
                                    catch(ActivityNotFoundException innerEx)
                                    {
                                        Toast.makeText(getContext(), "No map application available", Toast.LENGTH_LONG).show();
                                    }
                                }
                                }

                                break;

                        }
                        return true;
                    }
                });
                menu.inflate (R.menu.appointmentmenu);
                menu.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==5){
            if(resultCode== AppCompatActivity.RESULT_OK){
                Place place = PlacePicker.getPlace(getActivity(),data);
                String address = (String)place.getName();
                LatLng latLng = place.getLatLng();
                lat = latLng.latitude;
                lon = latLng.longitude;
                hospitalloc.setText(address);
            }
        }

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
