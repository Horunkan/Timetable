package com.maciejkitowski.timetable.Date;

import android.util.Log;

final class DateWwwLoader implements ILoader {
    private static final String logcat = "DateWwwLoader";
    private final String downloadUrl = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";

    @Override
    public void load() {
        Log.i(logcat, "Load dates from url.");
        Log.e(logcat, "Getting dates from url not implemented yet.");
    }
}
