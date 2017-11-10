package com.maciejkitowski.timetable.Schedule.Timestamp;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public final class TimestampCreator {
    private static final String logcat = "TimestampCreator";
    private static final int singleTimestampHeight = 60 * 2;
    private static final int startHour = 7;
    private static final int endHour = 20;

    private static List<Timestamp> hours;

    private TimestampCreator() { }

    public static void create(Activity activity) {
        Log.i(logcat, "Create timestamp");
        if(hours != null) delete();
        hours = new ArrayList<>();

        for(int hr = startHour; hr <= endHour; ++hr) {
            hours.add(new Timestamp(activity, hr, singleTimestampHeight));
        }
    }

    public static void delete() {
        Log.i(logcat, "Delete timestamp");
        for(Timestamp hour : hours) hour.delete();
    }
}
