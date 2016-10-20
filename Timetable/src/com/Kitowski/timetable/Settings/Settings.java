package com.Kitowski.timetable.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	public static boolean displayLegend, selectYear, selectGroup;
	public static String selectYearValue, selectGroupValueLetter, selectGroupValueNumber;
	
	public static void loadSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		
		displayLegend = pref.getBoolean("Legend", true);
		selectYear = pref.getBoolean("SelectYear", false);
		selectYearValue = pref.getString("SelectYearValue", "NULL");
		selectGroup = pref.getBoolean("SelectGroup", false);
		selectGroupValueLetter = pref.getString("SelectGroupValueLetter", "NULL");
		selectGroupValueNumber = pref.getString("SelectGroupValueNumber", "NULL");
	}
	
	public static void saveSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor edit = pref.edit();
		
		edit.putBoolean("Legend", displayLegend);
		edit.putBoolean("SelectGroup", selectYear);
		edit.putString("SelectYearValue", selectYearValue);
		edit.putBoolean("SelectGroup", selectGroup);
		edit.putString("SelectGroupValueLetter", selectGroupValueLetter);
		edit.putString("SelectGroupValueNumber", selectGroupValueNumber);
		edit.apply();
	}
}
