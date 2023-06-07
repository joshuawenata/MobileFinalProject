package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WaterReminder extends AppCompatActivity {

    AnimationDrawable waveAnimated;
    private ImageView imageView;
    private Button button;
    private int buttonClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder);
        imageView = findViewById(R.id.water_reminder_changeWaterImage);
        button = findViewById(R.id.water_reminder_changeWaterButton);
        imageView.setImageResource(R.drawable.glass1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickCount++;

                if (buttonClickCount % 4 == 1) {
                    imageView.setImageResource(R.drawable.glass2);
                } else if (buttonClickCount % 4 == 2) {
                    imageView.setImageResource(R.drawable.glass3);
                } else {
                    imageView.setImageResource(R.drawable.glass4);
                }
            }
        });
    }
}