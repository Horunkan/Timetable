package com.maciejkitowski.timetable.Schedule;

import android.app.Activity;
import android.util.Log;
import android.widget.Spinner;

import com.maciejkitowski.timetable.Date.DateChangedListener;
import com.maciejkitowski.timetable.R;

public class GroupSpinnerController implements DateChangedListener {
    private static final String logcat = "GroupSpinner";
    private final String urlTemplate = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Timetable.php?date=%s";

    private Spinner spinner;
    private Activity activity;
    private String currentDate;

    public GroupSpinnerController(Activity activity) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
        spinner = activity.findViewById(R.id.Group);
    }

    @Override
    public void onDateChanged(String date) {
        Log.i(logcat, "Date changed to: " + date);
        currentDate = date;
    }
}
