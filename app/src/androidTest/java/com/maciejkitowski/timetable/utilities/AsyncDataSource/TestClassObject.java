package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import android.util.Log;

import java.util.List;

final class TestClassObject implements AsyncDataListener {
    private static final String logcat = "UnitTest";
    boolean fail = false;
    boolean success = false;

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        success = true;
    }

    @Override
    public void onReceiveFail(String message) {
        Log.i(logcat, "Receive fail: " + message);
        fail = true;
    }
}
