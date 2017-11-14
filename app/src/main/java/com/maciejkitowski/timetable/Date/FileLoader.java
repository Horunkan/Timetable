package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncDataListener;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class FileLoader extends Loader {
    private static final String logcat = "DateFileLoader";
    static final String filename = "dates.json";

    public FileLoader(Context context, AsyncDataListener listener) {
        super(context, listener);
    }

    @Override
    public void start() {
        Log.i(logcat, "Load dates from file.");
        listener.onReceiveBegin();
        startLoading();
    }

    private void startLoading() {
        Log.i(logcat, "Start loading from file");

        try {
            File file = new File(context.getFilesDir(), filename);
            List<String> loaded = new ArrayList<>();
            loaded.add(FileUtils.readFileToString(file, Charset.defaultCharset()));

            Log.i(logcat, "Loading finished");
            setReceivedData(loaded);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            listener.onReceiveFail(ex.getLocalizedMessage());
        }
    }

    static void saveToFile(Context context, List<String> dates) {
        Log.i(logcat, "Save dates to JSON file");

        try {
            JSONArray array = new JSONArray();
            for(String str : dates) array.put(str);
            Log.i(logcat+"-val", array.toString());

            File file = new File(context.getFilesDir(), filename);
            FileUtils.writeStringToFile(file, array.toString(), Charset.defaultCharset());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static boolean isDatesSavedOnDevice(Context context) {
        Log.i(logcat, String.format("Check if %s exists on device.", filename));
        File file = new File(context.getFilesDir(), filename);

        Log.i(logcat, String.format("File %s found: %s", filename, file.exists()));
        return file.exists();
    }

    static void delete(Context context) {
        Log.i(logcat, String.format("Delete %s file.", filename));
        File file = new File(context.getFilesDir(), filename);
        file.delete();
    }
}
