package com.salim.medhelp;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SessionManager mgt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mgt = new SessionManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {



                mgt.checkLogin();

                finish();
            }
        },3000);
    }
}
