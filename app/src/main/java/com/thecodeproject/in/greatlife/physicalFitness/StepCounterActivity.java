package com.thecodeproject.in.greatlife.physicalFitness;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.thecodeproject.in.greatlife.R;

public class StepCounterActivity extends AppCompatActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private TextView stepCountTextView;
    private int stepCount = 0;
    private Button startStopButton;
    private boolean isCounting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        stepCountTextView = findViewById(R.id.tv_step);

        startStopButton = findViewById(R.id.btn_step);

        // Initialize sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(this,"Device does not support step counting sensor",Toast.LENGTH_SHORT).show();
        }

        // Set button click listener
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCounting) {
                    stopCounting();
                } else {
                    startCounting();
                }
            }
        });

    }

    private void startCounting() {
        isCounting = true;
        startStopButton.setText("Stop Counting");
        // Register the sensor listener
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void stopCounting() {
        isCounting = false;
        startStopButton.setText("Start Counting");
        // Unregister the sensor listener to save battery
        if (stepSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop counting when the app is paused
        if (isCounting) {
            stopCounting();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            // Update the step count
            stepCount = (int) event.values[0];
            // Update the UI
            stepCountTextView.setText("Steps: " + stepCount);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }
}