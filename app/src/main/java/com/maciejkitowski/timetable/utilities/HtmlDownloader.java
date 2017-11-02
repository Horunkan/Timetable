package com.maciejkitowski.timetable.utilities;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public final class HtmlDownloader extends AsyncTask<String, Integer, String[]> {
    private static final String logcat = "HTML-downloader";

    private IDownloadable callSource = null;
    private boolean downloadFailed = false;
    private Exception failException = null;

    public HtmlDownloader() { }

    public HtmlDownloader(IDownloadable source) {
        callSource = source;
    }

    @Override
    protected void onPreExecute() {
        Log.i(logcat, "Start download.");
        if(callSource != null) callSource.downloadStarted();
    }

    @Override
    protected String[] doInBackground(String... source) {
        Log.i(logcat, String.format("Start downloading %d HTML pages.", source.length));
        ArrayList<String> downloaded = new ArrayList<>();

        for(String src : source) {
            downloaded.add(loadPage(src));
        }

        if(downloaded.size() == source.length) Log.i(logcat, "Downloading pages finished");
        else Log.w(logcat, String.format("Downloaded %d pages, should: %d", downloaded.size(), source.length));

        return downloaded.toArray(new String[downloaded.size()]);
    }

    @Override
    protected void onPostExecute(String[] strings) {
        if(downloadFailed) {
            Log.w(logcat, "Download failed!");
            if(callSource != null) callSource.downloadFailed(failException);
        }
        else {
            Log.i(logcat, "Download finished!");
            if(callSource != null) callSource.downloadSuccessful();
        }

        super.onPostExecute(strings);
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
            downloadFailed = true;
            failException = ex;

            return null;
        }
    }
}
