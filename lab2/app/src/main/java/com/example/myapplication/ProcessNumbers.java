package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProcessNumbers extends AppCompatActivity {

    int firstNumber;
    int secondNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_numbers);

        TextView numberOne = findViewById(R.id.numberOne);
        TextView numberTwo = findViewById(R.id.numberTwo);
        firstNumber = Integer.parseInt(getIntent().getStringExtra("firstNumber"));
        secondNumber = Integer.parseInt(getIntent().getStringExtra("secondNumber"));
        numberOne.setText("First number: " + firstNumber);
        numberTwo.setText("Second number: " + secondNumber);

    }

    public void goBack(View view) {
        Intent intentGoBack = new Intent(this, MainActivity.class);
        Toast.makeText(getApplicationContext(), "Go back", Toast.LENGTH_SHORT).show();
        startActivity(intentGoBack);
    }


    public void addNumbers(View view) {

        Intent intentGiveResult = new Intent(this, MainActivity.class);


        intentGiveResult.putExtra("result", firstNumber + secondNumber );

        Toast.makeText(getApplicationContext(), "Added numbers", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, intentGiveResult);
        finish();
    }

    public void subtractNumbers(View view) {
        Intent intentGiveResult = new Intent(this, MainActivity.class);

        intentGiveResult.putExtra("result", firstNumber - secondNumber );
        Toast.makeText(getApplicationContext(), "Subtracted numbers", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, intentGiveResult);
        finish();


    }
}