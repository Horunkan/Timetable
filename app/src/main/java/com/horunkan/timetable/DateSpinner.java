package com.horunkan.timetable;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class DateSpinner implements AdapterView.OnItemSelectedListener {
    private static final String logcat = "DateSpinner";

    private Spinner spinner;

    public DateSpinner(Timetable activity, ArrayList<String> dates) {
        spinner = (Spinner) activity.findViewById(R.id.Date);
        spinner.setVisibility(View.VISIBLE);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, dates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected: %s", spinner.getSelectedItem()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
