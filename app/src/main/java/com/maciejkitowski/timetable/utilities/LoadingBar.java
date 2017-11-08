package com.maciejkitowski.timetable.utilities;

import android.util.Log;
import android.view.View;

import com.maciejkitowski.timetable.MainActivity;
import com.maciejkitowski.timetable.R;

public final class LoadingBar {
    private static final String logcat = "ProgressBar";
    private static View bar;

    private LoadingBar() { }

    public static void initialize(MainActivity activity) {
        bar = activity.findViewById(R.id.LoadingBar);
    }

    public static void display() {
        Log.i(logcat, "Display progress bar");
        if(bar != null) bar.setVisibility(View.VISIBLE);
        else Log.e(logcat, "Progress bar not initialized");
    }

    public static void hide() {
        Log.i(logcat, "Hide progress bar");
        if(bar != null) bar.setVisibility(View.GONE);
        else Log.e(logcat, "Progress bar not initialized");
    }
}
