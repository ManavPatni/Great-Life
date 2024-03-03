package com.thecodeproject.in.greatlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.thecodeproject.in.greatlife.physicalFitness.ExerciseActivity;
import com.thecodeproject.in.greatlife.physicalFitness.StepCounterActivity;

public class PhysicalFitnessActivity extends AppCompatActivity {

    private LinearLayout exerciseLL, stepCounterLL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_fitness);

        exerciseLL = findViewById(R.id.ll_exercise);
        stepCounterLL = findViewById(R.id.ll_stepCounter);

        exerciseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhysicalFitnessActivity.this, ExerciseActivity.class);
                startActivity(i);
            }
        });

        stepCounterLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhysicalFitnessActivity.this, StepCounterActivity.class);
                startActivity(i);
            }
        });

    }
}
