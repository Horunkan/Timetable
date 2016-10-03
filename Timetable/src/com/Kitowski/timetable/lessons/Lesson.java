package com.Kitowski.timetable.lessons;

import com.Kitowski.timetable.Timetable;

import android.R.color;
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
        background.setColor(getBackgroundColor(lesson));
        background.setStroke(4, Color.LTGRAY);
		
		this.setText(lessonData[0] + " " + lessonData[1]);
		this.setPadding(15, 15, 15, 15);
		this.setBackgroundDrawable(background);
		
		this.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) { displayDetails(); }
        });
	}
	
	private int getBackgroundColor(String str) {
		if(str.contains("wykład+laboratorium")) return Color.CYAN;
		else if(str.contains("wykład+ćwiczenia")) return Color.GREEN;
		else if(str.contains("wykład+ćwiczenia")) return Color.MAGENTA;
		else if(str.contains("ćwiczenia")) return Color.RED;
		else if(str.contains("laboratorium")) return Color.YELLOW;
		else if(str.contains("wykład")) return Color.GRAY;
		return Color.WHITE;
	}
	
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
