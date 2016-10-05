package com.Kitowski.timetable.date;

import java.util.ArrayList;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.utilities.HttpReader;

import android.util.Log;

public class DateLoader {
	private final static String logcatTAG = "Date loader";

	private ArrayList<String> toConvert;
	private ArrayList<String> dateList;
	private final String monthList[];
	
	public DateLoader(Timetable timetable) {
		HttpReader http = (HttpReader) new HttpReader().execute("https://inf.ug.edu.pl/terminy-zjazdow-semestr-zimowy-201617", "table");
		toConvert = new ArrayList<String>();
		dateList = new ArrayList<String>();
		monthList = timetable.getResources().getStringArray(R.array.month);
		
		if(loadFromHttp(http)) convertDate();
	}
	
	@SuppressWarnings("finally")
	private boolean loadFromHttp(HttpReader http) {
		try { toConvert = http.get(); }
		catch(Exception e) {
			Log.e(logcatTAG, "Failed to load");
			return false;
		}
		finally {
			if(toConvert.size() == 0) return false;
			else return true;
		}
	}
	
	public ArrayList<String> getList() { return dateList; }
	
	private void convertDate() {
		String buffer = "";
		
		for(String str : toConvert) {
			if(!str.contains("/")) {
				if(buffer != "") buffer = "";
				String st[] = str.split(" ");
				buffer = st[1] + "-" + Integer.toString(getMonth(st[0]));
			}
			else {
				String st[] = str.split("/");
				if(st[0].length() < 2) st[0] = "0" + st[0];
				if(st[1].length() < 2) st[1] = "0" + st[1];
				dateList.add(buffer + "-" + st[0]);
				dateList.add(buffer + "-" + st[1]);
			}
		}
	}
	
	private int getMonth(String value) {
		for(int i = 0; i < monthList.length; ++i) if(monthList[i].contentEquals(value)) return i + 1;
		return 1;
	}
}
