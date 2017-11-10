package com.maciejkitowski.timetable.utilities.Internet;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class HtmlDownloader extends AsyncTask<String, Void, Void> {
    private static final String logcat = "HTML-downloader";

    private final DownloadListener listener;
    private List<String> downloaded;
    private boolean downloadFailed = false;
    private Exception downloadFailException = null;

    public HtmlDownloader(DownloadListener listener) {
        Log.i(logcat, "Set Data listener");
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        Log.i(logcat, "Start data receiving");
        listener.onDownloadBegin();
        downloaded = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... strings) {
        Log.i(logcat, String.format("Start downloading %d HTML pages.", strings.length));
        for(String src : strings) downloaded.add(getPage(src));
        Log.i(logcat, String.format("Downloading %s pages finished.", downloaded.size()));

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(downloadFailed) {
            Log.w(logcat, "Download failed.");
            listener.onDownloadFailed(downloadFailException.getLocalizedMessage());
        }
        else {
            Log.i(logcat, "Download success.");
            listener.onDownloadSuccess(downloaded);
        }

        super.onPostExecute(aVoid);
    }

    private String getPage(String source) {
        Log.i(logcat, String.format("Start download HTML page from: %s", source));
        InputStreamReader reader;

        try {
            URL url = new URL(source);
            reader = new InputStreamReader(url.openStream());
            String loaded = IOUtils.toString(reader);
            reader.close();

            Log.i(logcat, String.format("Finished loading page: %s", source));
            Log.i(logcat + "-val", loaded);
            return loaded;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            downloadFailed = true;
            downloadFailException = ex;

            return null;
        }
    }
}