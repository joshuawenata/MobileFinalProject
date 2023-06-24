package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExercise extends AppCompatActivity {

    EditText name, repetition;
    TimePicker time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        initiallize();
    }

    private void initiallize() {
        name = findViewById(R.id.add_excersize_set_text);
        repetition = findViewById(R.id.add_excersize_repetition);
        time = findViewById(R.id.add_excersize_simpleTimePicker);
    }

    public void addExcercise(View view) {
        boolean flag = true;

        if(name.getText().toString().isEmpty()){
            Toast.makeText(AddExercise.this, "Exercise name need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if(repetition.getText().toString().isEmpty()){
            Toast.makeText(AddExercise.this, "Repetition need to be filled!", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if(flag){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid()).child("exercise").push();
            myRef.child("name").setValue(name.getText().toString());
            myRef.child("repetition").setValue(repetition.getText().toString());
            myRef.child("time").setValue(time.getHour()+":"+time.getMinute());

            startActivity(new Intent(AddExercise.this, exerciseReminder.class));
            finish();
        }

    }
}