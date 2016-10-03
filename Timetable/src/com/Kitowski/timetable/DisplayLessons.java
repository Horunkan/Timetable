package com.Kitowski.timetable;

import java.util.ArrayList;

import com.Kitowski.timetable.studyGroup.StudyGroup;

import android.R.color;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayLessons {
	private ArrayList<TextView> lessons;
	
	@SuppressWarnings("deprecation")
	public DisplayLessons(Timetable timetable, LinearLayout layout, StudyGroup group) {
		lessons = new ArrayList<TextView>();
		
		GradientDrawable gd = new GradientDrawable();
        gd.setColor(color.white);
        gd.setStroke(4, Color.LTGRAY);
		
		for(String str : group.getLessons()) {
			TextView txt = new TextView(timetable);
			txt.setText(str);	        
	        txt.setPadding(0, 15, 0, 15);
	        txt.setBackgroundDrawable(gd);
			
			layout.addView(txt);
			lessons.add(txt);
		}
	}
	
	public ArrayList<TextView> getLessons() { return lessons; }
}
