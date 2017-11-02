package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.IDownloadable;

import java.io.File;

final class DateFileLoader implements ILoader, IDownloadable {
    private static final String logcat = "DateFileLoader";
    static final String filename = "dates.json";

    @Override
    public void load() {
        Log.i(logcat, "Load dates from file.");
        Log.e(logcat, "Getting dates from file not implemented yet.");
    }

    static boolean isDatesSavedOnDevice(Context context) {
        Log.i(logcat, String.format("Check if %s exists on device.", filename));
        File file = new File(context.getFilesDir(), filename);

        Log.i(logcat, String.format("File %s found: %s", filename, file.exists()));
        return file.exists();
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
