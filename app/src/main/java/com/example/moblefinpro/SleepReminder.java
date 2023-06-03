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
import androidx.appcompat.app.AppCompatActivity;

import com.example.moblefinpro.receiver.BedTimeNotificationReceiver;
import com.example.moblefinpro.receiver.WakeUpNotificationReceiver;

import java.util.Calendar;

public class SleepReminder extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_reminder);

        Button pickTimeBtn = findViewById(R.id.sleep_bedtimebutton);
        Button pickTimeBtn1 = findViewById(R.id.sleep_bedtimebutton2);
        TextView selectedTimeTV = findViewById(R.id.sleep_switchview3);
        TextView selectedTimeTV1 = findViewById(R.id.sleep_switchview4);
        TimePicker bedtime = findViewById(R.id.bedtime);
        TimePicker wakeup = findViewById(R.id.wakeup);
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

        // on below line we are adding click
        // listener for our pick date button
        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(SleepReminder.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                selectedTimeTV.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
    }
}