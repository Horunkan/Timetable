package com.horunkan.timetable.utilities;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

public class RefreshButton implements View.OnClickListener {
    private static final String logcat = "RefreshButton";

    private Timetable timetable;
    private FloatingActionButton button;
    private TextView errorText;
    private Spinner dateSpinner;

    public RefreshButton(Timetable activity) {
        timetable = activity;
        button = (FloatingActionButton) activity.findViewById(R.id.RefreshButton);
        dateSpinner = (Spinner) activity.findViewById(R.id.Date);
        errorText = (TextView) activity.findViewById(R.id.ErrorText);
        button.setOnClickListener(this);
    }

    public void display() {
        Log.i(logcat, "Button displayed");
        errorText.setText(timetable.getString(R.string.error_noInternet));
        errorText.setVisibility(View.VISIBLE);
        dateSpinner.setVisibility(View.INVISIBLE);
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
