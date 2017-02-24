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

    private final String[] meetingTable;
    private ArrayList<String> dates;

    public MeetingsDates(Context con) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
        meetingTable = new String[2];
        dates = new ArrayList<String>();

        meetingTable[0] = pref.getString("MeetingTableWinter", "https://inf.ug.edu.pl/terminy-zjazdow-semestr-zimowy-201617.print");
        meetingTable[1] = pref.getString("MeetingTableSummer", "https://inf.ug.edu.pl/terminy-zjazdow-sem-letni-2016-17.print");

        loadDates();
    }

    private void loadDates() {
        HttpLoader http = (HttpLoader) new HttpLoader().execute(meetingTable[0], meetingTable[1]);

        try {
            ArrayList<String> toConvert = HtmlFormatter.getCleanWebsite(http.get());
            toConvert = getDateContentOnly(toConvert);
        }
        catch (Exception e) {
            Log.e(logcat, e.getMessage());
        }
    }

    private ArrayList<String> getDateContentOnly(ArrayList<String> arr) {
        final String monthYearRegex = "^[A-Za-ż]+ [0-9]{4}$";
        final String dayRegex = "^[0-9]+/*[0-9]*$";
        ArrayList<String> buffer = new ArrayList<String>();

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
}
