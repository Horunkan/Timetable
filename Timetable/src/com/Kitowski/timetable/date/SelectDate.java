package com.Kitowski.timetable.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.utilities.DateParser;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectDate extends Spinner implements OnItemSelectedListener {
	private final static String logcatTAG = "Select date";
	
	private ArrayAdapter<String> spinnerArrayAdapter;
	private Timetable timetable;
	
	public SelectDate(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnItemSelectedListener(this);
	}
	
	public void update(Timetable timetable, DateLoader date) {
		this.timetable = timetable;
		spinnerArrayAdapter = new ArrayAdapter<String>(timetable, android.R.layout.simple_spinner_item, date.getList());
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(spinnerArrayAdapter);
		
		updateCurrentSelection(date.getList());
	}
	
	public String getSelected() { return (String)this.getSelectedItem(); }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.i(logcatTAG, getSelected());
		timetable.refresh();
	}

	@Override public void onNothingSelected(AdapterView<?> parent) { }
		
	@SuppressLint("SimpleDateFormat")
	private void updateCurrentSelection(ArrayList<String> list) {
		Date currentDate = DateParser.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "00:00");
		
		for(int i = 0; i < list.size(); ++i) {
			Date toCheck = DateParser.parse(list.get(i), "00:00");
			
			if(currentDate.before(toCheck) || currentDate.compareTo(toCheck) == 0) {
				this.setSelection(i);
				break;
			}
		}
	}
}
