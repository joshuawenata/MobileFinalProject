package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ExcersizeReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersize_reminder);
    }
    RecyclerView recyclerView = findViewById(R.id.recyclerView_ex);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    List<String> data = new ArrayList<>();
    data.add("Item 1");
    data.add("Item 2");
    data.add("Item 3");
    recyclerAdapter adapter = new recyclerAdapter(data);
    recyclerView.setAdapter(adapter);
}