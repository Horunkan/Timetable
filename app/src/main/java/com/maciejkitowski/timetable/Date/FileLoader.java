package com.maciejkitowski.timetable.Date;

import android.util.Log;

final class FileLoader extends Loader {
    private static final String logcat = "FileLoader";
    static final String filename = "dates.json";

    @Override
    void start() {
        Log.i(logcat, "Start");
    }
}
