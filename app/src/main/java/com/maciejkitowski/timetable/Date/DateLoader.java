package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.util.Log;

public final class DateLoader {
    private static final String logcat = "DateLoader";

    private ILoader loader;

    public DateLoader(Context context) {
        Log.i(logcat, "Initialize date loader");

        if(DateFileLoader.isDatesSavedOnDevice(context)) loader = new DateFileLoader();
        else loader = new DateWwwLoader();
    }
}
