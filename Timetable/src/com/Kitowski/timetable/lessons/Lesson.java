package com.Kitowski.timetable.lessons;

import com.Kitowski.timetable.Timetable;

import android.R.color;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

public class Lesson extends TextView {
	private String lessonData[];
	private GradientDrawable background;
	
	@SuppressWarnings("deprecation")
	public Lesson(Timetable timetable, String lesson) {
		super(timetable);
		
		lessonData = lesson.split(",");
		background = new GradientDrawable();
        background.setColor(color.white);
        background.setStroke(4, Color.LTGRAY);
		
		this.setText(lessonData[0] + " " + lessonData[1]);
		this.setPadding(15, 15, 15, 15);
		this.setBackgroundDrawable(background);
	}
}
