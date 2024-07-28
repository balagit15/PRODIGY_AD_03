package com.example.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

public class CustomChronometer1 extends Chronometer {
    private long baseTime;
    private boolean running;
    private Handler handler;
    private Runnable ticker;

    public CustomChronometer1(Context context) {
        super(context);
        init();
    }

    public CustomChronometer1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomChronometer1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        handler = new Handler();
        ticker = new Runnable() {
            @Override
            public void run() {
                if (running) {
                    long elapsedMillis = SystemClock.elapsedRealtime() - baseTime;
                    setText(formatElapsedTime(elapsedMillis));
                    handler.postDelayed(this, 10); // Update every 10 milliseconds
                }
            }
        };
    }

    public void start() {
        baseTime = SystemClock.elapsedRealtime();
        running = true;
        handler.post(ticker);
    }

    public void stop() {
        running = false;
        handler.removeCallbacks(ticker);
    }

    public void reset() {
        stop();
        setText("00:00.00");
    }

    private String formatElapsedTime(long elapsedMillis) {
        long minutes = (elapsedMillis / 60000) % 60;
        long seconds = (elapsedMillis / 1000) % 60;
        long milliseconds = (elapsedMillis % 1000) / 10;
        return String.format("%02d:%02d.%02d", minutes, seconds,  milliseconds);
    }
}

