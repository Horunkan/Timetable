package com.horunkan.timetable;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GroupSpinner implements AdapterView.OnItemSelectedListener {
    private static final String logcat = "GroupSpinner";

    private Spinner spinner;
    private Timetable activity;

    public GroupSpinner(Timetable activity) {
        spinner = (Spinner) activity.findViewById(R.id.Group);
        this.activity = activity;
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, activity.getResources().getStringArray(R.array.groups));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
    }

    public int get() { return (int)spinner.getSelectedItemId(); }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected group: %s", (String)spinner.getSelectedItem()));
        activity.refreshLessons();
    }

    @Override public void onNothingSelected(AdapterView<?> parent) { }
}
