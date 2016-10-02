package com.Kitowski.timetable;

import java.util.ArrayList;

import com.Kitowski.timetable.studyGroup.StudyGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayLessons {
	private ArrayList<TextView> lessons;
	
	public DisplayLessons(Timetable timetable, LinearLayout layout, StudyGroup group) {
		lessons = new ArrayList<TextView>();
		
		for(String str : group.getLessons()) {
			TextView txt = new TextView(timetable);
			txt.setText(str);
			layout.addView(txt);
			lessons.add(txt);
		}
	}
	
	public ArrayList<TextView> getLessons() { return lessons; }
}
