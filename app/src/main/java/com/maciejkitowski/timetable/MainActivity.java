package com.maciejkitowski.timetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.maciejkitowski.timetable.Date.Loader;
import com.maciejkitowski.timetable.utilities.AlertText;
import com.maciejkitowski.timetable.utilities.LoadingBarToggle;

public class MainActivity extends AppCompatActivity {
    public static AlertText alertDisplayer;
    public static LoadingBarToggle loadingBar;
    private static final String logcat = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        Log.i(logcat, "Initialize");
        alertDisplayer = new AlertText(this);
        loadingBar = new LoadingBarToggle(this);

        Loader dateLoader = new Loader(this);
    }
}
