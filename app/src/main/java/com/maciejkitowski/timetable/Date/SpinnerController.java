package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.maciejkitowski.timetable.R;

public class SpinnerController implements AdapterView.OnItemClickListener {
    private static final String logcat = "SpinnerController";
    private Spinner spinner;

    public SpinnerController(Activity activity) {
        Log.i(logcat, "Initialize spinner controller");
        spinner = (Spinner)activity.findViewById(R.id.Date);
        spinner.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
