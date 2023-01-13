package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    private EditText inputServerAddress, inputServerPort;
    private ArrayAdapter<String> myAdapter;
    private String serverName;
    private int serverPort;
    private LinearLayout messageList;
    private TextView message;
    private TextView titleText;
    private boolean startedClientServer = false;
    private Socket socket;
    private BufferedReader bufferReader_input;
    private EditText inputMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       message = new TextView(this);
        inputServerAddress = findViewById(R.id.inputServerAddress);
        inputServerPort = findViewById(R.id.inputServerPort);
        messageList = findViewById(R.id.messageList);
        titleText = findViewById(R.id.tvTitlePage);
        inputMessage = findViewById(R.id.inputMessage);

    }


    public void onSendMessage(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != socket) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                        out.println(inputMessage.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageList.addView(textView("Client: " + inputMessage.getText().toString()));
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    void sendMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != socket) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                        out.println(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void connectToServer(View view) {
        startedClientServer = true;
        serverName = inputServerAddress.getText().toString();
        serverPort = Integer.parseInt(inputServerPort.getText().toString());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(serverName, serverPort);
                    while (startedClientServer) {
                        bufferReader_input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String dataFromServer = bufferReader_input.readLine();
                        if (null == dataFromServer || "Disconnect".contentEquals(dataFromServer)) {
                            startedClientServer = false;
                            dataFromServer = "Server Disconnected.";
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    titleText.setText("Dissconected");
                                }
                            });
                            break;
                        }
                        String finalDataFromServer = dataFromServer;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                messageList.addView(textView("Server: " + finalDataFromServer));
                            }
                        });
                    }

                    } catch(IOException e){
                        e.printStackTrace();
                    }

                }
            }
        ).start();

    }



    public TextView textView(String message) {
        if (null == message || message.trim().isEmpty()) {
            message = "<Empty Message>";
        }
        TextView tv = new TextView(this);

        tv.setText(message);
        tv.setTextSize(16);
        tv.setPadding(0, 5, 0, 0);
        return tv;
    }
}