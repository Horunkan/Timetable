package com.Kitowski.DeleteEvents;

import java.util.ArrayList;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Calendar.CalendarHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class DeleteEvents extends Activity {
	private LinearLayout layout;
	private CheckBox selectAllCheckbox;
	private ArrayList<String> eventsNames;
	private ArrayList<CheckBox> eventsCheckbox;
	private Button deleteButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_delete_events);
		layout = (LinearLayout)findViewById(R.id.deleteLayout);
		
		selectAllCheckbox = (CheckBox)findViewById(R.id.checkBox_selectAll);
		selectAllCheckbox.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) { toggleAllCheckboxes(); }});
		
		eventsNames = CalendarHelper.getAllEvents(this, getIntent().getStringExtra("date"));
		eventsCheckbox = new ArrayList<CheckBox>();
		addCheckboxes();
		addDeleteButton();
	}
	
	private void toggleAllCheckboxes() {
		for(CheckBox ch : eventsCheckbox) ch.setChecked(selectAllCheckbox.isChecked());
	}
	
	private void addCheckboxes() {
		for(String str : eventsNames) {
			CheckBox buffer = new CheckBox(this);
			
			buffer.setText(str.split(",")[1]); //Display without id
			eventsCheckbox.add(buffer);
			layout.addView(buffer);
		}
	}
	
	private void addDeleteButton() {
		deleteButton = new Button(this);
		deleteButton.setText(R.string.button_delete);
		deleteButton.setGravity(Gravity.CENTER);
		deleteButton.setPadding(10, 15, 10, 15);
		deleteButton.setTextSize(20);
		layout.addView(deleteButton);
		
		deleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { delete(); }});
	}
	
	private void delete() {
		for(int i = 0; i < eventsCheckbox.size(); ++i) {
			if(eventsCheckbox.get(i).isChecked()) CalendarHelper.deleteEvent(this, Long.parseLong(eventsNames.get(i).split(",")[0]));
			this.finish();
		}
	}
}
