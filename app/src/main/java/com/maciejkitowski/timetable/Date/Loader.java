package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

final class Loader implements AsyncDataListener {
    private static final String logcat = "DateLoader";

    private ILoader loader;
    private AsyncDataListener listener;
    List<String> dates;

    public Loader(Context context, AsyncDataListener listener) {
        Log.i(logcat, "Initialize date loader");
        this.listener = listener;

        if(FileLoader.isDatesSavedOnDevice(context)) loader = new FileLoader();
        else loader = new HtmlLoader(context, this);

        loader.load();
    }

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
        listener.onReceiveBegin();
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        formatReceivedData(data);
    }

    @Override
    public void onReceiveFail(String message) {
        Log.i(logcat, String.format("Receive fail: %s", message));
        listener.onReceiveFail(message);
    }

    void formatReceivedData(List<String> json) {
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
                Log.e(logcat, ex.getMessage());
                listener.onReceiveFail(ex.getLocalizedMessage());
            }
        }
    }
}
