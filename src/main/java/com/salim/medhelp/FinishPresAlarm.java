package com.salim.medhelp;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class FinishPresAlarm extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FinishPresAlarmdemo demo = new FinishPresAlarmdemo();
        demo.show(getSupportFragmentManager(), "DemoFragment");

    }
}