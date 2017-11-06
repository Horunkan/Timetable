package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AlertText;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public final class DateLoader {
    private static final String logcat = "DateLoader";

    private Context context;
    private ILoader loader;
    private List<String> dates;

    public DateLoader(Context context) {
        Log.i(logcat, "Initialize date loader");
        this.context = context;

        if(DateFileLoader.isDatesSavedOnDevice(context)) loader = new DateFileLoader();
        else loader = new DateWwwLoader(context, this);

        loader.load();
    }

    public void receivedJson(List<String> json) {
        Log.i(logcat, String.format("Received json array with %d size", json.size()));
        dates = new ArrayList<>();

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
                Log.e(logcat, ex.getMessage());
                AlertText alert = new AlertText(context);
                alert.display(String.format(context.getString(R.string.error_unknown), ex.getLocalizedMessage()));
            }
        }
    }
}
