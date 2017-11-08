package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

abstract class Loader implements AsyncDataListener {
    private static final String logcat = "DateLoader";

    protected Context context;
    protected AsyncDataListener listener;
    protected List<String> dates;

    public Loader(Context context, AsyncDataListener listener) {
        Log.i(logcat, "Initialize date loader");
        this.context = context;
        this.listener = listener;
    }

    public abstract void start();

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
        listener.onReceiveBegin();
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        setReceivedData(data);
    }

    @Override
    public void onReceiveFail(String message) {
        Log.i(logcat, String.format("Receive fail: %s", message));
        listener.onReceiveFail(message);
    }

    protected void setReceivedData(List<String> json) {
        Log.i(logcat, String.format("Received json array with %d size", json.size()));
        dates = new ArrayList<>();

        for(String js : json) {
            try {
                JSONArray array = new JSONArray(js);
                Log.i(logcat+"-val", array.toString());

                for(int i = 0; i < array.length(); ++i) {
                    Log.i(logcat+"-val", String.format("Add %s to dates array", array.getString(i)));
                    dates.add(array.getString(i));
                }

                listener.onReceiveSuccess(dates);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                listener.onReceiveFail(ex.getLocalizedMessage());
            }
        }
    }
}
