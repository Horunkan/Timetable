package com.Kitowski.timetable.Calendar;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.Kitowski.timetable.lessons.Lesson;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;

public class CalendarHelper {
	public static boolean addToCalendar(Activity act, String date, Lesson lesson, boolean withAlarm) {
		try {
			Date start = DateParser.parse(date, lesson.getStartTime());
			Date end = DateParser.parse(date, lesson.getEndTime());
			Calendar beginTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			beginTime.setTime(start);
			endTime.setTime(end);
			
			ContentResolver content = act.getContentResolver();
			ContentValues event = new ContentValues();
			
			event.put(Events.DTSTART, beginTime.getTimeInMillis());
			event.put(Events.DTEND, endTime.getTimeInMillis());
			event.put(Events.TITLE, lesson.getTitle());
			event.put(Events.DESCRIPTION, lesson.getDescription());
			event.put(Events.CALENDAR_ID, 1);
			event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
			event.put(Events.HAS_ALARM, withAlarm);
			Uri uri = content.insert(Events.CONTENT_URI, event);
			
			if(withAlarm) {
				long eventID = Long.parseLong(uri.getLastPathSegment());
				addAlarm(content, eventID);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static void addAlarm(ContentResolver content, long eventID) {
		ContentValues alarm = new ContentValues();
	    alarm.put(Reminders.EVENT_ID, eventID);
	    alarm.put(Reminders.METHOD, Reminders.METHOD_ALERT);
	    alarm.put(Reminders.MINUTES, 1440);
	    content.insert(Reminders.CONTENT_URI,alarm);
	}
	
	public static boolean anyEventExists(Activity act, String date) {
		try {
			Date start = DateParser.parse(date, "00:00");
			Date end = DateParser.parse(date, "23:59");
			Calendar beginTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			beginTime.setTime(start);
			endTime.setTime(end);
			
			ContentResolver content = act.getContentResolver();
			String queryProjection[] = {Events._ID};
			String querySelection = "(deleted != 1 and dtstart>" + beginTime.getTimeInMillis() + " and dtend <" + endTime.getTimeInMillis() + ")";
			Cursor cursor = content.query(Events.CONTENT_URI, queryProjection, querySelection, null, null);
			
			int count = cursor.getCount();
			cursor.close();
			
			if(count > 0) return true;
			else return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<String> getAllEvents(Activity act, String date) {
		ArrayList<String> buffer = new ArrayList<String>();

		try {
			Date start = DateParser.parse(date, "00:00");
			Date end = DateParser.parse(date, "23:59");
			Calendar beginTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			beginTime.setTime(start);
			endTime.setTime(end);
			
			ContentResolver content = act.getContentResolver();
			String queryProjection[] = {Events._ID, Events.TITLE};
			String querySelection = "(deleted != 1 and dtstart>" + beginTime.getTimeInMillis() + " and dtend <" + endTime.getTimeInMillis() + ")";
			Cursor cursor = content.query(Events.CONTENT_URI, queryProjection, querySelection, null, null);
			
			while(cursor.moveToNext()) buffer.add(cursor.getString(0) + "," + cursor.getString(1));
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	public static void deleteEvent(Activity act, long id) {
		ContentResolver content = act.getContentResolver();		
		Uri toDelete = ContentUris.withAppendedId(Events.CONTENT_URI, id);
		content.delete(toDelete, null, null);
	}
}
