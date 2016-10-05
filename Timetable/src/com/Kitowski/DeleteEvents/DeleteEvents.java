package com.Kitowski.DeleteEvents;

import java.util.ArrayList;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Calendar.CalendarHelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class DeleteEvents extends Activity {
	private LinearLayout layout;
	private CheckBox selectAllCheckbox;
	private ArrayList<String> events;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_events);
		layout = (LinearLayout)findViewById(R.id.deleteLayout);
		selectAllCheckbox = (CheckBox)findViewById(R.id.checkBox_selectAll);
		events = CalendarHelper.getAllEvents(this, getIntent().getStringExtra("date"));
		
		for(String str : events) {
			Log.d("A", str);
		}
	}
}
