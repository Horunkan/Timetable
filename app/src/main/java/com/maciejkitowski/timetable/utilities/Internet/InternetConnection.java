package com.maciejkitowski.timetable.utilities.Internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public final class InternetConnection {
    private static final String logcat = "InternetConnection";

    private InternetConnection() {}

    public static boolean isConnected(Context context) {
        Log.i(logcat, "Check internet connection");

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        boolean connected = netInfo != null && netInfo.isAvailable() && netInfo.isConnected();

        Log.i(logcat, String.format("Internet connected: %s", connected));
        return connected;
    }
}
