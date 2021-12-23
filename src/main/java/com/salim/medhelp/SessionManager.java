package com.salim.medhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;
    int mode = 0;
    String prefname = "loginpref";
    String islogin = "islogin";


    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(prefname,mode);
        editor = pref.edit();

    }

    public void createLoginSession(/*String fname,String lname,String email,String password,String state,String name,long id*/){
        // Storing login value as TRUE
        editor.putBoolean(islogin, true);

        // Storing name in pref


        // Storing email in pref

        // commit changes
        editor.commit();
    }
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities

            // Staring Login Activity
            _context.startActivity(i);
        }
        else{
            Intent i = new Intent(_context, MainActivity.class);
            _context.startActivity(i);


        }

    }
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(islogin, false);
    }

}

