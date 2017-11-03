package com.maciejkitowski.timetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.maciejkitowski.timetable.Date.DateLoader;

public class MainActivity extends AppCompatActivity {
    private DateLoader dateLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateLoader = new DateLoader(this);
    }
}
