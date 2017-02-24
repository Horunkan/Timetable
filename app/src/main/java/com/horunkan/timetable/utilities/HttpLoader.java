package com.horunkan.timetable.utilities;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class HttpLoader extends AsyncTask<String, Void, ArrayList<String>> {
    private static final String logcat = "Http-loader";
    private static final String logcatVal = logcat + "-value";

    ArrayList<String> content;

    @Override
    protected ArrayList<String> doInBackground(String... http) {
        content = new ArrayList<>();

        for(String source : http) content.addAll(loadWebsite(source));

        Log.i(logcat, String.format("Total loaded %d lines.", content.size()));
        return content;
    }

    private ArrayList<String> loadWebsite(String source) {
        Log.i(logcat, String.format("Started load from: %s", source));

        ArrayList<String> buffer = new ArrayList<>();
        URL url;
        InputStream input;
        BufferedReader reader;

        try {
            url = new URL(source);
            input = url.openStream();
            reader = new BufferedReader(new InputStreamReader(input));

            String line = null;
            while((line = reader.readLine()) != null) {
                Log.i(logcatVal, line);
                buffer.add(line);
            }

            input.close();
            reader.close();
        }
        catch(Exception e) {
            Log.e(logcat, e.getMessage());
        }

        Log.i(logcat, String.format("Finished load from: %s\nTotal lines: %d", source, buffer.size()));
        return buffer;
    }
}
