package com.maciejkitowski.timetable.utilities.UserInterface;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.maciejkitowski.timetable.R;

import java.util.ArrayList;
import java.util.List;

public class RefreshButton implements View.OnClickListener {
    private static final String logcat = "RefreshButton";

    private FloatingActionButton button;
    private List<RefreshListener> listeners;

    public RefreshButton(Activity activity) {
        Log.i(logcat, "Initialize");
        listeners = new ArrayList<>();
        button = (FloatingActionButton)activity.findViewById(R.id.RefreshButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(logcat, "Pressed button");
        AlertText.hide();
        LoadingBar.hide();
        callListeners();
    }

    public void addRefreshListener(RefreshListener listener) {
        Log.i(logcat, "Add new listener");
        listeners.add(listener);
    }

    private void callListeners() {
        Log.i(logcat, String.format("Call %d listeners", listeners.size()));
        for(RefreshListener lis : listeners) lis.onRefresh();
    }
}
