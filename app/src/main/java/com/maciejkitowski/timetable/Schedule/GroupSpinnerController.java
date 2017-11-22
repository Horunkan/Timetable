package com.maciejkitowski.timetable.Schedule;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.maciejkitowski.timetable.R;

import java.util.LinkedList;
import java.util.List;

public final class GroupSpinnerController implements AdapterView.OnItemSelectedListener {
    private static final String logcat = "GroupSpinner";

    private Spinner spinner;
    private Activity activity;

    public GroupSpinnerController(Activity activity) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
        spinner = activity.findViewById(R.id.Group);
    }

    public void updateValues(List<Group> groups) {
        Log.i(logcat, "Update spinner values - Received values count: " + groups.size());
        List<String> buffer = new LinkedList<>();
        for(Group gr : groups) {
            Log.i(logcat+"-val", gr.toString());
            buffer.add(gr.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, buffer);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected group: %s", spinner.getSelectedItem()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
