package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import android.util.Log;

import java.util.List;

final class TestClassObject implements AsyncDataSourceListener {
    private static final String logcat = "UnitTest";
    boolean fail = false;
    boolean success = false;
    
    @Override
    public void onLoadBegin() {
        Log.i(logcat, "Receive begin");
    }

    @Override
    public void onLoadSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        success = true;
    }

    @Override
    public void onLoadFail(String message) {
        Log.i(logcat, "Receive fail: " + message);
        fail = true;
    }
}
