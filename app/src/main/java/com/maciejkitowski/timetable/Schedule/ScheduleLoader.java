package com.maciejkitowski.timetable.Schedule;

import android.app.Activity;
import android.util.Log;

import com.maciejkitowski.timetable.Date.DateChangedListener;
import com.maciejkitowski.timetable.utilities.FileUtil;
import com.maciejkitowski.timetable.utilities.UserInterface.RefreshListener;

public final class ScheduleLoader implements DateChangedListener, RefreshListener {
    private static final String logcat = "ScheduleLoader";
    private final String filenameTemplate = "%s.json";
    private final String urlTemplate = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Timetable.php?date=%s";
    private final Activity activity;
    private final GroupSpinnerController spinner;

    private String selectedDate;
    private boolean loadingFromUrl = false;

    public ScheduleLoader(Activity activity, GroupSpinnerController spinner) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
        this.spinner = spinner;
    }

    @Override
    public void onDateChanged(String date) {
        Log.i(logcat, "Date changed to: " + date);
        selectedDate = date;
        loadSelectedSchedule();
    }

    @Override
    public void onRefresh() {
        Log.i(logcat, "Refresh saved schedules");
    }

    private void loadSelectedSchedule() {
        Log.i(logcat, "Load schedule for date: " + selectedDate);

        if(isFileOnDevice()) loadFromFile();
        else loadFromUrl();
    }

    private void loadFromFile() {
        Log.i(logcat, "Load schedule from file for date: " + selectedDate);
        loadingFromUrl = false;
    }

    private void loadFromUrl() {
        Log.i(logcat, "Load schedule from url for date: " + selectedDate);
        loadingFromUrl = true;
    }

    private boolean isFileOnDevice() { return FileUtil.isSavedOnDevice(activity, String.format(filenameTemplate, selectedDate)); }
}
