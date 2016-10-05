package com.Kitowski.DeleteEvents;

import java.util.ArrayList;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Calendar.CalendarHelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class DeleteEvents extends Activity {
	private LinearLayout layout;
	private CheckBox selectAllCheckbox;
	private ArrayList<String> eventsNames;
	private ArrayList<CheckBox> eventsCheckbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_events);
		layout = (LinearLayout)findViewById(R.id.deleteLayout);
		
		selectAllCheckbox = (CheckBox)findViewById(R.id.checkBox_selectAll);
		selectAllCheckbox.setOnClickListener(new OnClickListener() { @Override public void onClick(View v) { toggleAllCheckboxes(); } });
		
		eventsNames = CalendarHelper.getAllEvents(this, getIntent().getStringExtra("date"));
		eventsCheckbox = new ArrayList<CheckBox>();
		addCheckboxes();
	}
	
	private void toggleAllCheckboxes() {
		for(CheckBox ch : eventsCheckbox) ch.setChecked(selectAllCheckbox.isChecked());
	}
	
	private void addCheckboxes() {
		for(String str : eventsNames) {
			CheckBox buffer = new CheckBox(this);
			
			buffer.setText(str.split(",")[1]); //Display without ids
			eventsCheckbox.add(buffer);
			layout.addView(buffer);
		}
	}
}
