package com.Kitowski.timetable.utilities;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateParser {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date parse(String date, String time) {
		try {
			Date buffer = dateFormat.parse(date + " " + time);
			return buffer;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date parse(String date) {
		try {
			Date buffer = dateOnly.parse(date);
			return buffer;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
