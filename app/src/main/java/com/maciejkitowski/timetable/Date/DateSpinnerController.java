package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncFileLoader;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncHtmlDownloader;
import com.maciejkitowski.timetable.utilities.FileUtil;
import com.maciejkitowski.timetable.utilities.InternetConnection;
import com.maciejkitowski.timetable.utilities.UserInterface.AlertText;
import com.maciejkitowski.timetable.utilities.UserInterface.LoadingBar;
import com.maciejkitowski.timetable.utilities.UserInterface.RefreshListener;

import org.joda.time.LocalDate;
import org.json.JSONArray;

import java.util.LinkedList;
import java.util.List;

public class DateSpinnerController implements AdapterView.OnItemSelectedListener, AsyncDataListener, RefreshListener {
    private static final String logcat = "DateSpinner";
    private final String filename = "dates.json";
    private final String url = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";

    private Spinner spinner;
    private Activity activity;
    private boolean loadingFromUrl = false;
    private List<String> dates = new LinkedList<>();
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
        LoadingBar.display();
    }

    @Override
    public void onReceiveSuccess(List<String> data) {
        Log.i(logcat, "Receive success");
        if(loadingFromUrl) FileUtil.saveToFile(activity, filename, data);
        LoadingBar.hide();
        formatData(data);
    }

    @Override
    public void onReceiveFail(String message) {
        Log.w(logcat, String.format("Receive fail: %s", message));
        if(isFileOnDevice()) loadFromFile();
        else AlertText.display(message);
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
        dates.clear();
        AlertText.hide();
        loadFromUrl();
    }

    public void start() {
        Log.i(logcat, "Start loading");

        if(isFileOnDevice()) loadFromFile();
        else loadFromUrl();
    }

    private void loadFromFile() {
        Log.i(logcat, "Load dates from file");
        loadingFromUrl = false;

        AsyncFileLoader loader = new AsyncFileLoader(activity);
        loader.addListener(this);
        loader.execute(filename);
    }

    private void loadFromUrl() {
        Log.i(logcat, "Load dates from url");
        loadingFromUrl = true;

        if(InternetConnection.isConnected(activity)) {
            AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
            downloader.addListener(this);
            downloader.execute(url);
        }
        else {
            if(isFileOnDevice()) {
                Toast.makeText(activity, R.string.error_nointernet_foundfile, Toast.LENGTH_LONG).show();
                loadFromFile();
            }
            else AlertText.display(activity.getString(R.string.error_nointernet));
        }
    }

    private void formatData(List<String> json) {
        Log.i(logcat, "Format received json");

        for(String js : json) {
            try {
                JSONArray array = new JSONArray(js);
                Log.i(logcat+"-val", array.toString());

                for(int i = 0; i < array.length(); ++i) {
                    Log.i(logcat+"-val", String.format("Add %s to dates array", array.getString(i)));
                    dates.add(array.getString(i));
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                AlertText.display(ex.getLocalizedMessage());
                break;
            }
        }

        setSpinnerValues();
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

    private boolean isFileOnDevice() { return FileUtil.isSavedOnDevice(activity, filename); }
}
