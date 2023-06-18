package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class caloryneeds extends AppCompatActivity {

    private TextView calheight;
    private TextView calweight;
    private TextView calage;
    private TextView calView;

    private SeekBar height_bar;
    private SeekBar weight_bar;
    private SeekBar age_bar;

    final int minHeight = 90, minWeight = 1, minAge = 1;
    private double bmr, calorie;
    private double PAL[] = {1.2, 1.375, 1.55, 1.725, 1.9};
    private int PALpos;

    private Spinner spinner1;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloryneeds);

        // Physical Activity Declaration
        spinner1 = (Spinner) findViewById(R.id.caloryneeds_spinner);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());

        // TextView declaration
        calheight = (TextView) findViewById(R.id.caloryneeds_height_number);
        calweight = (TextView) findViewById(R.id.caloryneeds_weight_number);
        calage = (TextView) findViewById(R.id.caloryneeds_age_number);
        calView = (TextView) findViewById(R.id.caloryneeds_view);

        // SeekBar declaration
        height_bar = (SeekBar) findViewById(R.id.caloryneeds_height_seekbar);
        weight_bar = (SeekBar) findViewById(R.id.caloryneeds_weight_seekbar);
        age_bar = (SeekBar) findViewById(R.id.caloryneeds_age_seekbar);

        Bundle data = getIntent().getExtras();
        gender = data.getString("gender");
        Log.d("Gender", "onCreate: " + gender);

        switch(gender) {
            case "male":
                caloriemale();
                break;
            case "female":
                caloriefemale();
                break;
        }

        // SeekBar modification results in bmr update
        height_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int heightValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                heightValue = i + minHeight;
                calheight.setText("" + heightValue);

                switch(gender) {
                    case "male":
                        caloriemale();
                        break;
                    case "female":
                        caloriefemale();
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });

        weight_bar.setMax(200); // Sets max value for weight
        weight_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int weightValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                weightValue = i + minWeight;
                calweight.setText("" + weightValue);

                switch(gender) {
                    case "male":
                        caloriemale();
                        break;
                    case "female":
                        caloriefemale();
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        age_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int ageValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ageValue = i + minAge;
                calage.setText("" + ageValue);

                switch(gender) {
                    case "male":
                        caloriemale();
                        break;
                    case "female":
                        caloriefemale();
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // Calculates the calorie for men
    public void caloriemale() {
        bmr = 88.362 +
                (13.397 * Double.parseDouble(calweight.getText().toString())) +
                (4.799 * Double.parseDouble(calheight.getText().toString())) +
                (5.677 * Double.parseDouble(calage.getText().toString()));
        calorie = bmr * PAL[PALpos];

        calView.setText("" + calorie);
        Log.d("Male", "male calories: " + calorie);
    }

    // Calculates the calorie for women
    public void caloriefemale() {
        bmr = 447.593 +
                (9.247 * Double.parseDouble(calweight.getText().toString())) +
                (3.098 * Double.parseDouble(calheight.getText().toString())) +
                (4.330 * Double.parseDouble(calage.getText().toString())) * PAL[PALpos];

        calorie = bmr * PAL[PALpos];

        calView.setText("" + calorie);
        Log.d("Female", "female calories: " + calorie);

    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner1.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
                // ToDo when first item is selected
                PALpos = 0;
            } else {
                // Todo when item is selected by the user
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
                PALpos = pos - 1;
                Log.d("PAL position", "onItemSelected: " + PALpos);
                Log.d("Gender", "onItemSelected: " + gender);

                switch(gender) {
                    case "male":
                        caloriemale();
                        break;
                    case "female":
                        caloriefemale();
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}