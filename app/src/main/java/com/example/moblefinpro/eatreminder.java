package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.Switch;

public class eatreminder extends AppCompatActivity {
    private int breakfast, lunch, dinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eatreminder);

        Switch breakfast_switch = findViewById(R.id.breakfast_switch);
        Switch lunch_switch = findViewById(R.id.lunch_switch);
        Switch dinner_switch = findViewById(R.id.dinner_switch);

        breakfast_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) breakfast = 1;
                else breakfast = 0;
            }
        });

    }
}