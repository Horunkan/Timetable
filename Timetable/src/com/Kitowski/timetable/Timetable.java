package com.Kitowski.timetable;

import java.util.ArrayList;

import com.Kitowski.timetable.Calendar.CalendarHelper;
import com.Kitowski.timetable.date.DateLoader;
import com.Kitowski.timetable.date.SelectDate;
import com.Kitowski.timetable.lessons.Lesson;
import com.Kitowski.timetable.lessons.LessonLegend;
import com.Kitowski.timetable.studyGroup.SelectGroup;
import com.Kitowski.timetable.studyGroup.StudyGroupLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Timetable extends Activity {
	private LinearLayout layout;
	private TextView errorText;
	private Button refreshButton;
	private SelectDate selectDate;
	private StudyGroupLoader groups;
	private SelectGroup selectGroup;
	private ArrayList<Lesson> lessons;
	private LessonLegend legend;
	private Button calendarButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		layout = (LinearLayout)findViewById(R.id.mainLayout);
		loadTimetable();
	}
	
	private void loadTimetable() {
		if(isOnline()) {
			addSelectDate();
			addSelectGroup();
			addLessons();
			addLegend();
		}
		else {
			setError("No internet connection");
			addRefreshButton();
		}
	}
	
	public void refresh(boolean lessonsOnly) {
		layout.removeView(errorText);
		layout.removeView(refreshButton);
		layout.removeView(calendarButton);
		legend.remove(layout);
		if(!lessonsOnly) {
			layout.removeView(selectGroup);
			addSelectGroup();
		}
		
		for(TextView txt : lessons) layout.removeView(txt);
		if(groups.loaded) {
			addLessons();
			addCalendarButton();
			addLegend();
		}
	}
	
	private void addSelectDate() {
		DateLoader date = new DateLoader();
		selectDate = new SelectDate(this, date);
		layout.addView(selectDate);	
	}
	
	private void addSelectGroup() {
		groups = new StudyGroupLoader(selectDate.getSelected());	
		if(groups.loaded) {
			selectGroup = new SelectGroup(this, groups);
			layout.addView(selectGroup);
		}
		else {
			if(!isOnline()) setError("No internet connection");
			else setError("Timetable for selected day is not published yet");
		}
	}
	
	private void addLessons() {
		lessons = new ArrayList<Lesson>();
		for(String str : groups.getGroup(selectGroup.getSelectedGroup()).getLessons()) {
			Lesson ls = new Lesson(this, str);	
			layout.addView(ls);
			lessons.add(ls);
		}
	}
	
	private void addCalendarButton() {
		calendarButton = new Button(this);
		calendarButton.setText("Add to calendar");
		calendarButton.setGravity(Gravity.CENTER);
		calendarButton.setPadding(10, 15, 10, 15);
		calendarButton.setTextSize(20);
		layout.addView(calendarButton);
		
		calendarButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {;
				for(Lesson ls : lessons) {
					if(!CalendarHelper.addToCalendar(getTimetable(), selectDate.getSelected(), ls)) {
						Toast toast = Toast.makeText(getTimetable(), "Failed to add timetable to calendar", Toast.LENGTH_SHORT);
						toast.show();
						break;
					}
					Toast toast = Toast.makeText(getTimetable(), "Timetable added to calendar", Toast.LENGTH_SHORT);
					toast.show();
				}
		    }
		});
	}
	
	public Timetable getTimetable() { return this; }
	
	private void addLegend() {
		 legend = new LessonLegend(this);
		 legend.dispay(layout);
	}
	
	private boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
	private void setError(String error) {
		errorText = new TextView(this);
		errorText.setText(error);
		errorText.setTypeface(Typeface.DEFAULT_BOLD);
		errorText.setGravity(Gravity.CENTER);
		errorText.setTextSize(20);
		layout.addView(errorText);
	}
	
	private void addRefreshButton() {
		refreshButton = new Button(this);
		refreshButton.setText("Refresh");
		refreshButton.setGravity(Gravity.CENTER);
		refreshButton.setTextSize(20);
		layout.addView(refreshButton);
		
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		      layout.removeAllViews();
		      loadTimetable();
		    }
		});
	}
}
