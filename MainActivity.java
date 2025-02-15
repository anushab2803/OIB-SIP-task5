package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView timerTextView;
    private Button startButton, stopButton, resetButton;

    private Handler handler = new Handler();
    private long startTime = 0L, elapsedTime = 0L;
    private boolean isRunning = false;

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                long currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;
                updateTimeDisplay();
                handler.postDelayed(this, 10);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(v -> startTimer());
        stopButton.setOnClickListener(v -> stopTimer());
        resetButton.setOnClickListener(v -> resetTimer());
    }

    private void startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            isRunning = true;
            handler.post(updateTimer);
        }
    }

    private void stopTimer() {
        if (isRunning) {
            isRunning = false;
            handler.removeCallbacks(updateTimer);
        }
    }

    private void resetTimer() {
        isRunning = false;
        handler.removeCallbacks(updateTimer);
        elapsedTime = 0L;
        updateTimeDisplay();
    }

    private void updateTimeDisplay() {
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) (elapsedTime / 60000) % 60;
        int seconds = (int) (elapsedTime / 1000) % 60;
        int centiseconds = (int) ((elapsedTime % 1000) * 60 / 1000);

        String time = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, centiseconds);
        timerTextView.setText(time);
    }


}
