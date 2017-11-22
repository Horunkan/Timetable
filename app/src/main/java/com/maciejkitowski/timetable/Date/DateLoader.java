package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncFileLoader;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncHtmlDownloader;
import com.maciejkitowski.timetable.utilities.FileUtil;
import com.maciejkitowski.timetable.utilities.InternetConnection;
import com.maciejkitowski.timetable.utilities.UserInterface.AlertText;

import java.util.List;

public class DateLoader implements AsyncDataListener {
    private static final String logcat = "DateLoader";
    private final String filename = "dates.json";
    private final String url = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";
    private final Activity activity;

    private boolean loadingFromUrl = false;

    public DateLoader(Activity activity) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
    }

    public void start() {
        Log.i(logcat, "Start");

        if(isFileOnDevice()) loadFromFile();
        else loadFromUrl();
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

    private void loadFromFile() {
        Log.i(logcat, "Load dates from file");
        loadingFromUrl = false;

        AsyncFileLoader loader = new AsyncFileLoader(activity);
        loader.addListener(this);
        loader.execute(filename);
    }

    private void loadFromUrl() {
        Log.i(logcat, "Load dates from url");
        loadingFromUrl = true;

        if(InternetConnection.isConnected(activity)) {
            AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
            downloader.addListener(this);
            downloader.execute(url);
        }
        else {
            if(isFileOnDevice()) {
                Toast.makeText(activity, R.string.error_nointernet_foundfile, Toast.LENGTH_LONG).show();
                loadFromFile();
            }
            else AlertText.display(activity.getString(R.string.error_nointernet));
        }
    }

    private boolean isFileOnDevice() { return FileUtil.isSavedOnDevice(activity, filename); }
}
