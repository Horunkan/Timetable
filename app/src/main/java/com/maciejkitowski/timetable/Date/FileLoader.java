package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncFileLoader;

import java.util.List;

final class FileLoader extends Loader {
    private static final String logcat = "FileLoader";
    static final String filename = "dates.json";
    private final Context context;

    public FileLoader(Context context) {
        Log.i(logcat, "Initialize");
        this.context = context;
    }

    @Override
    void start() {
        Log.i(logcat, "Start");
        startLoading();
    }

    @Override
    public void onLoadBegin() {
        Log.i(logcat, "Begin load");
        for(AsyncDataListener listener : listeners) listener.onReceiveBegin();
    }

    @Override
    public void onLoadSuccess(List<String> data) {
        Log.i(logcat, "Load success");
        for(String dat : data) Log.i(logcat+"-val", dat);
    }

    @Override
    public void onLoadFail(String message) {
        Log.i(logcat, "Load fail: " + message);
        for(AsyncDataListener listener : listeners) listener.onReceiveFail(message);
    }

    private void startLoading() {
        Log.i(logcat, "Start loading dates from file.");
        AsyncFileLoader loader = new AsyncFileLoader(context);
        loader.addListener(this);
        loader.execute(filename);
    }
}
