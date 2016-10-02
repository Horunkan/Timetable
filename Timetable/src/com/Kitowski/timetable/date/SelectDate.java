package com.Kitowski.timetable.date;

import com.Kitowski.timetable.Timetable;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class SelectDate implements OnItemSelectedListener {
	private final static String logcatTAG = "Select date";
	
	private Spinner spinner;
	private ArrayAdapter<String> spinnerArrayAdapter;
	
	public SelectDate(Timetable timetable, LinearLayout layout, DateLoader date) {
		spinner = new Spinner(timetable);
		spinnerArrayAdapter = new ArrayAdapter<String>(timetable, android.R.layout.simple_spinner_item, date.getList()); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setOnItemSelectedListener(this);
		layout.addView(spinner);
	}
	
	public String getSelected() { return (String)spinner.getSelectedItem(); }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.i(logcatTAG, (String)spinner.getSelectedItem());
	}

	@Override public void onNothingSelected(AdapterView<?> parent) { }
}
