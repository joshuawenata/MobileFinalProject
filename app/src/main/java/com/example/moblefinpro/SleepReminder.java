package com.example.moblefinpro;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moblefinpro.receiver.BedTimeNotificationReceiver;
import com.example.moblefinpro.receiver.WakeUpNotificationReceiver;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_reminder);

        bedtime = findViewById(R.id.sleep_reminder_bedtime);
        wakeup = findViewById(R.id.sleep_reminder_wakeup);
        Context context = this;

        Intent w = new Intent(context, WakeUpNotificationReceiver.class);
        PendingIntent pendingIntentWakeUp = PendingIntent.getBroadcast(context, 0, w, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManagerWakeUp = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerTimeMillisWakeUp = System.currentTimeMillis() + (wakeup.getHour()* 3600000L +wakeup.getMinute()* 60000L);
        alarmManagerWakeUp.set(AlarmManager.RTC_WAKEUP, triggerTimeMillisWakeUp, pendingIntentWakeUp);

        Intent b = new Intent(context, WakeUpNotificationReceiver.class);
        PendingIntent pendingIntentBedtime = PendingIntent.getBroadcast(context, 0, b, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManagerBedtime = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerTimeMillisBedtime = System.currentTimeMillis() + (bedtime.getHour()* 3600000L +bedtime.getMinute()* 60000L);
        alarmManagerBedtime.set(AlarmManager.RTC_WAKEUP, triggerTimeMillisBedtime, pendingIntentBedtime);

        bedtimetext = findViewById(R.id.sleep_reminder_bedtime_text);
        wakeuptext = findViewById(R.id.sleep_reminder_wakeup_text);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());

        myRef.child("bedtime").addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()==null){
                    bedtimetext.setText("12:30");
                }else{
                    bedtimetext.setText(snapshot.getValue().toString());
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
                    wakeuptext.setText("12:30");
                }else{
                    wakeuptext.setText(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addBedtime(View view) {
        bedtimetext = findViewById(R.id.sleep_reminder_bedtime_text);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());

        myRef.child("bedtime").setValue(bedtime.getHour()+":"+ checkDigit(bedtime.getMinute()));
        bedtimetext.setText(bedtime.getHour()+":"+ checkDigit(bedtime.getMinute()));
    }

    public void addWakeUp(View view) {
        wakeuptext = findViewById(R.id.sleep_reminder_wakeup_text);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(firebaseUser.getUid());

        myRef.child("wakeup").setValue(wakeup.getHour()+":" + checkDigit(wakeup.getMinute()));
        wakeuptext.setText(wakeup.getHour()+":"+ checkDigit(wakeup.getMinute()));
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}