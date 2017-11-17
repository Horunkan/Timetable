package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AsyncFileLoader extends AsyncTask<String, Void, Void> {
    private static final String logcat = "AsyncFileLoader";
    private Context context;
    private List<AsyncDataSourceListener> listeners;
    private List<String> loaded;
    private boolean loadFailed = false;
    private Exception failException = null;

    public AsyncFileLoader(Context context) {
        Log.i(logcat, "Initialize");
        this.context = context;
        this.listeners = new LinkedList<>();
    }

    public void addListener(AsyncDataSourceListener listener) {
        Log.i(logcat, "Add new listener");
        listeners.add(listener);
    }

    @Override
    protected void onPreExecute() {
        Log.i(logcat, "Start data receiving");
        loaded = new ArrayList<>();
        for(AsyncDataSourceListener list : listeners) list.onLoadBegin();
    }

    @Override
    protected Void doInBackground(String... files) {
        Log.i(logcat, String.format("Start loading %d files.", files.length));
        for(String file : files) loaded.add(getFile(file));
        Log.i(logcat, String.format("Loading %d files finished.", loaded.size()));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(loadFailed) {
            Log.w(logcat, "Load failed.");
            String message = failException.getLocalizedMessage();
            for(AsyncDataSourceListener list : listeners) list.onLoadFail(message);
        }
        else {
            Log.i(logcat, "Download success.");
            for(AsyncDataSourceListener list : listeners) list.onLoadSuccess(loaded);
        }
    }
    
    private String getFile(String filename) {
        Log.i(logcat, "Load from file: " + filename);

        try {
            File file = new File(context.getFilesDir(), filename);
            return FileUtils.readFileToString(file, Charset.defaultCharset());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            failException = ex;
            loadFailed = true;
            return null;
        }
    }
}
