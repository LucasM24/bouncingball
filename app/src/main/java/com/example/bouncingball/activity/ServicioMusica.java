package com.example.bouncingball.activity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.bouncingball.R;

public class ServicioMusica  extends Service {

    MediaPlayer mymusic ;
    public void onCreate(){
        super.onCreate();
        mymusic =MediaPlayer.create(this, R.raw.soundtheme);
        mymusic.setLooping(true);

    }
    public int onStartCommand(Intent intent,int flags, int startId){
    mymusic.start();
    return START_STICKY;
    }
    public void onDestroy(){
        super.onDestroy();
        if(mymusic.isPlaying()){
            mymusic.stop();
        }
        mymusic.release();
        mymusic = null ;
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
}
