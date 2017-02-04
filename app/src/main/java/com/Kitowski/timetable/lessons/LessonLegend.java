package com.Kitowski.timetable.lessons;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Timetable;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LessonLegend {
	private static String lessonType[];
	
	private TextView title;
	private TextView legend[];
	private Timetable timetable;
	
	public LessonLegend(Timetable timetable) {	
		this.timetable = timetable;
		addTitle();
		
		legend = new TextView[6];
		for(int i = 0; i < 5; ++i) legend[i] = addLegend(lessonType[i]);
		legend[5] = addLegend("Inne");
	}
	
	public void dispay(LinearLayout layout) {
		layout.addView(title);
		for(int i = 0; i < 6; ++i) layout.addView(legend[i]);
	}
	
	public void remove(LinearLayout layout) {
		layout.removeView(title);
		for(int i = 0; i < 6; ++i) layout.removeView(legend[i]);
	}
	
	private void addTitle() {
		title = new TextView(timetable);
		title.setText(R.string.legend_title);
		title.setTypeface(Typeface.DEFAULT_BOLD);
		title.setGravity(Gravity.CENTER);
		title.setTextSize(20);
		title.setPadding(15, 200, 15, 5);
	}
	
	@SuppressWarnings("deprecation")
	private TextView addLegend(String str) {
		GradientDrawable background = new GradientDrawable();
		background.setColor(getColor(str));
		background.setStroke(2, Color.BLACK);
		
		TextView buffer = new TextView(timetable);
		buffer.setText(str);
		buffer.setPadding(15, 5, 15, 5);
		buffer.setTextSize(10);
		buffer.setBackgroundDrawable(background);
		
		return buffer;
	}
	
	public static void updateLessonType(Timetable timetable) { lessonType = timetable.getResources().getStringArray(R.array.legend_values); }
	
	public static int getColor(String str) {
		if(str.contains(lessonType[0])) return Color.CYAN;
		else if(str.contains(lessonType[1])) return Color.GREEN;
		else if(str.contains(lessonType[2])) return Color.RED;
		else if(str.contains(lessonType[3])) return Color.YELLOW;
		else if(str.contains(lessonType[4])) return Color.GRAY;
		return Color.WHITE;
	}
}
