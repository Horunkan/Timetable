package com.Kitowski.timetable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Timetable extends Activity {
	private LinearLayout layout;
	private ProgressBar loadBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		
		layout = (LinearLayout)findViewById(R.id.mainLayout);
		loadBar = new ProgressBar(this);
		layout.addView(loadBar);
	}
}
