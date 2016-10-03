package com.Kitowski.timetable.date;

import com.Kitowski.timetable.Timetable;

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
	
	public SelectDate(Timetable timetable, DateLoader date) {
		super(timetable);
		this.timetable = timetable;
		spinnerArrayAdapter = new ArrayAdapter<String>(timetable, android.R.layout.simple_spinner_item, date.getList()); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(spinnerArrayAdapter);
		this.setOnItemSelectedListener(this);
	}
	
	public String getSelected() { return (String)this.getSelectedItem(); }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.i(logcatTAG, getSelected());
		timetable.refresh(false);
	}

	@Override public void onNothingSelected(AdapterView<?> parent) { }
}
