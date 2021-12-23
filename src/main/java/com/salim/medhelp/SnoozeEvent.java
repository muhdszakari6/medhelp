package com.salim.medhelp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
public class SnoozeEvent extends Service {
    MediaPlayer mediaPlayer;

    /*  public class LocalBinder extends Binder{
          com.salim.medhelp.SnoozeEvent getSercive(){
              return com.salim.medhelp.SnoozeEvent.this;
          }
      }*/
    // private final IBinder mbinder = new LocalBinder();
    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        // return null;
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){

        return Service.START_NOT_STICKY;
    }
    @Override
    public void onCreate(){

        mediaPlayer = MediaPlayer.create(this,R.raw.alarm1);
        mediaPlayer.start();
        mediaPlayer.setScreenOnWhilePlaying(true);


    }
    @Override
    public void onDestroy(){
        mediaPlayer.stop();
    }
}
