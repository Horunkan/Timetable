package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.AsyncDataSource.AsyncHtmlDownloader;
import com.maciejkitowski.timetable.utilities.FileUtil;
import com.maciejkitowski.timetable.utilities.InternetConnection;

import java.util.List;

final class HtmlLoader extends Loader {
    private static final String logcat = "HtmlLoader";
    private final String[] downloadUrls = {"http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php"};

    public HtmlLoader(Context context) {
        super(context);
        Log.i(logcat, "Initialize");
    }

    @Override
    void start() {
        Log.i(logcat, "Load dates json from urls.");
        startDownloading();
    }

    @Override
    public void onLoadBegin() {
        Log.i(logcat, "Download started");
        for(AsyncDataListener listener : listeners) listener.onReceiveBegin();
    }

    @Override
    public void onLoadSuccess(List<String> data) {
        Log.i(logcat, "Download success");
        for(String dat : data) Log.i(logcat+"-val", dat);
        formatData(data);
        FileUtil.saveJsonArray(context, FileLoader.filename, dates);
    }

    @Override
    public void onLoadFail(String message) {
        Log.w(logcat, String.format("Download failed: %s", message));
        for(AsyncDataListener listener : listeners) listener.onReceiveFail(message);
    }

    private void startDownloading() {
        try {
            if(InternetConnection.isConnected(context)) {
                AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
                downloader.addListener(this);
                downloader.execute(downloadUrls);
            }
            else displayNoConnectionError();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            for(AsyncDataListener listener : listeners) listener.onReceiveFail(ex.getLocalizedMessage());
        }
    }

    private void displayNoConnectionError() {
        Log.i(logcat, "Internet not connected, display error.");
        for(AsyncDataListener listener : listeners) listener.onReceiveFail(context.getString(R.string.error_nointernet));
    }
}
