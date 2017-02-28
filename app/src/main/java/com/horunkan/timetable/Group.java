package com.horunkan.timetable;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Group implements AdapterView.OnItemSelectedListener {
    private static final String logcat = "Group";

    private Spinner spinner;

    public Group(Timetable activity) {
        spinner = (Spinner) activity.findViewById(R.id.Group);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, activity.getResources().getStringArray(R.array.groups));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected group: %s", (String)spinner.getSelectedItem()));
    }

    @Override public void onNothingSelected(AdapterView<?> parent) { }
}
