package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncDataSourceListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

abstract class DateLoader implements AsyncDataSourceListener {
    private static final String logcat = "DateLoader";
    protected final Context context;
    protected List<AsyncDataListener> listeners = new ArrayList<>();
    List<String> dates = new ArrayList<>();

    public DateLoader(Context context) {
        Log.i(logcat, "Initialize");
        this.context = context;
    }

    abstract void start();

    public void addListener(AsyncDataListener listener) {
        Log.i(logcat, "Add new listener");
        listeners.add(listener);
    }

    protected void formatData(List<String> json) {
        Log.i(logcat, String.format("Received json array with %d size", json.size()));
        boolean fail = false;

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
                fail = true;
                for(AsyncDataListener listener : listeners) listener.onReceiveFail(ex.getLocalizedMessage());
            }
        }

        if(!fail) for(AsyncDataListener listener : listeners) listener.onReceiveSuccess(dates);
    }
}
