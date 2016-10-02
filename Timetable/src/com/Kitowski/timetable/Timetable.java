package com.Kitowski.timetable;

import com.Kitowski.timetable.date.DateLoader;
import com.Kitowski.timetable.date.SelectDate;
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

public class Timetable extends Activity {
	private LinearLayout layout;
	private TextView errorText;
	private Button refreshButton;
	private SelectDate selectDate;
	private StudyGroupLoader groups;
	private SelectGroup selectGroup;
	private DisplayLessons lessons;
	
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
		}
		else {
			setError("No internet connection");
			addRefreshButton();
		}
	}
	
	public void refresh(boolean lessonsOnly) {
		layout.removeView(errorText);
		if(!lessonsOnly) {
			layout.removeView(selectGroup.getSpinner());
			addSelectGroup();
		}
		
		for(TextView txt : lessons.getLessons()) layout.removeView(txt);
		if(groups.loaded) addLessons();
	}
	
	private void addSelectDate() {
		DateLoader date = new DateLoader();
		selectDate = new SelectDate(this, layout, date);
	}
	
	private void addSelectGroup() {
		groups = new StudyGroupLoader(selectDate.getSelected());	
		if(groups.loaded)selectGroup = new SelectGroup(this, layout, groups);
		else {
			if(!isOnline()) setError("No internet connection");
			else setError("Timetable at selected day is not published yet");
		}
	}
	
	private void addLessons() {
		lessons = new DisplayLessons(this, layout, groups.getGroup(selectGroup.getSelectedGroup()));
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
