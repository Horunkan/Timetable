package com.maciejkitowski.timetable.utilities;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//TODO Not tested yet, prepare Unit tests
public final class HtmlDownloader extends AsyncTask<String, Integer, String[]> {
    private static final String logcat = "HTML-downloader";

    @Override
    protected String[] doInBackground(String... source) {
        Log.i(logcat, String.format("Start downloading %d HTML pages.", source.length));
        ArrayList<String> downloaded = new ArrayList<>();

        for(String src : source) {
            downloaded.add(loadPage(src));
        }

        if(downloaded.size() == source.length) Log.i(logcat, "Downloading pages finished");
        else Log.w(logcat, String.format("Downloaded %d pages, should: %d", downloaded.size(), source.length));

        return (String[])downloaded.toArray();
    }

    private String loadPage(String source) {
        Log.i(logcat, String.format("Start download HTML page from: %s", source));
        InputStreamReader reader;

        try {
            URL url = new URL(source);
            reader = new InputStreamReader(url.openStream());
            String loaded = IOUtils.toString(reader);

            Log.i(logcat, String.format("Finished loading page: %s", source));
            Log.i(logcat + "-val", loaded);
            return loaded;
        }
        catch (Exception ex) {
            Log.e(logcat, ex.getMessage());

            return null;
        }
    }
}
