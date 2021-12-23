package com.salim.medhelp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Alarm_pojo;
import com.salim.medhelp.pojo.Rem_pojo;
import com.salim.medhelp.pojo.Signup_pojo;
import com.salim.medhelp.pojo.Trigger_pojo;

import java.util.Objects;

public class Rem_demo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        AlertDialog.Builder alarmalert = new AlertDialog.Builder(getContext());

        alarmalert.setTitle("Dosage Reminder");


        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        long user_id = preferences.getLong("id", 1092L);
        Signup_pojo spojo = new Signup_pojo();
        spojo.setId(user_id);

        Signup_crud crud = new Signup_crud(getContext());

        Trigger_pojo tpojo = crud.getCurrentTrigger();

        Alarm_pojo pojo1 = new Alarm_pojo();
        
        pojo1.setId(tpojo.getAlarm_id());
        Log.i("Alarm id ", "is " + tpojo.getAlarm_id());


        Alarm_pojo pojo = crud.getAlarm(pojo1);

        long tid = tpojo.getTrig() + System.currentTimeMillis();

        tpojo.setTrig(tid);

        //Rem_pojo rpojo = crud.getAlarmFromRem(pojo);
        Rem_pojo rem = new Rem_pojo();
        rem.setId(tpojo.getRem_id());

        Rem_pojo rpojo = crud.getRem(rem);

        crud.updateTrig(tpojo,pojo);


        String name = "Didnt get it";
        String treat = "";

        name = rpojo.getTabletname();
        treat = rpojo.getTablettreatment();
        int dose = pojo.getDose();


        alarmalert.setMessage("Time to take  \n" +name+"\n"+ "For "+treat+"\n"+"Dose: "+dose);


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
