package com.maciejkitowski.timetable.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.maciejkitowski.timetable.R;

public final class LoadingBarToggle {
    private static String logcat = "ProgressBar";

    private View bar;

    public LoadingBarToggle(Context context) {
        bar = ((Activity)context).findViewById(R.id.LoadingBar);
    }

    public void display() {
        Log.i(logcat, "Display progress bar");
        bar.setVisibility(View.VISIBLE);
    }

    public void hide() {
        Log.i(logcat, "Hide progress bar");
        bar.setVisibility(View.GONE);
    }
}
