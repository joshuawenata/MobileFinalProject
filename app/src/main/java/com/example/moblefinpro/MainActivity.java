package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

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

    public void gotoExercise(View view) {
        startActivity(new Intent(this, ExcersizeReminder.class));
    }
}