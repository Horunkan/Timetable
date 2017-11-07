package com.maciejkitowski.timetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.maciejkitowski.timetable.Date.Loader;

public class MainActivity extends AppCompatActivity {
    private Loader dateLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateLoader = new Loader(this);
    }
}
