package com.salim.medhelp;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
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
 * {@link AddpresForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddpresForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddpresForm extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText name,treatment,numofdrug,doseperday,firstdosetime,firstdosedate;
    ImageView pic;
    Button addpic,savepres;
    Bitmap thumbnail;
    byte[] dbimg;
    AlarmManager alarmManager;
    private static final int SELECTPHOTO = 1;
    private static final int CAPTUREPHOTO = 2;



    public AddpresForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
0     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddpresForm.
     */
    // TODO: Rename and change types and number of parameters
    public static AddpresForm newInstance(String param1, String param2) {

        AddpresForm fragment = new AddpresForm();
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
                             final Bundle savedInstanceState) {

        TimePickerDialog.OnTimeSetListener tlistener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (i <= 9) {
                    firstdosetime.setText("0" + i + ":" + String.format("%02d",i1));
                } else
                    firstdosetime.setText(i + ":" +String.format("%02d",i1));

            }
        };
        final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),0,tlistener,20,18,true);
        DatePickerDialog.OnDateSetListener dlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                firstdosedate.setText(i2 + "/" + i1 + "/" + i);

            }
        };
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),0, dlistener, 2018, 11, 29);

        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_addpres_form, container, false);
         name = view.findViewById(R.id.tabletname);
         treatment = view.findViewById(R.id.tablettreatment);
         numofdrug = view.findViewById(R.id.nooftablets);
         doseperday = view.findViewById(R.id.doseperday);
         firstdosetime = view.findViewById(R.id.firstdosetime);
         firstdosetime.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 timePickerDialog.show();
             }
         });
         firstdosedate = view.findViewById(R.id.firstdosedate);
         firstdosedate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 datePickerDialog.show();
             }
         });
         pic = view.findViewById(R.id.prepic);
         addpic = view.findViewById(R.id.addpicbtn);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        addpic.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 PopupMenu menu = new PopupMenu(getActivity(),view);
                 menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                     @Override
                     public boolean onMenuItemClick(MenuItem menuItem) {
                         switch (menuItem.getItemId()){
                             case R.id.choosetabpicfrmglry:
                                 Intent intent = new Intent(Intent.ACTION_PICK);
                                 intent.setType("image/*");

                                 startActivityForResult(intent,SELECTPHOTO);
                                 break;
                             case R.id.capturetabpic:
                                 Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                 startActivityForResult(intent1,CAPTUREPHOTO);
                                 break;

                             case R.id.removetabpic:
                                 pic.setImageResource(R.drawable.ic_help);
                                 //dbimg = getBitmapAsByteArray(R.drawable.ic_help);
                                 dbimg = null;
                                 break;


                         }
                         return false;
                     }
                 });
                 menu.inflate(R.menu.tabpic);
                 menu.show();
             }
         });

         savepres = view.findViewById(R.id.savepres);




        savepres.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {
                 String name1,treat,num,dose,fdtime,ftdate;
                 name1 = name.getText().toString();
                 treat = treatment.getText().toString();
                 num = numofdrug.getText().toString();
                 dose = doseperday.getText().toString();
                 fdtime = firstdosetime.getText().toString();
                 ftdate = firstdosedate.getText().toString();
                 if(name1.isEmpty()){
                     name.setError("Please enter the name of the tablet");
                 }
                 else if(treat.isEmpty()){
                     treatment.setError("Please enter the treatment assigned to the drug");
                 }
                 else if(num.isEmpty()){
                     numofdrug.setError("Please enter the number of tablets");
                 } else if (dose.isEmpty()) {

                     doseperday.setError("Enter the dose per day of the tablet");
                 }
                 else if(fdtime.isEmpty()){
                     firstdosetime.setError("Enter the time fo first dose");
                 }
                 else if(ftdate.isEmpty()){
                     firstdosedate.setError("Enter the date of the first dose");
                 }
                 else{



                     DatePicker datePicker = datePickerDialog.getDatePicker();


                     int year = datePicker.getYear();
                     int month = datePicker.getMonth();
                     int day = datePicker.getDayOfMonth();

                     int hour = Integer.parseInt(firstdosetime.getText().toString().substring(0, 2));
                     int minute = Integer.parseInt(firstdosetime.getText().toString().substring(3));

                     int type = AlarmManager.RTC;

                     GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute);

                     long thenum = Long.parseLong(numofdrug.getText().toString()) - 1;
                     long thedoseperday = Long.parseLong(doseperday.getText().toString());
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
                         SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);

                         final long id = preferences.getLong("id", 1092L);
                         final Signup_pojo pojo = new Signup_pojo();

                         pojo.setId(id);

                         Pres_pojo pres_pojo = new Pres_pojo();
                         pres_pojo.setName(name.getText().toString());
                         pres_pojo.setTreatment(treatment.getText().toString());
                         pres_pojo.setNumber(Integer.parseInt(numofdrug.getText().toString()));
                         pres_pojo.setDose(doseperday.getText().toString());
                         pres_pojo.setFirstdosedate(firstdosedate.getText().toString());
                         pres_pojo.setFirstdosetime(firstdosetime.getText().toString());
                         pres_pojo.setImage(dbimg);
                         pres_pojo.setStatus(0);
                         pres_pojo.setTrig(fintime);
                         pres_pojo.setUserid(id);


                         Signup_crud crud = new Signup_crud(getContext());
                         Pres_pojo result = crud.addPres(pres_pojo, pojo);

                         if(result.getMessage()=="success") {

                             Intent itActivity = new Intent();
                             Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);
                             //Intent itBroadcast = new Intent(getContext(), ReminderReciever.class);


                             itActivity.setAction("com.salim.medhelp.alarm");

                             //PendingIntent piBroadcast = PendingIntent.getBroadcast(getContext(), id, itBroadcast, 0);
                             PendingIntent piActivity = PendingIntent.getActivity(getContext(), (int) fintime, itActivity, 0);
                             PendingIntent piService = PendingIntent.getService(getContext(), (int)fintime, itService,  0);

                             alarmManager.set(type, fintime, piActivity);

                            // alarmManager.set(type, millis, piBroadcast);
                             alarmManager.set(type, fintime, piService);


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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECTPHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri uri = data.getData();
                    assert uri != null;
                    final InputStream inputStream = Objects.requireNonNull(getContext()).getContentResolver().openInputStream(uri);
                    final Bitmap theimage = (Bitmap)BitmapFactory.decodeStream(inputStream);
                    pic.setImageBitmap(theimage);

                    byte[] unvalidated_img = getBitmapAsByteArray(theimage);

                    int size = unvalidated_img.length;
                    if((size/1024)<1050){
                        dbimg = unvalidated_img;
                    }else{
                        dbimg = null;
                        pic.setImageResource(R.drawable.ic_help);

                        Toast.makeText(getContext(),"The selected image is too large",Toast.LENGTH_LONG).show();

                    }



                }
                catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        }

            else if(requestCode==CAPTUREPHOTO){
                if(resultCode==RESULT_OK){
                    onCaptureImageResult(data);

                }
            }

        }


    public void onCaptureImageResult(Intent data){
        thumbnail = (Bitmap)data.getExtras().get("data");

        pic.setImageBitmap(thumbnail);
        dbimg = getBitmapAsByteArray(thumbnail);
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
