package com.example.moblefinpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.moblefinpro.receiver.notificationReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class eatreminder extends AppCompatActivity {
    private int breakfast, lunch, dinner; // Switch state
    // Time array to save times, passed to user's time in database later
    private int breakfastTime[] = {6, 0}, lunchTime[] = {12, 0}, dinnerTime[] = {18, 00};
    // Channel id for notification
    private final String breakfast_id = "Breakfast", lunch_id = "Lunch", dinner_id = "Dinner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eatreminder);

        // Set notification channel (required for android 8.0 (API 26) or higher)
        setNotificationChannel(breakfast_id);
        setNotificationChannel(lunch_id);
        setNotificationChannel(dinner_id);

        // Switch declaration and view finding
        Switch breakfast_switch = findViewById(R.id.breakfast_switch);
        Switch lunch_switch = findViewById(R.id.lunch_switch);
        Switch dinner_switch = findViewById(R.id.dinner_switch);

        // TextView for displaying time picked
        TextView breakfast_time = findViewById(R.id.breakfast_time);
        TextView lunch_time = findViewById(R.id.lunch_time);
        TextView dinner_time = findViewById(R.id.dinner_time);

        // Button for setting up time
        Button breakfastButton = findViewById(R.id.breakfastbutton);
        Button lunchButton = findViewById(R.id.lunchbutton);
        Button dinnerButton = findViewById(R.id.dinnerbutton);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());

        breakfast_time.setText(breakfastTime[0] + ":" + checkDigit(breakfastTime[1]));
        lunch_time.setText(lunchTime[0] + ":" + checkDigit(lunchTime[1]));
        dinner_time.setText(dinnerTime[0] + ":" + checkDigit(dinnerTime[1]));

        myRef.child("breakfast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    myRef.child("breakfast").setValue(breakfast_time.getText().toString());
                } else {
                    breakfast_time.setText(snapshot.getValue().toString());
                    String[] time = breakfast_time.getText().toString().split(":");
                    breakfastTime[0] = Integer.parseInt(time[0]);
                    breakfastTime[1] = Integer.parseInt(time[1]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("lunch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    myRef.child("lunch").setValue(lunch_time.getText().toString());
                } else {
                    lunch_time.setText(snapshot.getValue().toString());
                    String[] time = lunch_time.getText().toString().split(":");
                    lunchTime[0] = Integer.parseInt(time[0]);
                    lunchTime[1] = Integer.parseInt(time[1]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("dinner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    myRef.child("dinner").setValue(dinner_time.getText().toString());
                } else {
                    dinner_time.setText(snapshot.getValue().toString());
                    String[] time = dinner_time.getText().toString().split(":");
                    dinnerTime[0] = Integer.parseInt(time[0]);
                    dinnerTime[1] = Integer.parseInt(time[1]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Breakfast time button
        breakfast_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) breakfast = 1;
                else breakfast = 0;

                setNotification(breakfast_id, "breakfast", breakfast, breakfastTime);
                Log.d("Breakfast check", "onCheckedChanged: " + breakfast);
            }
        });

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(breakfastTime, breakfast_time, "breakfast");
                breakfast_switch.setChecked(false);
                breakfast = 0;
            }

        });

        lunch_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) lunch = 1;
                else lunch = 0;

                setNotification(lunch_id, "lunch", lunch, lunchTime);

                Log.d("Lunch check", "onCheckedChanged: " + lunch);
            }
        });

        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(lunchTime, lunch_time, "lunch");
                lunch_switch.setChecked(false);
                lunch = 0;
            }
        });

        dinner_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) dinner = 1;
                else dinner = 0;

                setNotification(dinner_id, "dinner", dinner, dinnerTime);

                Log.d("Dinner check", "onCheckedChanged: " + dinner);
            }
        });

        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setTime(dinnerTime, dinner_time, "dinner");
               dinner_switch.setChecked(false);
               dinner = 0;
            }
        });

    }

    // Display time picker and set it to TextView
    public void setTime(int time[], TextView timeSet, String path) {
        final Calendar c = Calendar.getInstance();

        // Getting hour and minute.
        int hour = time[0];
        int minute = time[1];

        // on below line we are initializing our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(eatreminder.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // on below line we are setting selected time
                        // in our text view.
                        timeSet.setText(hourOfDay + ":" + checkDigit(minute));
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());
                        myRef.child(path).setValue(timeSet.getText().toString());
                        time[0] = hourOfDay;
                        time[1] = minute;

                        Log.d("", "onTimeSet: " + time[0] + ":" + checkDigit(time[1]));
                    }
                }, hour, minute, false);
        // at last we are calling show to
        // display our time picker dialog.
        timePickerDialog.show();
    }

    // Used for minutes digit checking
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    // Set notification
    @SuppressLint("MissingPermission")
    public void setNotification(String notificationType, String message, int switchStatus, int time[]) {
        Intent intent = new Intent(eatreminder.this, notificationReceiver.class);

        intent.putExtra("notifType", notificationType);
        intent.putExtra("message", message);
        intent.putExtra("activity", "eat");

        PendingIntent alarmIntent = PendingIntent.getBroadcast(eatreminder.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch(switchStatus) {
            case 1:
                int hour = time[0], minute = time[1];

                // Create time
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);

                if (startTime.getTimeInMillis() <= System.currentTimeMillis()) {
                    // Add a day to the alarm time
                    startTime.add(Calendar.DAY_OF_MONTH, 1);
                }

                long alarmStartTime = startTime.getTimeInMillis();

                // Set alarm
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

                Toast.makeText(this, notificationType + " reminder has been set.", Toast.LENGTH_SHORT).show();
                break;

            case 0:
                alarm.cancel(alarmIntent);

                Toast.makeText(this, notificationType + " reminder has been cancelled.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setNotificationChannel(String type) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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