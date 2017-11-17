package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import android.os.AsyncTask;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;

import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class AsyncHtmlDownloader extends AsyncTask<String, Void, Void> {
    private static final String logcat = "AsyncHtmlDownloader";
    private List<AsyncDataSourceListener> listeners;
    private List<String> downloaded;
    private boolean downloadFailed = false;
    private Exception downloadFailException = null;

    public AsyncHtmlDownloader() {
        Log.i(logcat, "Initialize");
        listeners = new LinkedList<>();
    }

    public void addListener(AsyncDataSourceListener listener) {
        Log.i(logcat, "Add new listener");
        listeners.add(listener);
    }

    @Override
    protected void onPreExecute() {
        Log.i(logcat, "Start data receiving");
        callOnReceiveBegin();
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
            callOnReceiveFail(downloadFailException.getLocalizedMessage());
        }
        else {
            Log.i(logcat, "Download success.");
            callOnReceiveSuccess(downloaded);
        }

        super.onPostExecute(aVoid);
    }

    private void callOnReceiveBegin() {
        Log.i(logcat, "Call all listeners (onReceiveBegin)");
        for(AsyncDataSourceListener list : listeners) list.onLoadBegin();
    }

    private void callOnReceiveSuccess(List<String> data) {
        Log.i(logcat, "Call all listeners (onReceiveSuccess)");
        for(AsyncDataSourceListener list : listeners) list.onLoadSuccess(data);
    }

    private void callOnReceiveFail(String message) {
        Log.i(logcat, "Call all listeners (onReceiveFail)");
        for(AsyncDataSourceListener list : listeners) list.onLoadFail(message);
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
