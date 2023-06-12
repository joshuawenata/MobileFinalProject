package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
    double bmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloryneeds);

        // TextView declaration
        calheight = (TextView) findViewById(R.id.calheight_number);
        calweight = (TextView) findViewById(R.id.calweight_number);
        calage = (TextView) findViewById(R.id.calage_number);
        calView = (TextView) findViewById(R.id.cal_view);

        // SeekBar declaration
        height_bar = (SeekBar) findViewById(R.id.calheight_seekbar);
        weight_bar = (SeekBar) findViewById(R.id.calweight_seekbar);
        age_bar = (SeekBar) findViewById(R.id.calage_seekbar);

        // RelativeLayout declaration
        RelativeLayout male = findViewById(R.id.maleview_layoutcal);
        RelativeLayout female = findViewById(R.id.femaleview_layoutcal);

        // Disables the sliders unless a gender has been selected
        height_bar.setEnabled(false);
        weight_bar.setEnabled(false);
        age_bar.setEnabled(false);

        Toast.makeText(this, "Please select your gender first!", Toast.LENGTH_LONG).show();

        // When male layout is chosen
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Displays the default bmr value first
                bmrmale();

                // Enables the sliders
                height_bar.setEnabled(true);
                weight_bar.setEnabled(true);
                age_bar.setEnabled(true);

                // SeekBar modification results in bmr update
                height_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int heightValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        heightValue = i + minHeight;
                        calheight.setText("" + heightValue);
                        bmrmale();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                });

                weight_bar.setMax(200); // Sets max value for weight
                weight_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int weightValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        weightValue = i + minWeight;
                        calweight.setText("" + weightValue);
                        bmrmale();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

                age_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int ageValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        ageValue = i + minAge;
                        calage.setText("" + ageValue);
                        bmrmale();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

            }
        });

        // When female layout is chosen
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Displays the lowest bmr value first
                bmrfemale();

                // Enables the sliders
                height_bar.setEnabled(true);
                weight_bar.setEnabled(true);
                age_bar.setEnabled(true);

                // SeekBar modification results in bmr update
                height_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int heightValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        heightValue = i + minHeight;
                        calheight.setText("" + heightValue);
                        bmrfemale();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                });

                weight_bar.setMax(200); // Sets max value for weight
                weight_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int weightValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        weightValue = i + minWeight;
                        calweight.setText("" + weightValue);
                        bmrfemale();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

                age_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int ageValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        ageValue = i + minAge;
                        calage.setText("" + ageValue);
                        bmrfemale();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

            }
        });
    }

    // Calculates the bmr for men
    public void bmrmale(){
        bmr = 88.362 +
                (13.397 * Double.parseDouble(calweight.getText().toString())) +
                (4.799 * Double.parseDouble(calheight.getText().toString())) +
                (5.677 * Double.parseDouble(calage.getText().toString()));

        calView.setText("" + bmr);
    }

    // Calculates the bmr for women
    public void bmrfemale(){
        bmr = 447.593 +
                (9.247 * Double.parseDouble(calweight.getText().toString())) +
                (3.098 * Double.parseDouble(calheight.getText().toString())) +
                (4.330 * Double.parseDouble(calage.getText().toString()));

        calView.setText("" + bmr);
    }

}