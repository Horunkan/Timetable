package com.Kitowski.timetable.Calendar;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.lessons.Lesson;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
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
			
			if(!isEventExists(timetable, lesson.getLessonName(), lesson.getDetails(), beginTime, endTime)) {
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
			}
			else {
				deleteEvent(timetable, lesson.getLessonName(), lesson.getDetails(), beginTime, endTime);
				Toast.makeText(timetable, R.string.toast_removeduplicate, Toast.LENGTH_SHORT).show();
				addToCalendar(timetable, date, lesson, withAlarm);
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
	
	
	private static boolean isEventExists(Timetable timetable, String title, String description, Calendar beginTime, Calendar endTime) {
		ContentResolver content = timetable.getContentResolver();
		String queryProjection[] = {"_id"};
		String querySelection = "(deleted != 1 and dtstart is " + beginTime.getTimeInMillis() + " and dtend is " + endTime.getTimeInMillis() + " and title is \"" + title + "\" and description is \"" + description + "\")";
		Cursor cursor = content.query(Events.CONTENT_URI, queryProjection, querySelection, null, null);
		
		int count = cursor.getCount();
		cursor.close();
		
		if(count > 0) return true;
		else return false;
	}
	
	private static void deleteEvent(Timetable timetable, String title, String description, Calendar beginTime, Calendar endTime) {
		ContentResolver content = timetable.getContentResolver();
		String queryProjection[] = {"_id"};
		String querySelection = "(deleted != 1 and dtstart is " + beginTime.getTimeInMillis() + " and dtend is " + endTime.getTimeInMillis() + " and title is \"" + title + "\" and description is \"" + description + "\")";
		Cursor cursor = content.query(Events.CONTENT_URI, queryProjection, querySelection, null, null);
		
		while(cursor.moveToNext()) {
			Uri eventUri = ContentUris.withAppendedId(Events.CONTENT_URI, cursor.getLong(0));
			content.delete(eventUri, null, null);
		}
		
		cursor.close();
	}
}
