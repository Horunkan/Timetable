package com.Kitowski.timetable.studyGroup;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.Kitowski.timetable.Timetable;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SelectGroup implements OnItemSelectedListener {
	private final static String logcatTAG = "Select group";
	
	private Spinner spinner;
	private ArrayAdapter<String> spinnerArrayAdapter;
	private Timetable timetable;
	
	public SelectGroup(Timetable timetable, LinearLayout layout, StudyGroupLoader loader) {
		this.timetable = timetable;
		spinner = new Spinner(timetable);
		spinnerArrayAdapter = new ArrayAdapter<String>(timetable, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		for(StudyGroup gr : loader.getGroups()) spinnerArrayAdapter.add(gr.getName());
		
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setOnItemSelectedListener(this);
		layout.addView(spinner);
	}
	
	public int getSelectedGroup() { return (int) spinner.getSelectedItemId(); }
	public Spinner getSpinner() { return spinner; }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.i(logcatTAG, (String)spinner.getSelectedItem() + spinner.getSelectedItemId());
		timetable.refresh(true);
	}

	@Override public void onNothingSelected(AdapterView<?> parent) { }
}
