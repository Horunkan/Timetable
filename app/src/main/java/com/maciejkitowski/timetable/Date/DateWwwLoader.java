package com.maciejkitowski.timetable.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.HtmlDownloader;
import com.maciejkitowski.timetable.utilities.IDownloadable;
import com.maciejkitowski.timetable.utilities.InternetConnection;

import java.util.ArrayList;

final class DateWwwLoader implements ILoader, IDownloadable {
    private static final String logcat = "DateWwwLoader";
    private final String[] downloadUrls = {"http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php"};

    private ArrayList<String> jsonList;
    private Context context;

    @Override
    public void load(Context context) {
        Log.i(logcat, "Load dates json from urls.");
        this.context = context;
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
            if(InternetConnection.isConnected(context)) {
                HtmlDownloader downloader = new HtmlDownloader(this);
                downloader.execute(downloadUrls).get();
            }
            else displayNoConnectionError();
        }
        catch (Exception ex) {
            Log.e(logcat, ex.getMessage());
            downloadFailed(ex);
        }
    }

    private void displayNoConnectionError() {
        Log.i(logcat, "Internet not connected, display error.");
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(context.getString(R.string.error_nointernet));
        dialog.setCancelable(true);

        dialog.show();
    }
}
