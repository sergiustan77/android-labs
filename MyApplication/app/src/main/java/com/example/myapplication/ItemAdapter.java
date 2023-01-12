package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ItemAdapter extends BaseAdapter {

    ArrayList<String> dynamicStudents;
    int[] initialImages;
    LayoutInflater inflater;
    View currentView;
    TextView textStudentName;
    ImageView imageStudent;


    public ItemAdapter(Context context, ArrayList<String> dynamicStudents, int[] initialImages){
            this.dynamicStudents = dynamicStudents;
            this.initialImages = initialImages;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return dynamicStudents.size();
    }

    @Override
    public Object getItem(int i) {
        return dynamicStudents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add (String string) {
        dynamicStudents.add(string);

    }

    public void remove (String string) {
        dynamicStudents.remove(string);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        currentView = inflater.inflate(R.layout.list_with_design,null);
        imageStudent = currentView.findViewById(R.id.imageStudent);
        imageStudent.setImageResource(initialImages[getRandomNumberUsingNextInt(0,8)]);
        textStudentName = currentView.findViewById(R.id.textStudentName);
        textStudentName.setText(dynamicStudents.get(i));


        return currentView;
    }
    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}
