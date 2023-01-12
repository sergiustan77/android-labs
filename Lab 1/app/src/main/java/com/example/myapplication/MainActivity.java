package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendInfos(View view) {
        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView textFirstName = findViewById(R.id.textFirstName);
        TextView textLastName = findViewById(R.id.textLastName);
        TextView textEmail = findViewById(R.id.textEmail);

        EditText inputFirstName = findViewById(R.id.inputFirstName);
        EditText inputLastName = findViewById(R.id.inputLastName);
        EditText inputEmail = findViewById(R.id.inputEmail);

        welcomeText.setText("Hello " + inputFirstName.getText().toString() + "!");
        textFirstName.setText("First Name: " + inputFirstName.getText().toString());
        textLastName.setText("Last Name: " + inputLastName.getText().toString());
        textEmail.setText("Email: " + inputEmail.getText().toString());

    }
}