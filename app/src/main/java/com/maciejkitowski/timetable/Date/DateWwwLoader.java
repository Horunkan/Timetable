package com.maciejkitowski.timetable.Date;

import android.util.Log;

import com.maciejkitowski.timetable.utilities.HtmlDownloader;
import com.maciejkitowski.timetable.utilities.IDownloadable;

import java.util.ArrayList;

final class DateWwwLoader implements ILoader, IDownloadable {
    private static final String logcat = "DateWwwLoader";
    private final String[] downloadUrls = {"http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php"};

    private ArrayList<String> jsonList;

    @Override
    public void load() {
        Log.i(logcat, "Load dates json from urls.");
        startDownloading();
    }

    @Override
    public ArrayList<String> getJson() {
        return jsonList;
    }

    @Override
    public void downloadStarted() {
        Log.i(logcat, "Download started");
    }

    @Override
    public void downloadSuccessful(ArrayList<String> content) {
        Log.i(logcat, "Download success");
        jsonList = content;
        for(String str : content) Log.i(logcat + "-val", str);
    }

    @Override
    public void downloadFailed(Exception exception) {
        Log.w(logcat, String.format("Download failed with exception: %s", exception.getLocalizedMessage()));
    }

    private void startDownloading() {
        try {
            HtmlDownloader downloader = new HtmlDownloader(this);
            downloader.execute(downloadUrls).get();
        }
        catch (Exception ex) {
            Log.e(logcat, ex.getMessage());
            downloadFailed(ex);
        }
    }
}
