package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void gotoBmi(View view) {
        startActivity(new Intent(this, bmicalculator.class));
    }

    public void gotoDailyCalory(View view) {
        startActivity(new Intent(this, caloryneeds.class));
    }

    public void gotoEat(View view) {
        startActivity(new Intent(this, eatreminder.class));
    }

    public void gotoWater(View view) {
        startActivity(new Intent(this, WaterReminder.class));
    }

    public void gotoSleep(View view) {
        startActivity(new Intent(this, SleepReminder.class));
    }

    public void gotoExcercise(View view) {
        startActivity(new Intent(this, ExcersizeReminder.class));
    }
}