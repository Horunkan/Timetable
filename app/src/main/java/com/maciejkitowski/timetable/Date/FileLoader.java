package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

final class FileLoader implements ILoader {
    private static final String logcat = "DateFileLoader";
    static final String filename = "dates.json";

    @Override
    public void load() {
        Log.i(logcat, "Load dates from file.");
        Log.e(logcat, "Getting dates from file not implemented yet.");
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
}
