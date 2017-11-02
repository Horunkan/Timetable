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
        Log.i(logcat, "Download started");
    }

    @Override
    public void downloadSuccessful() {
        Log.i(logcat, "Download success");
    }

    @Override
    public void downloadFailed(Exception exception) {
        Log.i(logcat, String.format("Download failed with exception: %s", exception.getLocalizedMessage()));
    }
}
