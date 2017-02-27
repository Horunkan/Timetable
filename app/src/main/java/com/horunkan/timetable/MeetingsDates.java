package com.horunkan.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.horunkan.timetable.utilities.HtmlFormatter;
import com.horunkan.timetable.utilities.HttpLoader;

import java.util.ArrayList;

public class MeetingsDates {
    private static final String logcat = "MeetingsDates";
    private static final String logcatVal = logcat + "-value";

    private final String dateFormat = "%s-%02d-%02d";
    private final String monthYearRegex = "^[A-Za-ż]+ [0-9]{4}$";
    private final String dayRegex = "^[0-9]+/*[0-9]*$";
    private final String[] months;

    private final String[] meetingTable;
    private ArrayList<String> dates;

    public MeetingsDates(Context con) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
        meetingTable = new String[2];
        dates = new ArrayList<>();
        months = con.getResources().getStringArray(R.array.months);

        meetingTable[0] = pref.getString("MeetingTableWinter", "https://inf.ug.edu.pl/terminy-zjazdow-semestr-zimowy-201617.print");
        meetingTable[1] = pref.getString("MeetingTableSummer", "https://inf.ug.edu.pl/terminy-zjazdow-sem-letni-2016-17.print");

        loadDates();
    }

    private void loadDates() {
        HttpLoader http = (HttpLoader) new HttpLoader().execute(meetingTable[0], meetingTable[1]);

        try {
            ArrayList<String> toConvert = HtmlFormatter.getCleanWebsite(http.get());
            toConvert = getDateContentOnly(toConvert);
            formatDates(toConvert);
        }
        catch (Exception e) {
            Log.e(logcat, e.getMessage());
        }
    }

    private ArrayList<String> getDateContentOnly(ArrayList<String> arr) {
        ArrayList<String> buffer = new ArrayList<>();

        Log.i(logcat, "Start array filtering");
        for(String line : arr) {
            if(line.matches(monthYearRegex) || line.matches(dayRegex)) {
                buffer.add(line);
                Log.i(logcatVal, line);
            }
        }
        Log.i(logcat, String.format("Array filtering finished\nLines left: %d\nLines removed: %d", buffer.size(), arr.size() - buffer.size()));

        return buffer;
    }

    private void formatDates(ArrayList<String> arr) {
        String year = "";
        int month = 0;

        Log.i(logcat, "Start dates convert");
        for(String str : arr) {
            if(str.matches(monthYearRegex)) {
                year = getYear(str);
                month = getMonth(str);
            }
            else addDate(year, month, str);
        }

        for(String str : dates) Log.i(logcatVal, str);
        Log.i(logcat, String.format("Finished dates convert\nDates count: %d", dates.size()));
    }

    private String getYear(String str) { return str.split(" ")[1]; }

    private int getMonth(String str) {
        String buffer = str.split(" ")[0];
        for(int i = 0; i < 12; ++i) if(months[i].contentEquals(buffer)) return i + 1;
        return 1;
    }

    private void addDate(String year, int month, String days) {
        String[] day = days.split("/");

        dates.add(String.format(dateFormat, year, month, Integer.parseInt(day[0])));
        if(day.length > 1) dates.add(String.format(dateFormat, year, month, Integer.parseInt(day[1])));
    }
}
