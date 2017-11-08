package com.maciejkitowski.timetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.maciejkitowski.timetable.Date.SpinnerController;

public class MainActivity extends AppCompatActivity {
    private static final String logcat = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        Log.i(logcat, "Initialize");

        SpinnerController spinner = new SpinnerController(this);
    }
}
