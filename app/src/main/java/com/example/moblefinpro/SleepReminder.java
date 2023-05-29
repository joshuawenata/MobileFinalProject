package com.example.moblefinpro;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SleepReminder extends AppCompatActivity {
    private Button pickTimeBtn;
    private Button pickTimeBtn1;
    private TextView selectedTimeTV;
    private TextView selectedTimeTV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_reminder);
        pickTimeBtn = findViewById(R.id.sleep_bedtimebutton);
        pickTimeBtn1 = findViewById(R.id.sleep_bedtimebutton2);
        selectedTimeTV = findViewById(R.id.sleep_switchview3);
        selectedTimeTV1 = findViewById(R.id.sleep_switchview4);

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