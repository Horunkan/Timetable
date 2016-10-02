package com.Kitowski.timetable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Timetable extends Activity {
	private LinearLayout layout;
	private ProgressBar loadBar = null;
	private TextView errorText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		layout = (LinearLayout)findViewById(R.id.mainLayout);
		loadTimetable();
	}
	
	private void loadTimetable() {
		if(isOnline()) {
			addProgressBar();
		}
		else {
			removeProgressBar();
			setError("No internet connection");
		}
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
	
	private void addProgressBar() {
		loadBar = new ProgressBar(this);
		layout.addView(loadBar);
	}
	
	private void removeProgressBar() {
		layout.removeView(loadBar);
		loadBar = null;
	}
}
