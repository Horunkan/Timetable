package com.maciejkitowski.timetable.Date;

import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncDataSourceListener;

import java.util.ArrayList;
import java.util.List;

abstract class Loader implements AsyncDataSourceListener {
    private static final String logcat = "Loader";
    protected List<AsyncDataListener> listeners = new ArrayList<>();

    abstract void start();

    public void addListener(AsyncDataListener listener) {
        Log.i(logcat, "Add new listener");
        listeners.add(listener);
    }
}
