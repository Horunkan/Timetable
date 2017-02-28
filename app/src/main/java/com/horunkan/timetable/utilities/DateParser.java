package com.horunkan.timetable.utilities;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static final String logcat = "DateParser";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static Date parse(String date, String time) {
        try {
            Date buffer = dateFormat.parse(date + " " + time);
            Log.i(logcat, String.format("Parsed date: %s", dateFormat.format(buffer)));
            return buffer;
        }
        catch (Exception e) {
            Log.e(logcat, String.format("Failed to parse: %s, %s - $s", date, time, e.getMessage()));
            return null;
        }
    }

    public static Date parse(Date date, String time) {
        return parse(new SimpleDateFormat("yyyy-MM-dd").format(date), time);
    }
}
