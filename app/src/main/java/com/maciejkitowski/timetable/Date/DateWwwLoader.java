package com.maciejkitowski.timetable.Date;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.HtmlDownloader;
import com.maciejkitowski.timetable.utilities.IDownloadable;
import com.maciejkitowski.timetable.utilities.InternetConnection;
import com.maciejkitowski.timetable.utilities.LoadingBarToggle;

import java.util.ArrayList;

final class DateWwwLoader implements ILoader, IDownloadable {
    private static final String logcat = "DateWwwLoader";
    private final String[] downloadUrls = {"http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php"};

    private Context context;
    private ArrayList<String> jsonList;
    private TextView alertTextView;
    private LoadingBarToggle loadingBar;

    public DateWwwLoader(Context context) {
        this.context = context;
        loadingBar = new LoadingBarToggle(context);
        alertTextView = (TextView)((Activity)context).findViewById(R.id.AlertText);
    }

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
        loadingBar.display();
    }

    @Override
    public void downloadSuccessful(ArrayList<String> content) {
        Log.i(logcat, "Download success");
        loadingBar.hide();
        jsonList = content;
        for(String str : content) Log.i(logcat + "-val", str);
    }

    @Override
    public void downloadFailed(Exception exception) {
        Log.w(logcat, String.format("Download failed with exception: %s", exception.getLocalizedMessage()));
        loadingBar.hide();
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
        alertTextView.setVisibility(View.VISIBLE);
        alertTextView.setText(context.getString(R.string.error_nointernet));
    }
}
