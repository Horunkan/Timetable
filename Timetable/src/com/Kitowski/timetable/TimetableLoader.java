package com.Kitowski.timetable;

import java.util.ArrayList;

import android.util.Log;

public class TimetableLoader {
	private final static String logcatTAG = "Timetable loader";
	public boolean loaded = false;
	
	private ArrayList<String> toConvert;
	private ArrayList<Group> groups;
	
	
	public TimetableLoader(String date) {
		HttpReader http = (HttpReader) new HttpReader().execute("https://inf.ug.edu.pl/plan-" + date + ".print", "body");
		toConvert = new ArrayList<String>();
		
		try {
			toConvert = http.get();
			
			if(toConvert.size() == 0) loaded = false;
			else loaded = true;
		}
		catch(Exception e) {
			Log.e(logcatTAG, "Failed to load");
			loaded = false;
		}
		
		if(loaded) {
			groups = new ArrayList<Group>();
			
			toConvert.remove(0);
			toConvert.remove(0);
			toConvert.remove(toConvert.size() - 1);
			toConvert.remove(toConvert.size() - 1);
			
			loadGroups();
			
			for(int i = 0; i < groups.size(); ++i) {
				Log.i(logcatTAG, groups.get(i).getName());
				for(String lesson : groups.get(i).getLessons()) Log.i(logcatTAG, lesson);
			}
		}
	}
	
	public ArrayList<Group> getGroups() { return groups; }
	public Group getGroup(int id) { return groups.get(id); }
	
	private void loadGroups() {
		Group buffer = null;
		
		for(String str : toConvert) {
			if(str.contains("Studia")) {
				if(buffer != null) {
					groups.add(buffer);
					buffer = new Group(str);
				}
				else buffer = new Group(str);
			}
			else {
				buffer.addLesson(str);
			}
		}
		groups.add(buffer);
	}
}
