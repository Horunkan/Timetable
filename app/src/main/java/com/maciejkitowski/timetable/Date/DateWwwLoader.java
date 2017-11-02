package com.maciejkitowski.timetable.Date;

import android.util.Log;

import com.maciejkitowski.timetable.utilities.IDownloadable;

import java.util.ArrayList;

final class DateWwwLoader implements ILoader, IDownloadable {
    private static final String logcat = "DateWwwLoader";
    private final String downloadUrl = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";

    @Override
    public void load() {
        Log.i(logcat, "Load dates from url.");

        /*try {
            HtmlDownloader downloader = new HtmlDownloader(this);
            downloader.execute(downloadUrl);


            Log.i(logcat, "Download finished.");
            Log.i(logcat + "-val", json[0]);
        }
        catch (Exception ex) {
            Log.e(logcat, ex.getMessage());
        }*/

        //Log.e(logcat, "Getting dates from url not implemented yet.");
    }

    @Override
    public void downloadStarted() {
        Log.i(logcat, "Download started");
    }

    @Override
    public void downloadSuccessful(ArrayList<String> content) {
        Log.i(logcat, "Download success");
    }

    @Override
    public void downloadFailed(Exception exception) {
        Log.i(logcat, String.format("Download failed with exception: %s", exception.getLocalizedMessage()));
    }
}
