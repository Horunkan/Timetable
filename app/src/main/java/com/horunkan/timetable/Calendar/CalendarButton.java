package com.horunkan.timetable.Calendar;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

public class CalendarButton implements View.OnClickListener {
    private static final String logcat = "CalendarButton";

    private Timetable timetable;
    private FloatingActionButton button;

    public CalendarButton(Timetable activity) {
        this.timetable = activity;
        button = (FloatingActionButton) activity.findViewById(R.id.CalendarButton);
        button.setOnClickListener(this);
    }

    public void display() {
        Log.i(logcat, "Display");
        button.show();
    }

    public void hide() {
        Log.i(logcat, "Hide");
        button.hide();
    }

    @Override
    public void onClick(View v) {
        Log.i(logcat, "Clicked");
    }
}
