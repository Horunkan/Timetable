package com.maciejkitowski.timetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.maciejkitowski.timetable.Date.DateSpinnerController;
import com.maciejkitowski.timetable.utilities.UserInterface.AlertText;
import com.maciejkitowski.timetable.utilities.UserInterface.LoadingBar;
import com.maciejkitowski.timetable.utilities.UserInterface.RefreshButton;

import net.danlew.android.joda.JodaTimeAndroid;

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
        JodaTimeAndroid.init(this);
        AlertText.initialize(this);
        LoadingBar.initialize(this);
        RefreshButton refresh = new RefreshButton(this);

        DateSpinnerController spinner = new DateSpinnerController(this);
        refresh.addRefreshListener(spinner);
    }
}
