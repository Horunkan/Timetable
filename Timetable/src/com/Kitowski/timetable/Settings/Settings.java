package com.Kitowski.timetable.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Settings {
	private final static String logcatTAG = "Settings";
	
	public enum Setting { DISPLAY_LEGEND, SELECT_YEAR, SELECT_GROUP, SELECTED_YEAR, SELECTED_GROUP_LETT, SELECTED_GROUP_NUM }
	
	private static boolean displayLegend, selectYear, selectGroup;
	private static String selectYearValue, selectGroupValueLetter, selectGroupValueNumber;
	
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
	
	public static boolean getBoolean(Setting sett) {
		if(sett == Setting.DISPLAY_LEGEND) return displayLegend;
		else if(sett == Setting.SELECT_YEAR) return selectYear;
		else if(sett == Setting.SELECT_GROUP) return selectGroup;
		else {
			Log.e(logcatTAG, "Wrong Setting enum - getBoolean()");
			return false;
		}
	}
	
	public static void toggleBoolean(Setting sett) {
		if(sett == Setting.DISPLAY_LEGEND) displayLegend = !displayLegend;
		else if(sett == Setting.SELECT_YEAR) selectYear = !selectYear;
		else if(sett == Setting.SELECT_GROUP) selectGroup = !selectGroup;
		else Log.e(logcatTAG, "Wrong Setting enum - toggleBoolean()");
	}
	
	public static String getString(Setting sett) {
		if(sett == Setting.SELECTED_YEAR) return selectYearValue;
		else if(sett == Setting.SELECTED_GROUP_LETT) return selectGroupValueLetter;
		else if(sett == Setting.SELECTED_GROUP_NUM) return selectGroupValueNumber;
		else {
			Log.e(logcatTAG, "Wrong Setting enum - getString()");
			return "";
		}
	}
	
	public static void setString(Setting sett, String value) {
		if(sett == Setting.SELECTED_YEAR) selectYearValue = value;
		else if(sett == Setting.SELECTED_GROUP_LETT) selectGroupValueLetter = value;
		else if(sett == Setting.SELECTED_GROUP_NUM) selectGroupValueNumber = value;
		else Log.e(logcatTAG, "Wrong Setting enum - setString()");
	}
	
	public static char getGroup(Setting sett) {
		if(sett == Setting.SELECTED_GROUP_LETT) return selectGroupValueLetter.charAt(selectGroupValueLetter.length() - 1);
		else if(sett == Setting.SELECTED_GROUP_NUM) return selectGroupValueNumber.charAt(selectGroupValueNumber.length() - 1);
		else {
			Log.e(logcatTAG, "Wrong Setting enum - getGroup()");
			return '0';
		}
	}
}
