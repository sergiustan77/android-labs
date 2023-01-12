package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.fragments.Details1;
import com.example.myapplication.fragments.Details2;
import com.example.myapplication.fragments.Details3;
import com.example.myapplication.fragments.Details4;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String fragment = getIntent().getStringExtra("details");

        switch(fragment.toString())  {
            case "Details1":
                replaceFragment(new Details1());
                break;
            case "Details2":
                replaceFragment(new Details2());
                break;
            case "Details3":
                replaceFragment(new Details3());
                break;
            case "Details4":
                replaceFragment(new Details4());
                break;

        }

    }

    public void goBack(View view) {
        Intent mainPageIntent = new Intent(this, MainActivity.class);
        Toast.makeText(getApplicationContext(), "Going back...", Toast.LENGTH_SHORT).show();
        startActivity(mainPageIntent);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameDetails, fragment);
        fragmentTransaction.commit();
    }
}