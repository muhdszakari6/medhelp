package com.salim.medhelp;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Pres_pojo;
import com.salim.medhelp.pojo.Signup_pojo;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdatePres.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdatePres#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePres extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static int SELECT = 1;
    private static int CAPTURE = 2;
    private OnFragmentInteractionListener mListener;

    EditText updatename,updatetreatment,updatenumofdrug,updatedoseperday,updatefirstdosetime,updatefirstdosedate;
    ImageView updatepic;
    Button updateaddpic,updatesavepres;
    Bitmap thumbnail;
    byte[] dbimg;
    AlarmManager alarmManager;



    public UpdatePres() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatePres.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePres newInstance(String param1, String param2) {
        UpdatePres fragment = new UpdatePres();
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
        View view = inflater.inflate(R.layout.fragment_update_pres, container, false);


        TimePickerDialog.OnTimeSetListener tlistener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (i <= 9) {
                    updatefirstdosetime.setText("0" + i + ":" + String.format("%02d",i1));
                } else
                    updatefirstdosetime.setText(i + ":" +String.format("%02d",i1));

            }
        };

        final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),0,tlistener,20,18,true);
        DatePickerDialog.OnDateSetListener dlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                updatefirstdosedate.setText(i2 + "/" + i1 + "/" + i);

            }
        };

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),0, dlistener, 2018, 11, 29);


        updatename = view.findViewById(R.id.updatetabletname);
        updatetreatment = view.findViewById(R.id.updatetablettreatment);
        updatenumofdrug = view.findViewById(R.id.updatenooftablets);
        updatedoseperday = view.findViewById(R.id.updatedoseperday);
        updatefirstdosetime = view.findViewById(R.id.updatefirstdosetime);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        updatefirstdosetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
        updatefirstdosedate = view.findViewById(R.id.updatefirstdosedate);
        updatefirstdosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        updatepic = view.findViewById(R.id.updateprepic);


        updateaddpic = view.findViewById(R.id.updateaddpicbtn);
        updateaddpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(getContext(),view);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.upchoosetabpicfrmglry:
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");

                                startActivityForResult(intent,SELECT);
                                break;
                            case R.id.upcapturetabpic:
                                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent1,CAPTURE);
                                break;

                            case R.id.upremovetabpic:
                                /*Drawable drawable = getResources().getDrawable(R.drawable.ic_help);
                                Bitmap b = ((BitmapDrawable)drawable).getBitmap();
                                updatepic.setImageResource((R.drawable.ic_help));
                                dbimg = getBitmapAsByteArray(b);*/

                                updatepic.setImageResource(R.drawable.ic_help);
                                dbimg=null;
                                break;
                        }
                        return true;
                    }
                });
                menu.inflate(R.menu.utabpic);
                menu.show();
            }
        });
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            updatename.setText(bundle.getString("tabname", "No"));
            updatetreatment.setText(bundle.getString("tabtreat", "No"));
            updatenumofdrug.setText(String.valueOf(bundle.getInt("number", 99)));
            updatedoseperday.setText(bundle.getString("dose","No"));
            updatefirstdosetime.setText(bundle.getString("time","No"));
            updatefirstdosedate.setText(bundle.getString("date","No"));
            Pres_pojo pojo = new Pres_pojo();
            pojo.setId(bundle.getLong("id"));
            Signup_crud crud = new Signup_crud(getContext());

            Pres_pojo pojo1  = crud.getPres(pojo);
            byte[] image = pojo1.getImage();
            if(image!=null){
                Bitmap pic = BitmapFactory.decodeByteArray(image, 0, image.length);
                updatepic.setImageBitmap(pic);
            }



        }
        final long userid = bundle.getLong("user");
        final long id =    bundle.getLong("id");
        final long cancelalarm = bundle.getLong("trig");

        updatesavepres = view.findViewById(R.id.updatesavepres);
        updatesavepres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1,treat,num,dose,fdtime,ftdate;
                name1 = updatename.getText().toString();
                treat = updatetreatment.getText().toString();
                num = updatenumofdrug.getText().toString();
                dose = updatedoseperday.getText().toString();
                fdtime = updatefirstdosetime.getText().toString();
                ftdate = updatefirstdosedate.getText().toString();
                if(name1.isEmpty()){
                    updatename.setError("Please enter the name of the tablet");
                }
                else if(treat.isEmpty()){
                    updatetreatment.setError("Please enter the treatment assigned to the drug");
                }
                else if(num.isEmpty()){
                    updatenumofdrug.setError("Please enter the number of tablets");
                } else if (dose.isEmpty()) {

                    updatedoseperday.setError("Enter the dose per day of the tablet");
                }
                else if(fdtime.isEmpty()){
                    updatefirstdosetime.setError("Enter the time fo first dose");
                }
                else if(ftdate.isEmpty()){
                    updatefirstdosedate.setError("Enter the date of the first dose");
                }
                else{
                    Pres_pojo pres_pojo = new Pres_pojo();
                    pres_pojo.setName(updatename.getText().toString());
                    pres_pojo.setTreatment(updatetreatment.getText().toString());
                    pres_pojo.setNumber(Integer.parseInt(updatenumofdrug.getText().toString()));
                    pres_pojo.setDose(updatedoseperday.getText().toString());
                    pres_pojo.setFirstdosedate(updatefirstdosedate.getText().toString());
                    pres_pojo.setFirstdosetime(updatefirstdosetime.getText().toString());
                    pres_pojo.setImage(dbimg);
                    pres_pojo.setStatus(0);

                    DatePicker datePicker = datePickerDialog.getDatePicker();

                    int year =  datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();

                    int hour = Integer.parseInt(updatefirstdosetime.getText().toString().substring(0, 2));
                    int minute = Integer.parseInt(updatefirstdosetime.getText().toString().substring(3));

                    int type = AlarmManager.RTC;

                    GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute);

                    long thenum = Long.parseLong(updatenumofdrug.getText().toString()) - 1;
                    long thedoseperday = Long.parseLong(updatedoseperday.getText().toString());
                    long calendarinmilliss = calendar.getTimeInMillis();

                    if(thedoseperday==0){
                        Toast.makeText(getContext(), "Dose per day cant be zero", Toast.LENGTH_LONG).show();

                    }else if(thenum<thedoseperday){
                        Toast.makeText(getContext(),"Number of drugs cant be less than or equals to dose per day",Toast.LENGTH_LONG).show();
                    }

                    else{
                        long fintime = calendarinmilliss + ((thenum/thedoseperday)* TimeUnit.DAYS.toMillis(1));


                        GregorianCalendar calendar1 = new GregorianCalendar();
                        long millisnow = calendar1.getTimeInMillis();


                        if (fintime < millisnow) {
                            Snackbar.make(view, "Our algorithm indicates that your prescription already ended ", Toast.LENGTH_LONG).show();
                            Toast.makeText(getContext(),"Check number of dose per day,number of tablets,or the time and calendar you specified",Toast.LENGTH_LONG).show();


                        }
                        else {


                            pres_pojo.setTrig(fintime);

                            pres_pojo.setUserid(userid);
                            pres_pojo.setId(id);


                            Signup_crud crud = new Signup_crud(getContext());
                            Signup_pojo pojo = new Signup_pojo();
                            pojo.setId(userid);
                            int update = crud.updatepres(pres_pojo,pojo);
                            Toast.makeText(getContext(),update+" ",Toast.LENGTH_LONG).show();
                            if(update!=0){
                                Intent itActivity = new Intent();
                                Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);
                                //Intent itBroadcast = new Intent(getContext(), ReminderReciever.class);


                                itActivity.setAction("com.salim.medhelp.alarm");

                                //PendingIntent piBroadcast = PendingIntent.getBroadcast(getContext(), id, itBroadcast, 0);
                                PendingIntent piActivity = PendingIntent.getActivity(getContext(), (int) fintime, itActivity, 0);
                                PendingIntent piService = PendingIntent.getActivity(getContext(), (int) fintime, itService, 0);

                                alarmManager.set(type, fintime, piActivity);
                                alarmManager.set(type,fintime,piService);


                                PendingIntent cancelservice = PendingIntent.getService(getContext(), (int)cancelalarm, itService,  0);

                                PendingIntent cancel = PendingIntent.getActivity(getContext(), (int) cancelalarm, itActivity, 0);
                                alarmManager.cancel(cancel);
                                alarmManager.cancel(cancelservice);

                                // alarmManager.set(type, millis, piBroadcast);

                                Toast.makeText(getContext(), "Reminder set", Toast.LENGTH_LONG).show();

                            }
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            FragmentManager manager = activity.getSupportFragmentManager();
                            manager.beginTransaction().replace(R.id.frag_container, new ViewPres()).addToBackStack(null).commit();
                    }
                }
            }
            }
        });


        return view;

    }


    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri uri = data.getData();
                    assert uri != null;
                    final InputStream inputStream = Objects.requireNonNull(getContext()).getContentResolver().openInputStream(uri);
                    final Bitmap theimage = (Bitmap)BitmapFactory.decodeStream(inputStream);
                    updatepic.setImageBitmap(theimage);

                    byte[] unvalidated_img = getBitmapAsByteArray(theimage);

                    int size = unvalidated_img.length;
                    if((size/1024)<1050){
                        dbimg = unvalidated_img;
                    }else{
                        dbimg = null;
                        updatepic.setImageResource(R.drawable.ic_help);

                        Toast.makeText(getContext(),"The selected image is too large",Toast.LENGTH_LONG).show();

                    }



                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        else if(requestCode==CAPTURE){
            if(resultCode==RESULT_OK){
                onCaptureImageResult(data);

            }
        }

    }
    public void onCaptureImageResult(Intent data){
        thumbnail = (Bitmap)data.getExtras().get("data");

        updatepic.setImageBitmap(thumbnail);
        dbimg = getBitmapAsByteArray(thumbnail);
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

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
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
