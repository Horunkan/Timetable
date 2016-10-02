package com.Kitowski.timetable;

import java.util.ArrayList;

import com.Kitowski.timetable.utilities.HttpReader;

import android.util.Log;

public class StudyGroupLoader {
	private final static String logcatTAG = "Timetable loader";
	
	private ArrayList<String> toConvert;
	private ArrayList<StudyGroup> groups;
	
	public StudyGroupLoader(String date) {
		HttpReader http = (HttpReader) new HttpReader().execute("https://inf.ug.edu.pl/plan-" + date + ".print", "body");
		toConvert = new ArrayList<String>();
		groups = new ArrayList<StudyGroup>();
		
		if(loadFromHttp(http)) {
			removeUnnecessaryLines();
			loadStudyGroups();
		}
	}
	
	public ArrayList<StudyGroup> getGroups() { return groups; }
	public StudyGroup getGroup(int id) { return groups.get(id); }
	
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
			else buffer.addLesson(str);
		}
		groups.add(buffer);
	}
}
