package com.horunkan.timetable.Calendar;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.horunkan.timetable.Timetable;

public class CalendarHelper {
    private static final String logcat = "CalendarHelper";
    private static final String logcatVal = logcat+"-value";

    public static Cursor getAvailableCalendars(Timetable activity) {
        Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/calendars");
        String[] fields = {CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME};

        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(CALENDAR_URI, fields, null, null, null);

        Log.i(logcatVal, "Found calendars: ");
        while (cursor.moveToNext()) Log.i(logcatVal, String.format("ID: %s, Name: %s", cursor.getString(0), cursor.getString(1)));
        Log.i(logcat, String.format("Total calendars found: %d", cursor.getCount()));
        cursor.moveToFirst();

        return cursor;
    }
}
