package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;
    private String URL = "https://www.floatrates.com/daily/ron.json";
    private EditText inputRON;
    private TextView displayCurrency;
    private String recData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        inputRON = findViewById(R.id.inputRON);
        displayCurrency = findViewById(R.id.textNewCurrency);

    }

    public void onClickUSD (View view) {

        trimData("usd");
    }
    public void onClickEUR (View view) {

        trimData("eur");
    }
    public void onClickGPB (View view) {

        trimData("gbp");
    }
    public void onClickMLD (View view) {

        trimData("mdl");
    }


    public void getData() {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(URL).build();
        Call call = client.newCall(req);


        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                    recData = response.body().string();

            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void trimData(String currency) {
        if(displayCurrency.getText().toString() != null) {

            try {
                Log.i("DATA", "trimData: " + recData);
                JSONObject jsonObject = new JSONObject(recData);
                Double currencyRate = Double.parseDouble(jsonObject.getJSONObject(currency).getString("rate"));

                displayCurrency.setText(String.valueOf(currencyRate * Double.parseDouble(inputRON.getText().toString())) + " " + currency.toUpperCase(Locale.ROOT));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "Get Data first!", Toast.LENGTH_SHORT).show();
        }
    }

}