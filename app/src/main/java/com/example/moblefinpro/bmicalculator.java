package com.example.moblefinpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class bmicalculator extends AppCompatActivity {

    private TextView bmi_weight;
    private TextView bmi_height;
    private TextView bmi_view;
    private SeekBar weight_bar;
    private SeekBar height_bar;
    private double height, weight, bmi;
    final int minHeight = 90;
    final int minWeight = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        bmi_weight = (TextView) findViewById(R.id.bmicalculator_weight_number);
        bmi_height = (TextView) findViewById(R.id.bmicalculator_height_number);
        bmi_view = (TextView) findViewById(R.id.bmicalculator_view);

        weight_bar = (SeekBar) findViewById(R.id.bmicalculator_weight_seekbar);
        height_bar = (SeekBar) findViewById(R.id.bmicalculator_height_seekbar);

        Button confirmButton = (Button) findViewById(R.id.bmicalculator_calcbutton);
        Button resetButton = (Button) findViewById(R.id.bmicalculator_resetbutton);

        // Height bar values adjusting
        height_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int heightValue;

            // Changing the value of bmi_height when bar is moved
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                heightValue = i + minHeight; // minHeight is used to cap the lower constraint
                bmi_height.setText(""+heightValue);
            }

            // Unused implemented method
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            // Unused implemented method
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Weight bar values adjusting
        weight_bar.setMax(200); // Sets the max value of the weight bar
        weight_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int weightValue;

            // Changing the value of bmi_weight when bar is moved
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                weightValue = i+minWeight;
                bmi_weight.setText(""+weightValue);
            }

            // Unused implemented method
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // Unused implemented method
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Confirm button onClick
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Convert height and weight string values to double for calculation
                height = Double.parseDouble(bmi_height.getText().toString());
                weight = Double.parseDouble(bmi_weight.getText().toString());

                // BMI = weight in kilos divided by square of height in meter
                bmi = weight/((height/100)*(height/100));

                /* Debugging
                Log.d("Height", "onClick: " + height);
                Log.d("Weight", "onClick: " + weight);
                Log.d("BMI", "onClick: " + bmi); */

                bmi_view.setText(new DecimalFormat("##.##").format(bmi));
            }
        });

        // Reset button onClick
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmi_height.setText(""+minHeight);
                bmi_weight.setText(""+minWeight);
                bmi_view.setText("0");

                height_bar.setProgress(0);
                weight_bar.setProgress(0);
            }
        });

    }
}