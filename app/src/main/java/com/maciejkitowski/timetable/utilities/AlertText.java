package com.maciejkitowski.timetable.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maciejkitowski.timetable.R;

public class AlertText {
    private static String logcat = "AlertText";

    private TextView text;
    private Context context;

    public AlertText(Context context) {
        this.context = context;
        text = (TextView)((Activity)context).findViewById(R.id.AlertText);
    }

    public void display(int ID) {
        display(context.getString(ID));
    }

    public void display(String value) {
        Log.i(logcat, String.format("Display alert with text: %s", value));
        text.setVisibility(View.VISIBLE);
        text.setText(value);
    }

    public void hide() {
        Log.i(logcat, "Hide alert");
        text.setVisibility(View.GONE);
    }
}
