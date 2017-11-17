package com.maciejkitowski.timetable.Date;

import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;

import java.util.List;

final class HtmlLoader extends Loader {
    private static final String logcat = "HtmlLoader";
    private final String[] downloadUrls = {"http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php"};

    @Override
    void start() {
        Log.i(logcat, "Load dates json from urls.");
        startDownloading();
    }

    @Override
    public void onLoadBegin() {
        Log.i(logcat, "Download started");
        for(AsyncDataListener listener : listeners) listener.onReceiveBegin();
    }

    @Override
    public void onLoadSuccess(List<String> data) {
        Log.i(logcat, "Download success");
        for(String dat : data) Log.i(logcat+"-val", dat);
        formatData(data);
    }

    @Override
    public void onLoadFail(String message) {
        Log.w(logcat, String.format("Download failed: %s", message));
    }

    private void startDownloading() {

    }
}
