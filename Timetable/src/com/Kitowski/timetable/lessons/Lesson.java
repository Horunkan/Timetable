package com.Kitowski.timetable.lessons;

import com.Kitowski.timetable.Timetable;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

public class Lesson extends TextView {
	private String lessonData[];
	private GradientDrawable background;
	private Timetable timetable;
	
	@SuppressWarnings("deprecation")
	public Lesson(Timetable timetable, String lesson) {
		super(timetable);
		this.timetable = timetable;
		
		lessonData = lesson.split(",");
		background = new GradientDrawable();
        background.setColor(LessonLegend.getColor(lesson));
        background.setStroke(4, Color.LTGRAY);
		
		this.setText(lessonData[0] + " " + lessonData[1]);
		this.setPadding(15, 15, 15, 15);
		this.setBackgroundDrawable(background);
		
		this.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) { displayDetails(); }
        });
	}
	
	public String[] getRawTime() { return lessonData[0].split("—"); }
	public String getLessonName() { return lessonData[1]; }
	public String getDetails() { return formatDetails(); }
	
	private void displayDetails() {
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(timetable);
		dlgAlert.setTitle(lessonData[1]);
		dlgAlert.setMessage(formatDetails());
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	private String formatDetails() {
		String buffer = lessonData[2] + "\n";
		
		for(int i = 3; i < lessonData.length; ++i) {
			if(lessonData[i].contains("gr.")) buffer += " - " + lessonData[i]; //group
			else if(lessonData[i].replaceAll("\\s", "").matches("-?\\d+(\\.\\d+)?")) buffer += ", sala: " + lessonData[i] + "\n"; //room
			else buffer += lessonData[i];
		}
		return buffer;
	}
}
