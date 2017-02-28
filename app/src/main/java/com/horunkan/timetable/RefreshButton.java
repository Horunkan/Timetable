package com.horunkan.timetable;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RefreshButton implements View.OnClickListener {
    private static final String logcat = "RefreshButton";

    private Timetable timetable;
    private FloatingActionButton button;
    private TextView errorText;

    public RefreshButton(Timetable activity) {
        timetable = activity;
        button = (FloatingActionButton) activity.findViewById(R.id.RefreshButton);
        errorText = (TextView) activity.findViewById(R.id.ErrorText);
        button.setOnClickListener(this);
    }

    public void display() {
        Log.i(logcat, "Button displayed");
        errorText.setText(timetable.getString(R.string.error_noInternet));
        errorText.setVisibility(View.VISIBLE);
        button.show();
    }

    @Override
    public void onClick(View v) {
        Log.i(logcat, "Clicked");
        button.hide();
        errorText.setVisibility(View.INVISIBLE);
        timetable.refresh();
    }
}
