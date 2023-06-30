package com.example.moblefinpro;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moblefinpro.receiver.BedTimeNotificationReceiver;
import com.example.moblefinpro.receiver.WakeUpNotificationReceiver;
import com.example.moblefinpro.receiver.notificationReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;

public class SleepReminder extends AppCompatActivity {
    TimePicker bedtime, wakeup;
    TextView bedtimetext, wakeuptext;
    Switch bedtimeSwitch, wakeupSwitch;
    private int bedtimes, wakeups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_reminder);

        Context context = this;
        int bedtimeTime[] = {00, 00}, wakeupTime[] = {9, 00};
        String bedtime_id = "Sleep";
        String wakeup_id = "Wake Up";
        setNotificationChannel(bedtime_id);
        setNotificationChannel(wakeup_id);
        bedtime = findViewById(R.id.sleep_reminder_bedtime);
        wakeup = findViewById(R.id.sleep_reminder_wakeup);
        bedtimeSwitch = findViewById(R.id.sleep_reminder_switchbedtime);
        wakeupSwitch = findViewById(R.id.sleep_reminder_switchwakeup);
        bedtimetext = findViewById(R.id.sleep_reminder_bedtime_text);
        wakeuptext = findViewById(R.id.sleep_reminder_wakeup_text);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());

        myRef.child("bedtime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()==null) {
                    bedtimetext.setText("00:00");
                } else {
                    bedtimetext.setText(snapshot.getValue().toString());
                    String[] time1 = bedtimetext.getText().toString().split(":");
                    bedtimeTime[0] = Integer.parseInt(time1[0]);
                    bedtimeTime[1] = Integer.parseInt(time1[1]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("wakeup").addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()==null){
                    wakeuptext.setText("09:00");
                }else{
                    wakeuptext.setText(snapshot.getValue().toString());
                    String[] time2 = wakeuptext.getText().toString().split(":");
                    wakeupTime[0] = Integer.parseInt(time2[0]);
                    wakeupTime[1] = Integer.parseInt(time2[1]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("bedtimestatus").addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
                    if(snapshot.getValue().toString().equals("1")){
                        bedtimeSwitch.setChecked(true);
                    }else{
                        bedtimeSwitch.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("wakeupstatus").addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
                    if(snapshot.getValue().toString().equals("1")){
                        wakeupSwitch.setChecked(true);
                        setNotification(wakeup_id, "C'mon wake up, have a nice day", 1, wakeupTime);
                    }else{
                        wakeupSwitch.setChecked(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bedtimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) bedtimes = 1;
                else bedtimes = 0;

                setNotification(bedtime_id, "Let's Sleep", bedtimes, bedtimeTime);

                Log.d("Bedtime check", "onCheckedChanged: " + bedtimes);
            }
        });

        wakeupSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) wakeups = 1;
                else wakeups = 0;

                setNotification(wakeup_id, "wakeup", wakeups, wakeupTime);

                Log.d("Wakeup check", "onCheckedChanged: " + wakeups);
            }
        });

    }

    // Set notification
    @SuppressLint("MissingPermission")
    public void setNotification(String notificationType, String message, int switchStatus, int time[]) {
        Intent intent = new Intent(SleepReminder.this, notificationReceiver.class);

        intent.putExtra("notifType", notificationType);
        intent.putExtra("message", message);
        intent.putExtra("activity", "sleep");

        PendingIntent alarmIntent = PendingIntent.getBroadcast(SleepReminder.this, 0,
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

    public void addBedtime(View view) {
        bedtimetext = findViewById(R.id.sleep_reminder_bedtime_text);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());

        myRef.child("bedtime").setValue(bedtime.getHour()+":"+ checkDigit(bedtime.getMinute()));
        myRef.child("bedtimestatus").setValue(0);
        bedtimetext.setText(bedtime.getHour()+":"+ checkDigit(bedtime.getMinute()));
    }

    public void addWakeUp(View view) {
        wakeuptext = findViewById(R.id.sleep_reminder_wakeup_text);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());

        myRef.child("wakeup").setValue(wakeup.getHour()+":" + checkDigit(wakeup.getMinute()));
        myRef.child("wakeupstatus").setValue(0);
        wakeuptext.setText(wakeup.getHour()+":"+ checkDigit(wakeup.getMinute()));
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}