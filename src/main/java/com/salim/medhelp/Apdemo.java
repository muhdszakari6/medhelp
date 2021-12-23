package com.salim.medhelp;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Ap_pojo;

import java.util.GregorianCalendar;
import java.util.Objects;

public class Apdemo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        AlertDialog.Builder alarmalert = new AlertDialog.Builder(getContext());

        alarmalert.setTitle("Doctors Appointment");


        Signup_crud crud = new Signup_crud(getContext());

        //long tid = System.currentTimeMillis();
        GregorianCalendar calendar = new GregorianCalendar();
        long tid = calendar.getTimeInMillis();


        Ap_pojo pojo = new Ap_pojo();
        pojo.setTrig(tid);

        final Ap_pojo ap_pojo = crud.getCurrentAP(pojo);

        Toast.makeText(getContext(),ap_pojo.getMessage(),Toast.LENGTH_LONG).show();
        String location = "Didnt get it";
        String complain = "";


        location = ap_pojo.getLocation();
        complain = ap_pojo.getComplain();


        alarmalert.setMessage(location +"\n"+ "For "+complain+"\n");


        Signup_crud scrud = new Signup_crud(getContext());

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        final long id = preferences.getLong("id",1092L);


        scrud.delete_ap(ap_pojo);


        alarmalert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
                   Intent it = new Intent(getContext(),com.salim.medhelp.SnoozeEvent.class);
                  getActivity().stopService(it);
            }
        });
//        alarmalert.setNegativeButton("Open in Maps", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                double lon = ap_pojo.getLon();
//                double lat = ap_pojo.getLat();
//                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat +"," + lon);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                try
//                {
//                    startActivity(mapIntent);
//                }
//                catch(ActivityNotFoundException ex)
//                {
//                    try
//                    {
//                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW,  Uri.parse("google.navigation:q=" + lat +"," + lon));
//                        startActivity(unrestrictedIntent);
//                    }
//                    catch(ActivityNotFoundException innerEx)
//                    {
//                        Toast.makeText(getContext(), "No map application available", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
        alarmalert.setIcon(R.drawable.ic_timer_black_24dp);
        return alarmalert.create();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        getActivity().finish();
    }



}
