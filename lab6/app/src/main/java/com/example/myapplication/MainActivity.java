package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    Button btnCount;
    TextView tvStatus;
    EditText timePeriod;
    EditText soundVolume;
    EditText soundDuration;
    BoundService boundService;
    boolean isConnected = false;
    ServiceConnection serviceConnection;
    int seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePeriod = findViewById(R.id.textPeriod);
        soundVolume = findViewById(R.id.textSoundVolume);
        soundDuration = findViewById(R.id.textSoundDuration);
        btnCount = findViewById(R.id.buttonPlaySound);
        tvStatus = findViewById(R.id.tvServiceStatus);

        boundService = new BoundService();
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.i("SERVICE CONNECT", "onServiceConnected: ");
                BoundService.MyBinder binder = (BoundService.MyBinder) iBinder;
                boundService = binder.getBoundService();
                isConnected = true;

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i("SERVICE DISSCONNECT", "onServiceDisconnected: ");
                isConnected = false;
            }
        };
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);



    }

//    public void onClickGetTime(View view) {
//        tvStatus.setText(boundService.getSysTime());
//    }

    public void onPlaySound(View view) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boundService.playSound(Integer.parseInt(soundDuration.getText().toString()), Float.parseFloat(soundVolume.getText().toString()));

            }
        }, 1000L * Integer.parseInt(timePeriod.getText().toString()));


    }
//    public void onButtonCount(View view) {
//        count++;
//        btnCount.setText("Count at: " + count);
//    }
//
//    public void onButtonStartService(View view) {
//        tvStatus.setText("Service STARTED");
//        Intent intentService = new Intent(this, BoundService.class);
//        startService(intentService);
//    }
//
//    public void onButtonStopService(View view) {
//        tvStatus.setText("Service STOPPED");
//        Intent intentService = new Intent(this, BoundService.class);
//        stopService(intentService);
//    }
}