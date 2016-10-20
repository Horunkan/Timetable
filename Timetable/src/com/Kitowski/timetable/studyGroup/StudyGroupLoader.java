package com.Kitowski.timetable.studyGroup;

import java.util.ArrayList;

import com.Kitowski.timetable.Settings.Settings;
import com.Kitowski.timetable.Settings.Settings.Setting;
import com.Kitowski.timetable.utilities.HttpReader;
import android.util.Log;

public class StudyGroupLoader {
	private final static String logcatTAG = "Timetable loader";
	
	private ArrayList<String> toConvert;
	private ArrayList<StudyGroup> groups;
	
	public boolean loaded = true;
	
	public StudyGroupLoader(String date) {
		toConvert = new ArrayList<String>();
		groups = new ArrayList<StudyGroup>();
		
		if(loadFromHttp(date)) {
			removeUnnecessaryLines();
			loadStudyGroups();
		}
		else loaded = false;
	}
	
	public ArrayList<StudyGroup> getGroups() { return groups; }
	public StudyGroup getGroup(int id) { return groups.get(id); }
	
	public String[] getGroupsNames() {
		String buffer[] = new String[groups.size()];
		for(int i = 0; i < buffer.length; ++i) buffer[i] = groups.get(i).getName();
		return buffer;
	}
	
	private boolean loadFromHttp(String date) {
		try { 
			HttpReader http = (HttpReader) new HttpReader().execute("https://inf.ug.edu.pl/plan-" + date + ".print", "body");
			toConvert = http.get();
			
			if(toConvert.size() == 0) return false;
			else return true;
		}
		catch(Exception e) {
			Log.e(logcatTAG, "Failed to load");
			return false;
		}
	}
	
	private void removeUnnecessaryLines() {
		toConvert.remove(0);
		toConvert.remove(0);
		toConvert.remove(toConvert.size() - 1);
		toConvert.remove(toConvert.size() - 1);
	}
	
	private void loadStudyGroups() {
		StudyGroup buffer = null;
		
		for(String str : toConvert) {
			if(str.contains("Studia")) {
				if(buffer != null) groups.add(buffer);
				buffer = new StudyGroup(str);
			}
			else if(Settings.getBoolean(Setting.SELECT_GROUP) && str.contains("gr.")) {
				char groupLetter = Settings.getGroup(Setting.SELECTED_GROUP_LETT);
				char groupNumber = Settings.getGroup(Setting.SELECTED_GROUP_NUM);
				
				if(str.contains(" " + groupLetter + ",") || str.contains(" i " + groupLetter)) buffer.addLesson(str);
				else if(str.contains(" " + groupNumber + ",") || str.contains(" i " + groupNumber)) buffer.addLesson(str);
			}
			else buffer.addLesson(str);
		}
		groups.add(buffer);
	}
}
