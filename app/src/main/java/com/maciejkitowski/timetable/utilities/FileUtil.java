package com.maciejkitowski.timetable.utilities;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

public class FileUtil {
    private static final String logcat = "FileUtil";

    public static boolean isSavedOnDevice(Context context, String name) {
        Log.i(logcat, String.format("Check if %s exists on device.", name));
        File file = new File(context.getFilesDir(), name);

        boolean exists = file.exists();
        if(exists) Log.i(logcat, String.format("File %s exists on device.", name));
        else if(exists) Log.w(logcat, String.format("File %s not exists on device.", name));

        return exists;
    }

    public static void saveJsonArray(Context context, String name, List<String> data) {
        Log.i(logcat, String.format("Save %d JSON array to %s file.", data.size(), name));

        try {
            JSONArray array = new JSONArray();
            for(String str : data) array.put(str);
            Log.i(logcat+"-val", array.toString());

            File file = new File(context.getFilesDir(), name);
            FileUtils.writeStringToFile(file, array.toString(), Charset.defaultCharset());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
