package com.maciejkitowski.timetable.Schedule;

import android.app.Activity;
import android.util.Log;
import android.widget.Spinner;
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

import java.util.List;

public final class GroupSpinnerController implements DateChangedListener, AsyncDataListener {
    private static final String logcat = "GroupSpinner";
    private final String urlTemplate = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Timetable.php?date=%s";

    private Spinner spinner;
    private Activity activity;
    private String selectedDate;
    private boolean loadingFromUrl = false;

    public GroupSpinnerController(Activity activity) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
        spinner = activity.findViewById(R.id.Group);
    }

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
        LoadingBar.display();
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        if(loadingFromUrl) FileUtil.saveToFile(activity, selectedDate + ".json", data);
        LoadingBar.hide();
        formatData(data);
    }

    @Override
    public void onReceiveFail(String message) {
        Log.w(logcat, String.format("Receive fail: %s", message));
        if(isFileOnDevice()) loadFromFile();
        else AlertText.display(message);
    }

    @Override
    public void onDateChanged(String date) {
        Log.i(logcat, "Date changed to: " + date);
        selectedDate = date;
        loadSelectedDate();
    }

    private void loadSelectedDate() {
        Log.i(logcat, "Load current date: " + selectedDate);

        if(isFileOnDevice()) loadFromFile();
        else loadFromUrl();
    }

    private void loadFromFile() {
        Log.i(logcat, "Load schedule from file");
        loadingFromUrl = false;

        AsyncFileLoader loader = new AsyncFileLoader(activity);
        loader.addListener(this);
        loader.execute(selectedDate + ".json");
    }

    private void loadFromUrl() {
        Log.i(logcat, "Load schedule from url");
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

    private void formatData(List<String> data) {
        Log.i(logcat, "Format received data");
    }

    private boolean isFileOnDevice() { return FileUtil.isSavedOnDevice(activity, selectedDate + ".json"); }
}
