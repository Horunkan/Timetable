package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AsyncDataListener;
import com.maciejkitowski.timetable.utilities.DownloadListener;
import com.maciejkitowski.timetable.utilities.HtmlDownloader;
import com.maciejkitowski.timetable.utilities.InternetConnection;

import java.util.List;

final class HtmlLoader extends Loader implements DownloadListener {
    private static final String logcat = "DateHtmlLoader";
    private final String[] downloadUrls = {"http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php"};

    public HtmlLoader(Context context, AsyncDataListener listener) {
        super(context, listener);
    }

    @Override
    public void start() {
        Log.i(logcat, "Load dates json from urls.");
        startDownloading();
    }

    @Override
    public void onDownloadBegin() {
        Log.i(logcat, "Download started");
        listener.onReceiveBegin();
    }

    @Override
    public void onDownloadSuccess(List<String> data) {
        Log.i(logcat, "Download success");
        for(String str : data) Log.i(logcat + "-val", str);
        setReceivedData(data);
        FileLoader.saveToFile(context, data);
    }

    @Override
    public void onDownloadFailed(String message) {
        Log.w(logcat, String.format("Download failed with error: %s", message));
        listener.onReceiveFail(message);
    }

    private void startDownloading() {
        try {
            if(InternetConnection.isConnected(context)) {
                HtmlDownloader downloader = new HtmlDownloader(this);
                downloader.execute(downloadUrls);
            }
            else displayNoConnectionError();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            listener.onReceiveFail(ex.getLocalizedMessage());
        }
    }

    private void displayNoConnectionError() {
        Log.i(logcat, "Internet not connected, display error.");
        listener.onReceiveFail(context.getString(R.string.error_nointernet));
    }
}
