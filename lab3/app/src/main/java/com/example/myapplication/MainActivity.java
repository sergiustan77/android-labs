package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.fragments.Fragment1;
import com.example.myapplication.fragments.Fragment2;
import com.example.myapplication.fragments.Fragment3;
import com.example.myapplication.fragments.Fragment4;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new Fragment1());
    }

    public void goToNext(View view) {
        if(getCurrentFragment() instanceof Fragment1){
            replaceFragment(new Fragment2());
        } else if(getCurrentFragment() instanceof Fragment2){
            replaceFragment(new Fragment3());
        } else if(getCurrentFragment() instanceof Fragment3){
            replaceFragment(new Fragment4());
        } if(getCurrentFragment() instanceof Fragment4){
            replaceFragment(new Fragment1());
        }

    }

    public void goToPrevious(View view) {
        if(getCurrentFragment() instanceof Fragment1){
            replaceFragment(new Fragment4());
        } else if(getCurrentFragment() instanceof Fragment2){
            replaceFragment(new Fragment1());
        } else if(getCurrentFragment() instanceof Fragment3){
            replaceFragment(new Fragment2());
        } if(getCurrentFragment() instanceof Fragment4){
            replaceFragment(new Fragment3());
        }

    }

    public void goToDetails(View view) {
        Intent detailsIntent = new Intent(this, Details.class);
        Toast.makeText(getApplicationContext(), "Getting image details..", Toast.LENGTH_SHORT).show();

        String frame = getFragmentName(getCurrentFragment());
        Log.d("FRAME", frame.toString());

        detailsIntent.putExtra("details",frame);

        startActivity(detailsIntent);
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.frameLayout);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private String getFragmentName(Fragment fragment) {
        switch(String.valueOf(fragment).charAt(8) ){
            case '1': return "Details1";

            case '2': return "Details2";

            case '3': return "Details3";

            case '4': return "Details4";

            default: return "";
        }
    }
}