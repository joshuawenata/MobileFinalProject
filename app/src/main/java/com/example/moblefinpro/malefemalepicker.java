package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class malefemalepicker extends AppCompatActivity {
    private String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malefemalepicker);

        RelativeLayout male = findViewById(R.id.malefemalepicker_maleview_layout);
        RelativeLayout female = findViewById(R.id.malefemalepicker_femaleview_layout);
        TextView textConfirm = findViewById(R.id.malefemalepicker_textselection);
        Button confirm = findViewById(R.id.malefemalepicker_button);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "male";
                male.setBackground(getDrawable(R.drawable.malepicker));
                female.setBackgroundColor(Color.parseColor("#8C8181"));
                textConfirm.setText("Male selected");
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "female";
                female.setBackground(getDrawable(R.drawable.femalepicker));
                male.setBackgroundColor(Color.parseColor("#FFFFFF"));
                textConfirm.setText("Female selected");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(malefemalepicker.this, caloryneeds.class);
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });
    }
}