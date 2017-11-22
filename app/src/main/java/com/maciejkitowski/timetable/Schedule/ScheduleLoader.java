package com.maciejkitowski.timetable.Schedule;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.maciejkitowski.timetable.Date.DateChangedListener;
import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncFileLoader;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncHtmlDownloader;
import com.maciejkitowski.timetable.utilities.FileUtil;
import com.maciejkitowski.timetable.utilities.InternetConnection;
import com.maciejkitowski.timetable.utilities.UserInterface.AlertText;
import com.maciejkitowski.timetable.utilities.UserInterface.LoadingBar;
import com.maciejkitowski.timetable.utilities.UserInterface.RefreshListener;

import java.util.LinkedList;
import java.util.List;

public final class ScheduleLoader implements DateChangedListener, RefreshListener, AsyncDataListener {
    private static final String logcat = "ScheduleLoader";
    private final String filenameTemplate = "%s.json";
    private final String urlTemplate = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Timetable.php?date=%s";
    private final Activity activity;
    private final GroupSpinnerController spinner;

    private String selectedDate;
    private boolean loadingFromUrl = false;
    private List<Group> groups = new LinkedList<>();

    public ScheduleLoader(Activity activity, GroupSpinnerController spinner) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
        this.spinner = spinner;
    }

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
        groups.clear();
        LoadingBar.display();
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        if(loadingFromUrl) FileUtil.saveToFile(activity, String.format(filenameTemplate, selectedDate), data);
        LoadingBar.hide();
        formatData(data);
    }

    @Override
    public void onReceiveFail(String message) {
        Log.w(logcat, String.format("Receive fail: %s", message));
        LoadingBar.hide();
        if(isFileOnDevice()) loadFromFile();
        else AlertText.display(message);
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

        if(InternetConnection.isConnected(activity)) {
            AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
            downloader.addListener(this);
            downloader.execute(String.format(urlTemplate, selectedDate));
        }
        else {
            if(isFileOnDevice()) {
                Toast.makeText(activity, R.string.error_nointernet_foundfile, Toast.LENGTH_LONG).show();
                loadFromFile();
            }
            else AlertText.display(activity.getString(R.string.error_nointernet));
        }
    }

    private void formatData(List<String> json) {
        Log.i(logcat, "Format received json");
    }

    private boolean isFileOnDevice() { return FileUtil.isSavedOnDevice(activity, String.format(filenameTemplate, selectedDate)); }
}
