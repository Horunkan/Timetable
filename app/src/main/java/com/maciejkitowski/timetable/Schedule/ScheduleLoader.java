package com.maciejkitowski.timetable.Schedule;

import android.app.Activity;
import android.util.Log;

import com.maciejkitowski.timetable.Date.DateChangedListener;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncFileLoader;
import com.maciejkitowski.timetable.utilities.FileUtil;
import com.maciejkitowski.timetable.utilities.UserInterface.RefreshListener;

import java.util.List;

public final class ScheduleLoader implements DateChangedListener, RefreshListener, AsyncDataListener {
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
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
    }

    @Override
    public void onReceiveFail(String message) {
        Log.w(logcat, String.format("Receive fail: %s", message));
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

        AsyncFileLoader loader = new AsyncFileLoader(activity);
        loader.addListener(this);
        loader.execute(String.format(filenameTemplate, selectedDate));
    }

    private void loadFromUrl() {
        Log.i(logcat, "Load schedule from url for date: " + selectedDate);
        loadingFromUrl = true;
    }

    private boolean isFileOnDevice() { return FileUtil.isSavedOnDevice(activity, String.format(filenameTemplate, selectedDate)); }
}
