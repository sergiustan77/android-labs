package com.example.myapplicationserver;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivityServer extends AppCompatActivity {

    private TextView tvServerAddress;
    private TextView tvServerPort;
    private TextView tvConnections;
    private TextView tvStatus;
    private String serverIP = "10.0.2.16";
    private int serverPort = 1666;
    private Socket currentClient;
    private EditText inputFromServer;
    private LinearLayout messagesList;
    private TextView message;
    private Socket socket;
    private boolean communicationIsRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_server);


        tvStatus = findViewById(R.id.tvStatus);
        message = new TextView(this);
        messagesList = findViewById(R.id.messagesList);
        inputFromServer = findViewById(R.id.inputTextToSend);
     }//on create

    private ServerThread serverThread;


    public void onClickStartServer(View view) {
        serverThread = new ServerThread();
        serverThread.startServer();
    }

    public void onClickStopServer(View view) {

        serverThread.stopServer();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSendMessage(View view) {
        try {
            if (null != currentClient) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter out = null;
                        try {
                            out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(currentClient.getOutputStream())),
                                    true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        out.println(inputFromServer.getText().toString());

                        runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               messagesList.addView(textView("Server: " + inputFromServer.getText().toString()) );
                           }
                       });
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ServerThread extends Thread implements Runnable{

        private boolean serverIsRunning;
        private ServerSocket serverSocket;
        private int numberOfClients = 0;

        BufferedReader input;

        public void startServer() {
            serverIsRunning = true;
            communicationIsRunning = true;
            start();
        }

        public void stopServer() {
            serverIsRunning = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(serverSocket != null) {
                        try {

                            serverSocket.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvStatus.setTextColor(Color.RED);
                                    tvStatus.setText("Server Stopped");
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }


        @Override
        public void run() {

            try {
                serverSocket = new ServerSocket(serverPort);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvStatus.setText("Waiting for clients...");
                    }
                });

                while(serverIsRunning) {
                    socket = serverSocket.accept();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvStatus.setText("Connected to: " + socket.getInetAddress() + ":" + socket.getLocalPort());
                        }
                    });
                    CommunicationThread cmmthread = new CommunicationThread(socket);
                    new Thread(cmmthread).start();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }



        }





    }//server thread

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            currentClient = clientSocket;
            try {
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvStatus.setTextColor(Color.RED);
                        tvStatus.setText("Error - Connection to client failed!");
                    }
                });

            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvStatus.setTextColor(Color.GREEN);
                    tvStatus.setText("Connected to client!");
                }
            });
        }

        public void run() {
            communicationIsRunning = true;
            while (communicationIsRunning) {
                try {
                    String read = input.readLine();
                    if (null == read || "Disconnect".contentEquals(read)) {
                        communicationIsRunning = false;
                        read = "Client Disconnected";
                        String finalRead1 = read;
                        runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               tvStatus.setTextColor(Color.RED);
                               tvStatus.setText(finalRead1);
                           }
                       });
                        break;
                    }
                    String finalRead = read;
                    runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           messagesList.addView(textView("Client: " + finalRead));
                       }
                   });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

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