package com.maciejkitowski.timetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.maciejkitowski.timetable.Date.DateLoader;
import com.maciejkitowski.timetable.Date.DateSpinnerController;
import com.maciejkitowski.timetable.Schedule.GroupSpinnerController;
import com.maciejkitowski.timetable.Schedule.ScheduleLoader;
import com.maciejkitowski.timetable.utilities.UserInterface.AlertText;
import com.maciejkitowski.timetable.utilities.UserInterface.CurrentTimeLine;
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
        initializeStatic();
        initializeTimetable();
    }

    private void initializeStatic() {
        Log.i(logcat, "Initialize static classes");
        JodaTimeAndroid.init(this);
        AlertText.initialize(this);
        LoadingBar.initialize(this);
    }

    private void initializeTimetable() {
        Log.i(logcat, "Initialize timetable");
        CurrentTimeLine timePointer = new CurrentTimeLine(this);
        RefreshButton refresh = new RefreshButton(this);
        DateSpinnerController dateSpinner = new DateSpinnerController(this);
        GroupSpinnerController groupSpinner = new GroupSpinnerController(this);

        DateLoader date = new DateLoader(this, dateSpinner);
        ScheduleLoader schedule = new ScheduleLoader(this, groupSpinner);

        refresh.addRefreshListener(date);
        refresh.addRefreshListener(schedule);
        dateSpinner.addListener(schedule);
        timePointer.display();
        date.start();
    }
}
