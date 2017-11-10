package com.maciejkitowski.timetable.utilities.UserInterface;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maciejkitowski.timetable.MainActivity;
import com.maciejkitowski.timetable.R;

public class AlertText {
    private static final String logcat = "AlertText";
    private static MainActivity activity;
    private static TextView text;

    private AlertText() { }

    public static void initialize(MainActivity activity) {
        Log.i(logcat, "Initialize");
        AlertText.activity = activity;
        text = (TextView) activity.findViewById(R.id.AlertText);
    }

    public static void display(int ID) {
        display(activity.getString(ID));
    }

    public static void display(String message) {
        Log.i(logcat, String.format("Display alert with text: %s", message));
        if(text != null) {
            text.setVisibility(View.VISIBLE);
            text.setText(message);
        }
        else Log.e(logcat, "Alert text not initialized");
    }

    public static void hide() {
        Log.i(logcat, "Hide alert");
        if(text != null)text.setVisibility(View.GONE);
        else Log.e(logcat, "Alert text not initialized");
    }
}
