package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class DateLoader {
    private static final String logcat = "DateLoader";
    private final String filename = "dates.json";
    private final String downloadUrl = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";
    private final Context context;

    public DateLoader(Context context) {
        Log.i(logcat, "Initialize date loader");
        this.context = context;

        if(isDatesSavedOnDevice()) loadDatesFromFile();
        else loadDatesFromWWW();
    }

    private void loadDatesFromFile() {
        Log.i(logcat, String.format("Load dates from %s file.", filename));
        Log.w(logcat, "Getting dates from file not work yet.");
    }

    private void loadDatesFromWWW() {
        Log.i(logcat, String.format("Load dates from %s url.", downloadUrl));
        Log.w(logcat, "Getting dates from url not work yet.");
    }

    private boolean isDatesSavedOnDevice() {
        Log.i(logcat, String.format("Check if %s exists on device.", filename));
        File file = new File(context.getFilesDir(), filename);

        Log.i(logcat, String.format("File %s found: %s", filename, file.exists()));
        return file.exists();
    }
}
