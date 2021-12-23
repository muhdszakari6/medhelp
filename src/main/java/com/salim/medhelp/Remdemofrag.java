package com.salim.medhelp;

import android.os.Bundle;
import  androidx.fragment.app.FragmentActivity;

public class Remdemofrag extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Rem_demo demo = new Rem_demo();
        demo.show(getSupportFragmentManager(), "DemoFragment");

    }
}
