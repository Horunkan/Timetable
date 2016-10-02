package com.Kitowski.timetable;

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
	private MeetingDates meetings;
	private SelectDate selectDates;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		layout = (LinearLayout)findViewById(R.id.mainLayout);
		loadTimetable();
	}
	
	private void loadTimetable() {
		if(isOnline()) {
			meetings = new MeetingDates();
			selectDates = new SelectDate(this, layout, meetings);
		}
		else {
			setError("No internet connection");
			addRefreshButton();
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
