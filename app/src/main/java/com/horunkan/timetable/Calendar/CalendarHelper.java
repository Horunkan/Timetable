package com.horunkan.timetable.Calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.util.Log;

import com.horunkan.timetable.Lesson.Lesson;
import com.horunkan.timetable.Timetable;
import com.horunkan.timetable.utilities.DateParser;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarHelper {
    private static final String logcat = "CalendarHelper";
    private static final String logcatVal = logcat+"-value";
    private static final int defaultReminderMinutes = 10;
    private static final int firstLessonReminderMinutes = 24 * 60;

    public static void addToCalendar(Timetable activity, String date, Lesson lesson, int lessonIndex) {
        try {
            Calendar startTime = getCalendar(date, lesson.getStartTime());
            Calendar endTime = getCalendar(date, lesson.getEndTime());

            ContentResolver content = activity.getContentResolver();
            ContentValues event = new ContentValues();
            event.put(Events.DTSTART, startTime.getTimeInMillis());
            event.put(Events.DTEND, endTime.getTimeInMillis());
            event.put(Events.TITLE, lesson.getTitle());
            event.put(Events.DESCRIPTION, lesson.getDescription());
            event.put(Events.CALENDAR_ID, SelectCalendar.get());
            event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            event.put(Events.HAS_ALARM, true);
            Uri uri = content.insert(Events.CONTENT_URI, event);

            long eventID = Long.parseLong(uri.getLastPathSegment());
            addAlarm(content, eventID, lessonIndex);
        }
        catch (SecurityException e) {
            Log.e(logcat, "NO PERMISSIONS FOR CALENDAR");
        }
        catch (Exception e) {
            Log.e(logcat, e.getMessage());
        }
    }

    private static void addAlarm(ContentResolver content, long eventID, int lessonIndex) throws SecurityException {
        ContentValues alarm = new ContentValues();
        alarm.put(Reminders.EVENT_ID, eventID);
        alarm.put(Reminders.METHOD, Reminders.METHOD_ALERT);
        if(lessonIndex == 0) alarm.put(Reminders.MINUTES, firstLessonReminderMinutes);
        else alarm.put(Reminders.MINUTES, defaultReminderMinutes);

        content.insert(Reminders.CONTENT_URI,alarm);
    }

    private static Calendar getCalendar(String date, String time) {
        Date dat = DateParser.parse(date, time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dat);
        return cal;
    }

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
