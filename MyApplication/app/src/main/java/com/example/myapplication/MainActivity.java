package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView lvTitle;
    ListView listStudents;
    ArrayList<String> dynamicStudentsList;
    int[] initalImages;
    // ArrayAdapter<String> listAdapter;
    EditText inputStudentName;
    ItemAdapter customItemAdapter;
    int image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTitle = findViewById(R.id.listTitle);
        listStudents = findViewById(R.id.listStudents);
        dynamicStudentsList = new ArrayList<String>();
        initalImages = new int[] {R.drawable.student1,R.drawable.student2,R.drawable.student3,R.drawable.student4,R.drawable.student5,R.drawable.student6,R.drawable.student7,R.drawable.student8};
         customItemAdapter = new ItemAdapter(this, dynamicStudentsList, initalImages);
        listStudents.setAdapter(customItemAdapter);
        inputStudentName = findViewById(R.id.inputStudentName);
    }

    public  void removeStudent(View view) {
        if (dynamicStudentsList.size() > 0) {
            if (!inputStudentName.getText().toString().isEmpty()) {
                customItemAdapter.remove(inputStudentName.getText().toString());
                Toast.makeText(MainActivity.this, "Student removed from list", Toast.LENGTH_SHORT).show();
                customItemAdapter.notifyDataSetChanged();

            }
        } else {
            Toast.makeText(MainActivity.this, "No students to remove", Toast.LENGTH_SHORT).show();
        }

    }

    public  void addStudent(View view) {

        String studentName = inputStudentName.getText().toString();

        if(studentName.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else {

            customItemAdapter.add(studentName);
            customItemAdapter.notifyDataSetChanged();

        }

    }


}