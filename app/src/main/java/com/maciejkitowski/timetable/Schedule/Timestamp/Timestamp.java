package com.maciejkitowski.timetable.Schedule.Timestamp;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maciejkitowski.timetable.R;

final class Timestamp {
    private static final String logcat = "Timestamp";
    private static final float textSize = 13f;

    private final LinearLayout layout;
    private final int hour;
    private final TextView text;

    public Timestamp(Activity activity, int hour, int height) {
        Log.i(logcat, String.format("Create %02d:00", hour, height));
        layout = activity.findViewById(R.id.Timestamp);
        this.hour = hour;

        text = new TextView(activity);
        text.setText(String.format("%02d:00", hour));
        text.setPadding(10, 0, 10, 0);
        text.setHeight(height);
        text.setTextSize(textSize);
        layout.addView(text);
    }

    public void delete() {
        Log.i(logcat, String.format("Remove %02d:00", hour));
        layout.removeView(text);
    }
}
