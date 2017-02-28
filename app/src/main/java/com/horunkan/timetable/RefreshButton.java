package com.horunkan.timetable;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

public class RefreshButton implements View.OnClickListener {
    private static final String logcat = "RefreshButton";

    private FloatingActionButton button;

    public RefreshButton(Timetable activity) {
        button = (FloatingActionButton) activity.findViewById(R.id.RefreshButton);
        button.setOnClickListener(this);
    }

    public void display() {
        Log.i(logcat, "Button displayed");
        button.show();
    }

    @Override
    public void onClick(View v) {
        Log.i(logcat, "Clicked");
    }
}
