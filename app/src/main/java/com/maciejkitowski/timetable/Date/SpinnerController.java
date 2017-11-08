package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.DataReceivedListener;

import java.util.List;

public class SpinnerController implements DataReceivedListener, AdapterView.OnItemClickListener {
    private static final String logcat = "SpinnerController";
    private Spinner spinner;

    public SpinnerController(Activity activity) {
        Log.i(logcat, "Initialize spinner controller");
        spinner = (Spinner)activity.findViewById(R.id.Date);
        spinner.setOnItemClickListener(this);
    }

    @Override
    public void onDataReceivedBegin() {
        Log.i(logcat, "Download started");
    }

    @Override
    public void onDataReceivedSuccess(List<String> data) {
        Log.i(logcat, "Download success");
    }

    @Override
    public void onDataReceivedFailed(Exception ex) {
        Log.w(logcat, String.format("Download failed with exception: %s", ex.getLocalizedMessage()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
