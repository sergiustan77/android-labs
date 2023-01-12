package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    TextView textResult = findViewById(R.id.textResult);
                    if(result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if(data != null) {
                            textResult.setText("Result: " + data.getIntExtra("result",0));
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "Activity 1", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void processNumbers(View view) {

        EditText inputFirstNumber = findViewById(R.id.inputFirstNumber);
        EditText inputSecondNumber = findViewById(R.id.inputSecondNumber);

        Intent intentProcessNumbers = new Intent(this, ProcessNumbers.class);

        try {
            intentProcessNumbers.putExtra("firstNumber", inputFirstNumber.getText().toString());
            intentProcessNumbers.putExtra("secondNumber", inputSecondNumber.getText().toString());
            activityResultLauncher.launch(intentProcessNumbers);
            Toast.makeText(getApplicationContext(), "Processing numbers...", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException numberFormatException) {
            Toast.makeText(getApplicationContext(), "Please enter both numbers!", Toast.LENGTH_SHORT).show();
        }

    }
}