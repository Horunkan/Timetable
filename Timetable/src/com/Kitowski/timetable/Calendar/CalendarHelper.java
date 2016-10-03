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
import android.net.Uri;
import android.provider.CalendarContract.Events;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class CalendarHelper {
	public static void addToCalendar(Timetable timetable, String date, Lesson lesson) {
		try {
			Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + lesson.getRawTime()[0]);
			Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date + " " + lesson.getRawTime()[1]);
			Calendar beginTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			beginTime.setTime(start);
			endTime.setTime(end);
			
			ContentResolver cr = timetable.getContentResolver();
			ContentValues values = new ContentValues();
			
			values.put(Events.DTSTART, beginTime.getTimeInMillis());
			values.put(Events.DTEND, endTime.getTimeInMillis());
			values.put(Events.TITLE, lesson.getLessonName());
			values.put(Events.DESCRIPTION, lesson.getDetails());
			values.put(Events.CALENDAR_ID, 1);
			values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
			Uri uri = cr.insert(Events.CONTENT_URI, values);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
