package com.Kitowski.timetable.lessons;

import java.util.ArrayList;

import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.studyGroup.StudyGroup;

import android.widget.LinearLayout;

public class DisplayLessons {
	private ArrayList<Lesson> lessons;
	
	public DisplayLessons(Timetable timetable, LinearLayout layout, StudyGroup group) {
		lessons = new ArrayList<Lesson>();
		
		for(String str : group.getLessons()) {			
			Lesson ls = new Lesson(timetable, str);		
			layout.addView(ls);
			lessons.add(ls);
		}
	}
	
	public ArrayList<Lesson> getLessons() { return lessons; }
}
