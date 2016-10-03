package com.Kitowski.timetable.Calendar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.lessons.Lesson;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class CalendarHelper {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static boolean addToCalendar(Timetable timetable, String date, Lesson lesson, boolean withAlarm) {
		try {
			Date start = dateFormat.parse(date + " " + lesson.getRawTime()[0]);
			Date end = dateFormat.parse(date + " " + lesson.getRawTime()[1]);
			Calendar beginTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			beginTime.setTime(start);
			endTime.setTime(end);
			
			ContentResolver content = timetable.getContentResolver();
			ContentValues event = new ContentValues();
			
			event.put(Events.DTSTART, beginTime.getTimeInMillis());
			event.put(Events.DTEND, endTime.getTimeInMillis());
			event.put(Events.TITLE, lesson.getLessonName());
			event.put(Events.DESCRIPTION, lesson.getDetails());
			event.put(Events.CALENDAR_ID, 1);
			event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
			event.put(Events.HAS_ALARM, withAlarm);
			Uri uri = content.insert(Events.CONTENT_URI, event);
			
			if(withAlarm) {
				long eventID = Long.parseLong(uri.getLastPathSegment());
				addAlarm(content, eventID);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static void addAlarm(ContentResolver content, long eventID) {
		ContentValues alarm = new ContentValues();
	    alarm.put(Reminders.EVENT_ID, eventID);
	    alarm.put(Reminders.METHOD, Reminders.METHOD_ALERT);
	    alarm.put(Reminders.MINUTES, 1440);
	    content.insert(Reminders.CONTENT_URI,alarm);
	}
}
