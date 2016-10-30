package com.Kitowski.timetable.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Settings {
	private final static String logcatTAG = "Settings";
	
	public enum Setting { DISPLAY_LEGEND, SELECT_GROUP, SELECT_SUBGROUP, SELECTED_GROUP, SELECTED_GROUP_LETT, SELECTED_GROUP_NUM }
	
	private static boolean displayLegend, selectGroup, selectSubgroup;
	private static int selectGroupID, selectSubgroupID_letter, selectSubgroupID_number;
	
	public static void loadSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);

		displayLegend = pref.getBoolean("Legend", true);
		selectGroup = pref.getBoolean("SelectGroup", false);
		selectGroupID = pref.getInt("SelectGroupID", 0);
		selectSubgroup = pref.getBoolean("SelectSubgroup", false);
		selectSubgroupID_letter = pref.getInt("SelectSubgroupID_letter", 0);
		selectSubgroupID_number = pref.getInt("SelectSubgroupID_number", 0);
	}
	
	public static void saveSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor edit = pref.edit();
		
		edit.putBoolean("Legend", displayLegend);
		edit.putBoolean("SelectGroup", selectGroup);
		edit.putInt("SelectGroupID", selectGroupID);
		edit.putBoolean("SelectSubgroup", selectSubgroup);
		edit.putInt("SelectSubgroupID_letter", selectSubgroupID_letter);
		edit.putInt("SelectSubgroupID_number", selectSubgroupID_number);
		edit.apply();
	}
	
	public static boolean getBoolean(Setting sett) {
		if(sett == Setting.DISPLAY_LEGEND) return displayLegend;
		else if(sett == Setting.SELECT_GROUP) return selectGroup;
		else if(sett == Setting.SELECT_SUBGROUP) return selectSubgroup;
		else {
			Log.e(logcatTAG, "Wrong Setting enum - getBoolean()");
			return false;
		}
	}
	
	public static void toggleBoolean(Setting sett) {
		if(sett == Setting.DISPLAY_LEGEND) displayLegend = !displayLegend;
		else if(sett == Setting.SELECT_GROUP) selectGroup = !selectGroup;
		else if(sett == Setting.SELECT_SUBGROUP) selectSubgroup = !selectSubgroup;
		else Log.e(logcatTAG, "Wrong Setting enum - toggleBoolean()");
	}
	
	public static int getInt(Setting sett) {
		if(sett == Setting.SELECTED_GROUP) return selectGroupID;
		else if(sett == Setting.SELECTED_GROUP_LETT) return selectSubgroupID_letter;
		else if(sett == Setting.SELECTED_GROUP_NUM) return selectSubgroupID_number;
		else {
			Log.e(logcatTAG, "Wrong Setting enum - getInt()");
			return 0;
		}
	}
	
	public static void setInt(Setting sett, int val) {
		if(sett == Setting.SELECTED_GROUP) selectGroupID = val;
		else if(sett == Setting.SELECTED_GROUP_LETT) selectSubgroupID_letter = val;
		else if(sett == Setting.SELECTED_GROUP_NUM) selectSubgroupID_number = val;
		else Log.e(logcatTAG, "Wrong Setting enum - setInt()");
	}
}
