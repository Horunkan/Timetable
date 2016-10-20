package com.Kitowski.timetable.date;

import java.util.Date;

import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.utilities.DateParser;
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
		
		updateCurrentSelection(date.getList().toArray(new String[0]));
	}
	
	public String getSelected() { return (String)this.getSelectedItem(); }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.i(logcatTAG, getSelected());
		timetable.refresh(false);
	}

	@Override public void onNothingSelected(AdapterView<?> parent) { }
	
	private void updateCurrentSelection(String[] list) {		
		Date currentDate = new Date();
		
		for(int i = 0; i < list.length; ++i) {
			Date toCheck = DateParser.parse(list[i], "00:00");
			
			if(currentDate.before(toCheck) || currentDate.compareTo(toCheck) == 0) {
				this.setSelection(i);
				break;
			}
		}
	}
}
