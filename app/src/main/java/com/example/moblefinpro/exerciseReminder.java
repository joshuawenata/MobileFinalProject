package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class exerciseReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);
        Button btnMove = findViewById(R.id.excersize_reminder_intentbtn);
        RecyclerView recyclerView = findViewById(R.id.excersize_reminder_recyclerView);

        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            startActivity(new Intent(exerciseReminder.this, AddExercise.class));
            }
        });

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Pushup","30 Reps","10:00"));
        items.add(new Item("SitUp","30 Reps","15:00"));
        items.add(new Item("Planks","2 Reps","15:00"));
        items.add(new Item("Rest","30 Reps","60:00"));
        items.add(new Item("Jumping Jacks","30 Reps","20:00"));
        items.add(new Item("Rest","30 Reps","40:00"));
        items.add(new Item("Combination 3","30 Reps","60:00"));




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new exerciseAdapter(getApplicationContext(),items));

    }
}