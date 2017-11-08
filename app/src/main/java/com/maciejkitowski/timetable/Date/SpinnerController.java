package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AlertText;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;

import java.util.List;

public class SpinnerController implements AdapterView.OnItemClickListener, AsyncDataListener {
    private static final String logcat = "SpinnerController";
    private Spinner spinner;
    private Activity activity;

    public SpinnerController(Activity activity) {
        Log.i(logcat, "Initialize spinner controller");
        this.activity = activity;
        spinner = (Spinner)activity.findViewById(R.id.Date);

        Loader loader = new Loader(activity, this);
    }

    @Override
    public void onReceiveBegin() {
        Log.i(logcat, "Receive begin");
        AlertText.display("AA");
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        AlertText.display("XD");
        setSpinnerValues(data);
    }

    @Override
    public void onReceiveFail(String message) {
        Log.i(logcat, String.format("Receive fail: %s", message));
        AlertText.display(message);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(logcat, String.format("Selected date: %s", spinner.getSelectedItem()));
    }

    private void setSpinnerValues(List<String> values) {
        Log.i(logcat, String.format("Set %d values to spinner", values.size()));
        for(String str : values) Log.i(logcat+"-val", str);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(this);
    }
}
