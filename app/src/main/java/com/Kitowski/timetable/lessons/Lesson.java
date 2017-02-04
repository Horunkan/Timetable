package com.Kitowski.timetable.lessons;

import com.Kitowski.timetable.Timetable;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

public class Lesson extends TextView {
	private String startTime, endTime, title, description;
	private GradientDrawable background;
	private Timetable timetable;
	
	@SuppressWarnings("deprecation")
	public Lesson(Timetable timetable, String lesson) {
		super(timetable);
		this.timetable = timetable;
		
		formatString(lesson);
		background = new GradientDrawable();
        background.setColor(LessonLegend.getColor(lesson));
        background.setStroke(4, Color.LTGRAY);
		
        this.setText(String.format("%s—%s %s", startTime, endTime, title));
		this.setPadding(15, 15, 15, 15);
		this.setBackgroundDrawable(background);
		
		this.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) { displayDetails(); }
        });
	}
	
	private void formatString(String rawLesson) {
		String lessonData[] = rawLesson.split(",");
		
		startTime = lessonData[0].split("—")[0];
		endTime = lessonData[0].split("—")[1];
		title = lessonData[1];
		description = formatDetails(lessonData);
	}
	
	public String getStartTime() { return startTime; }
	public String getEndTime() { return endTime; }
	public String getTitle() { return title; }
	public String getDescription() { return description; }
	
	private void displayDetails() {
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(timetable);
		dlgAlert.setTitle(title);
		dlgAlert.setMessage(description);
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	private String formatDetails(String lessonData[]) {
		String buffer = lessonData[2] + "\n";
		
		for(int i = 3; i < lessonData.length; ++i) {
			if(lessonData[i].contains("gr.")) buffer += " - " + lessonData[i]; //group
			else if(lessonData[i].replaceAll("\\s", "").matches("-?\\d+(\\.\\d+)?")) buffer += ", sala: " + lessonData[i] + "\n"; //room
			else buffer += lessonData[i];
		}
		return buffer;
	}
}
