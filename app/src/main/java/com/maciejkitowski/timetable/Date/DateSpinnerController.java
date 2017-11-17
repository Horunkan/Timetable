package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.FileUtil;
import com.maciejkitowski.timetable.utilities.UserInterface.AlertText;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.UserInterface.RefreshListener;

import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

public class DateSpinnerController implements AdapterView.OnItemSelectedListener, AsyncDataListener, RefreshListener {
    private static final String logcat = "DateSpinner";
    private enum sourceType {FILE, HTML}
    private Spinner spinner;
    private Activity activity;
    private List<String> dates;
    private List<DateChangedListener> listeners = new LinkedList<>();

    public DateSpinnerController(Activity activity) {
        Log.i(logcat, "Initialize spinner controller");
        this.activity = activity;
        spinner = activity.findViewById(R.id.Date);
    }

    public void addListener(DateChangedListener listener) {
        Log.i(logcat, "Add new listener");
        listeners.add(listener);
    }

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        dates = data;
        setSpinnerValues();
    }

    @Override
    public void onReceiveFail(String message) {
        Log.i(logcat, String.format("Receive fail: %s", message));
        if(FileUtil.isSavedOnDevice(activity, FileLoader.filename)) {
            Toast.makeText(activity, R.string.error_nointernet_foundfile, Toast.LENGTH_LONG).show();
            startLoading(sourceType.FILE);
        }
        else {
            AlertText.display(message);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected date: %s", spinner.getSelectedItem()));
        for(DateChangedListener listener : listeners) listener.onDateChanged(String.valueOf(spinner.getSelectedItem()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRefresh() {
        Log.i(logcat, "Refresh dates");
        startLoading(sourceType.HTML);
    }

    public void start() {
        Log.i(logcat, "Start loading");
        sourceType type;
        if(FileUtil.isSavedOnDevice(activity, FileLoader.filename)) type = sourceType.FILE;
        else type = sourceType.HTML;

        startLoading(type);
    }

    private void setSpinnerValues() {
        Log.i(logcat, String.format("Set %d values to spinner", dates.size()));
        for(String str : dates) Log.i(logcat+"-val", str);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        setClosestDate();
        spinner.setOnItemSelectedListener(this);
    }

    private void startLoading(sourceType type) {
        Log.i(logcat, "Start loading");
        Loader loader;

        if(type == sourceType.FILE) loader = new FileLoader(activity);
        else loader = new HtmlLoader();

        loader.addListener(this);
        loader.start();
    }

    private void setClosestDate() {
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
}
