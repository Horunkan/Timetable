package com.Kitowski.timetable.date;

import android.util.Log;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.utilities.HttpReader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateLoader {
    private final static String[] dateSources = {"https://inf.ug.edu.pl/terminy-zjazdow-semestr-zimowy-201617", "https://inf.ug.edu.pl/terminy-zjazdow-sem-letni-2016-17"};
	private final static String logcatTAG = "Date loader";
	private final static String dateStringFormat = "%1$s-%2$02d-%3$02d";

	private ArrayList<String> toConvert;
	private ArrayList<String> dateList;
	private final String monthList[];
	
	public DateLoader(Timetable timetable) {
		toConvert = new ArrayList<String>();
		dateList = new ArrayList<String>();
		monthList = timetable.getResources().getStringArray(R.array.month);
		
		if(loadFromHttp()) convertDate();
	}
	
	private boolean loadFromHttp() {
		try {
            for(String date : dateSources) {
                HttpReader http = (HttpReader) new HttpReader().execute(date, "table");
                toConvert.addAll(http.get());
            }

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

        String monthYearRegex = "^[A-Za-ż]+ [0-9]{4}$";
		Pattern monthYearPattern = Pattern.compile(monthYearRegex);

		for(String str : toConvert) {
            Matcher monthYearMatch = monthYearPattern.matcher(str);

            if(monthYearMatch.find()) {
                year = str.split(" ")[1];
                month = str.split(" ")[0];
            }
			else {
				String day[] = str.split("/");
				dateList.add(String.format(dateStringFormat, year, getMonth(month), Integer.parseInt(day[0])));
				if(day.length > 1) dateList.add(String.format(dateStringFormat, year, getMonth(month), Integer.parseInt(day[1])));
			}
		}
	}
	
	private int getMonth(String value) {
		for(int i = 0; i < monthList.length; ++i) if(monthList[i].contentEquals(value)) return i + 1;
		return 1;
	}
}
