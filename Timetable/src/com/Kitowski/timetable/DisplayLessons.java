package com.Kitowski.timetable;

import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayLessons {
	
	
	public DisplayLessons(Timetable timetable, LinearLayout layout, Group group) {
		for(String str : group.getLessons()) {
			TextView txt = new TextView(timetable);
			txt.setText(str);
			layout.addView(txt);
		}
	}
}
