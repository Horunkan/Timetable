package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

import com.maciejkitowski.timetable.R;
import com.maciejkitowski.timetable.utilities.AlertText;
import com.maciejkitowski.timetable.utilities.IDownloadable;
import com.maciejkitowski.timetable.utilities.LoadingBarToggle;

import java.util.ArrayList;
import java.util.List;

final class HtmlLoader implements ILoader, IDownloadable {
    private static final String logcat = "DateHtmlLoader";
    private final String[] downloadUrls = {"http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php"};

    private Context context;
    private Loader loader;
    private AlertText alert;
    private LoadingBarToggle loadingBar;
    List<String> downloaded;

    public HtmlLoader(Context context, Loader loader) {
        this.context = context;
        this.loader = loader;
        loadingBar = new LoadingBarToggle(context);
        alert = new AlertText(context);
    }

    @Override
    public void load() {
        Log.i(logcat, "Load dates json from urls.");
        startDownloading();
    }

    @Override
    public void downloadStarted() {
        Log.i(logcat, "Download started");
        loadingBar.display();
    }

    @Override
    public void downloadSuccessful(ArrayList<String> content) {
        Log.i(logcat, "Download success");
        for(String str : content) Log.i(logcat + "-val", str);
        loadingBar.hide();
        downloaded = content;
        loader.receivedJson(downloaded);
    }

    @Override
    public void downloadFailed(Exception exception) {
        Log.w(logcat, String.format("Download failed with exception: %s", exception.getLocalizedMessage()));
        loadingBar.hide();
    }

    private void startDownloading() {
        try {
            /*if(InternetConnection.isConnected(context)) {
                HtmlDownloader downloader = new HtmlDownloader(this);
                downloader.execute(downloadUrls).get();
            }
            else displayNoConnectionError();*/
        }
        catch (Exception ex) {
            Log.e(logcat, ex.getMessage());
            downloadFailed(ex);
        }
    }

    private void displayNoConnectionError() {
        Log.i(logcat, "Internet not connected, display error.");
        alert.display(R.string.error_nointernet);
    }
}
