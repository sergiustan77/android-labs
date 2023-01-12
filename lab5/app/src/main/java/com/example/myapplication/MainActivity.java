package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button buttonCount;
    private int clickCount;
    private volatile boolean stopWorker = false;
    private static final String TAG = "MainActivity | Thread -";
    //private TextView textStatus;
    private TextView textWorkerGuess;
    private TextView textAnswer;
    private TextView textTitle;
    private EditText minValue;
    private EditText maxValue;
    private EditText inputUserGuess;
    private int randomNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minValue = findViewById(R.id.minNumber);
        maxValue = findViewById(R.id.maxNumber);
        textWorkerGuess = findViewById(R.id.textWorkerGuess);
        inputUserGuess = findViewById(R.id.inputUserGuess);
        textAnswer = findViewById(R.id.textAnswer);
        textTitle = findViewById(R.id.textTitle);

    }


    public void startGame(View view) {
        textTitle.setText("Guess the number!");
        textAnswer.setText("");
        textWorkerGuess.setTextColor(Color.DKGRAY);
        stopWorker=false;

        randomNumber = getRandomNumber(Integer.parseInt(minValue.getText().toString()), Integer.parseInt(maxValue.getText().toString()));
        startWorkerThread(60);
    }

    public void startWorkerThread(int seconds) {

        stopWorker=false;
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            int randomWorkerNumber;
            @Override
            public void run() {
                for(int i=0; i<seconds; i++) {
                    randomWorkerNumber = getRandomNumber(Integer.parseInt(minValue.getText().toString()), Integer.parseInt(maxValue.getText().toString()));
                    if(stopWorker) {
                        return;
                    }

                    if(randomWorkerNumber == randomNumber) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textWorkerGuess.setTextColor(Color.RED);
                                textWorkerGuess.setText("You Lost!");
                                textAnswer.setText("Random number: " + randomNumber);
                            }
                        });
                        stopWorker=true;
                        return;
                    }

                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int time = seconds - finalI;
                            textTitle.setText("Time left: " + time);
                            textWorkerGuess.setText("Worker guess: " + randomWorkerNumber);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }


    public void userGuess(View view) {


        int userGuess = Integer.parseInt(inputUserGuess.getText().toString());
        if(inputUserGuess.getText().toString() == null) {
            Toast.makeText(this, "Guess", Toast.LENGTH_SHORT).show();
        } else
        if( userGuess == randomNumber) {
            stopWorker = true;
            textWorkerGuess.setTextColor(Color.GREEN);
            textWorkerGuess.setText("You Won!");
            textAnswer.setText("Random number: " + randomNumber);
        }

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}



