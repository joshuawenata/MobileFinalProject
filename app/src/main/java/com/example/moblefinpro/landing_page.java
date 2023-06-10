package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class landing_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        initialize();
    }

    private void initialize() {
        ImageButton sign_in = findViewById(R.id.landing_page_sign_in);
        ImageButton sign_up = findViewById(R.id.landing_page_sign_up);
        ImageButton google = findViewById(R.id.landing_page_google);
        sign_in.bringToFront();
        sign_up.bringToFront();
        google.bringToFront();
    }
}