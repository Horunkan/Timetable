package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.maciejkitowski.timetable.R;

import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

final class DateSpinnerController implements AdapterView.OnItemSelectedListener {
    private static final String logcat = "DateSpinner";

    private Spinner spinner;
    private Activity activity;
    private List<DateChangedListener> listeners = new LinkedList<>();

    public DateSpinnerController(Activity activity) {
        Log.i(logcat, "Initialize spinner controller");
        this.activity = activity;
        spinner = activity.findViewById(R.id.Date);
    }

    public void updateValues(List<String> dates) {
        Log.i(logcat, "Update spinner values - Received values count: " + dates.size());
        for(String str : dates) Log.i(logcat+"-val", str);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setClosestDate(dates);
        callDateChangedListeners();
        spinner.setOnItemSelectedListener(this);
    }

    public void addListener(DateChangedListener listener) {
        Log.i(logcat, "Add new listener");
        listeners.add(listener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected date: %s", spinner.getSelectedItem()));
        callDateChangedListeners();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setClosestDate(List<String> dates) {
        Log.i(logcat, "Set spinner initial item based on current date");
        LocalDate date = LocalDate.now();

        for(int i = 0; i < dates.size(); ++i) {
            LocalDate buffer = LocalDate.parse(dates.get(i));
            if(date.isEqual(buffer) || date.isBefore(buffer)) {
                Log.i(logcat, String.format("Set spinner date: %s", buffer));
                spinner.setSelection(i, true);

                break;
            }
        }
    }

    private void callDateChangedListeners() {
        Log.i(logcat, "Call all listeners with selected date (DateChanged)");
        for(DateChangedListener listener : listeners) listener.onDateChanged(String.valueOf(spinner.getSelectedItem()));
    }
}
