package com.example.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class BoundService extends Service {

    public BoundService() {

    }
    volatile boolean serviceStarted = false;
    private static final String TAG = "MyService | ";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return binder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "in service -> onCreate: ");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "in service -> onStartCommand: ");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "in service -> onDestroy: ");
        serviceStarted = true;
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        Log.i(TAG, "in service -> onTaskRemoved: ");
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public MyBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        BoundService getBoundService() {
            return BoundService.this;
        }
    }



    public void playSound(int duration, float volume) {


        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {

            mediaPlayer.setDataSource(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();

        }

        mediaPlayer.start();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                        mediaPlayer.stop();
            }
        }, 1000L * duration);
    }






    public String getSysTime() {
        java.text.SimpleDateFormat systemTime = new SimpleDateFormat("hh:mm:ss  dd/mm/yyyy", Locale.ENGLISH);
        return systemTime.format(new Date());
    }
}