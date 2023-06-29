package com.example.moblefinpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moblefinpro.object.Exercise;
import com.example.moblefinpro.receiver.notificationReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class exerciseReminder extends AppCompatActivity {
    Context context = this;
    private int arraySize;
    ArrayList<Exercise> newList = new ArrayList<>();
    ArrayList<Integer> switchStatus = new ArrayList<>();
    int[] time = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);

        Button btnMove = findViewById(R.id.excersize_reminder_intentbtn);
        RecyclerView recyclerView = findViewById(R.id.excersize_reminder_recyclerView);
        setNotificationChannel("Exercise");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference().child("Users").
                child(firebaseUser.getUid()).child("exercise");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    newList.add(postSnapshot.getValue(Exercise.class));
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
                exerciseAdapter newAdapter = new exerciseAdapter(context, newList);
                recyclerView.setAdapter(newAdapter);
                newAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(exerciseReminder.this, AddExercise.class));
                finish();
            }
        });
    }

    public void setNotification(String notificationType, String message, int switchStatus, int time[]) {
        Intent intent = new Intent(exerciseReminder.this, notificationReceiver.class);

        intent.putExtra("notifType", notificationType);
        intent.putExtra("message", message);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(exerciseReminder.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch(switchStatus){
            case 1:
                int hour = time[0], minute = time[1];

                // Create time
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                // Set alarm
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

                Toast.makeText(this, notificationType + " reminder has been set.", Toast.LENGTH_SHORT).show();
                break;

            case 0:
                alarm.cancel(alarmIntent);
                break;
        }
    }

    private void setNotificationChannel(String type){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = type + "Channel";
            String description = "Channel for " + type + " reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(type, name, importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}