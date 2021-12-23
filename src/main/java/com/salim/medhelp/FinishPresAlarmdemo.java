package com.salim.medhelp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Pres_pojo;
import com.salim.medhelp.pojo.Signup_pojo;

import java.util.Objects;

public class FinishPresAlarmdemo extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        AlertDialog.Builder alarmalert = new AlertDialog.Builder(getContext());

        alarmalert.setTitle("Finish Prescription");

        Signup_crud crud = new Signup_crud(getContext());

        long tid = System.currentTimeMillis();
        Pres_pojo pojo = new Pres_pojo();

        pojo.setTrig(tid);


        Pres_pojo pres_pojo = crud.getFinishPres(pojo);

        String tabletname = "Didnt get it";
        String tabtreatment = "";


            tabletname = pres_pojo.getName();
            tabtreatment = pres_pojo.getTreatment();


        alarmalert.setMessage(tabletname +"\n"+ "For "+tabtreatment+"\nFinished Today" + "");


        Signup_crud scrud = new Signup_crud(getContext());
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        final long id = preferences.getLong("id",1092L);
        Signup_pojo signupPojo = new Signup_pojo();
        signupPojo.setId(id);
        pres_pojo.setStatus(1);
        pres_pojo.setTrig(0);
            scrud.updatepres(pres_pojo,signupPojo);


        alarmalert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
                Intent it = new Intent(getContext(),com.salim.medhelp.SnoozeEvent.class);
                getActivity().stopService(it);
            }
        });

        alarmalert.setIcon(R.drawable.ic_timer_black_24dp);

        return alarmalert.create();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        getActivity().finish();
    }



}
