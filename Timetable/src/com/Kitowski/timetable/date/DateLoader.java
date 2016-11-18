package com.Kitowski.timetable.date;

import java.util.ArrayList;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.utilities.HttpReader;

import android.util.Log;

public class DateLoader {
	private final static String logcatTAG = "Date loader";
	private final static String dateStringFormat = "%1$s-%2$02d-%3$02d";

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
	
	private boolean loadFromHttp(HttpReader http) {
		try { 
			toConvert = http.get();
			
			if(toConvert.size() == 0) return false;
			else return true;
		}
		catch(Exception e) {
			Log.e(logcatTAG, "Failed to load");
			return false;
		}
	}
	
	public ArrayList<String> getList() { return dateList; }
	
	private void convertDate() {
		String year = "", month = "";
		
		for(String str : toConvert) {
			if(!str.contains("/")) {
				year = str.split(" ")[1];
				month = str.split(" ")[0];
			}
			else {
				String day[] = str.split("/");
				dateList.add(String.format(dateStringFormat, year, getMonth(month), Integer.parseInt(day[0])));
				dateList.add(String.format(dateStringFormat, year, getMonth(month), Integer.parseInt(day[1])));
			}
		}
	}
	
	private int getMonth(String value) {
		for(int i = 0; i < monthList.length; ++i) if(monthList[i].contentEquals(value)) return i + 1;
		return 1;
	}
}
