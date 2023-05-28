package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ExcersizeReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersize_reminder);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_ex);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Pushup","30 Reps","10:00"));
        items.add(new Item("SitUp","30 Reps","15:00"));
        items.add(new Item("Planks","2 Reps","15:00"));
        items.add(new Item("Rest","30 Reps","60:00"));
        items.add(new Item("Jumping Jacks","30 Reps","20:00"));
        items.add(new Item("Rest","30 Reps","40:00"));
        items.add(new Item("Combination 3","30 Reps","60:00"));




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new excersizeAdapter(getApplicationContext(),items));

    }
}