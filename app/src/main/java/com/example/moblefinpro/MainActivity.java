package com.example.moblefinpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.moblefinpro.object.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    TextView main_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        main_title = findViewById(R.id.main_title);
        DatabaseReference myRef = database.getReference().child("Users").child(user.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Handle the retrieved data here
                User thisuser = dataSnapshot.getValue(User.class);
                if(thisuser != null) {
                    main_title.setText("Hello, " + thisuser.getUsername() + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur
            }
        });

    }

    public void gotoBmi(View view) {
        startActivity(new Intent(this, bmicalculator.class));
    }

    public void gotoDailyCalory(View view) {
        startActivity(new Intent(this, malefemalepicker.class));
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
        startActivity(new Intent(this, exerciseReminder.class));
    }

    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, landing_page.class));
        finishAffinity();
    }
}