package com.horunkan.timetable;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.horunkan.timetable.utilities.DateParser;

import java.util.ArrayList;
import java.util.Date;

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

        updateSelection(dates);
    }

    public String get() { return (String) spinner.getSelectedItem(); }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected date: %s", spinner.getSelectedItem()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void updateSelection(ArrayList<String> dates) {
        Date currentDate = DateParser.parse(new Date(), "00:00");

        for(int i = 0; i < dates.size(); ++i) {
            Date buffer = DateParser.parse(dates.get(i), "00:00");

            if(currentDate.before(buffer) || currentDate.compareTo(buffer) == 0) {
                Log.i(logcat, String.format("Closest date is: %s", dates.get(i)));
                spinner.setSelection(i);
                break;
            }
        }
    }
}
