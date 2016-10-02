package com.Kitowski.timetable;

import java.util.ArrayList;

public class StudyGroup {
	private String groupName;
	private ArrayList<String> lessons;
	
	public StudyGroup(String name) {
		groupName = name;
		lessons = new ArrayList<String>();
	}
	
	public void addLesson(String str) {
		lessons.add(str);
	}
	
	public String getName() { return groupName; }
	public ArrayList<String> getLessons() { return lessons; }
}
