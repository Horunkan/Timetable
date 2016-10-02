package com.Kitowski.timetable;

import com.Kitowski.timetable.studyGroup.StudyGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayLessons {
	public DisplayLessons(Timetable timetable, LinearLayout layout, StudyGroup group) {
		for(String str : group.getLessons()) {
			TextView txt = new TextView(timetable);
			txt.setText(str);
			layout.addView(txt);
		}
	}
}
