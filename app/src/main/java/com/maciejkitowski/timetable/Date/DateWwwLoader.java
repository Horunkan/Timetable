package com.maciejkitowski.timetable.Date;

import android.util.Log;

import com.maciejkitowski.timetable.utilities.IDownloadable;

final class DateWwwLoader implements ILoader, IDownloadable {
    private static final String logcat = "DateWwwLoader";
    private final String downloadUrl = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";

    @Override
    public void load() {
        Log.i(logcat, "Load dates from url.");
        Log.e(logcat, "Getting dates from url not implemented yet.");
    }

    @Override
    public void downloadStarted() {

    }

    @Override
    public void downloadSuccessful() {

    }

    @Override
    public void downloadFailed() {

    }
}
