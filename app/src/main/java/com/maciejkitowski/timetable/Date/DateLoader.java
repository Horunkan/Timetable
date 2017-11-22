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
import com.maciejkitowski.timetable.utilities.UserInterface.LoadingBar;
import com.maciejkitowski.timetable.utilities.UserInterface.RefreshListener;

import org.json.JSONArray;

import java.util.LinkedList;
import java.util.List;

final class DateLoader implements AsyncDataListener, RefreshListener {
    private static final String logcat = "DateLoader";
    private final String filename = "dates.json";
    private final String url = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";
    private final Activity activity;

    private List<String> dates = new LinkedList<>();
    private DateSpinnerController spinner;
    private boolean loadingFromUrl = false;

    public DateLoader(Activity activity) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
        spinner = new DateSpinnerController(activity);
    }

    public void start() {
        Log.i(logcat, "Start");

        if(isFileOnDevice()) loadFromFile();
        else loadFromUrl();
    }

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
        dates.clear();
        LoadingBar.display();
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        if(loadingFromUrl) FileUtil.saveToFile(activity, filename, data);
        LoadingBar.hide();
        formatData(data);
        spinner.updateValues(dates);
    }

    @Override
    public void onReceiveFail(String message) {
        Log.w(logcat, String.format("Receive fail: %s", message));
        LoadingBar.hide();
        if(isFileOnDevice()) loadFromFile();
        else AlertText.display(message);
    }

    @Override
    public void onRefresh() {
        Log.i(logcat, "Refresh dates");
        AlertText.hide();
        loadFromUrl();
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

    private void formatData(List<String> json) {
        Log.i(logcat, "Format received json");

        for(String js : json) {
            try {
                JSONArray array = new JSONArray(js);
                Log.i(logcat+"-val", array.toString());

                for(int i = 0; i < array.length(); ++i) {
                    Log.i(logcat+"-val", String.format("Add %s to dates array", array.getString(i)));
                    dates.add(array.getString(i));
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                AlertText.display(ex.getLocalizedMessage());
                break;
            }
        }
    }

    private boolean isFileOnDevice() { return FileUtil.isSavedOnDevice(activity, filename); }
}
