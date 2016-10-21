package com.Kitowski.timetable.studyGroup;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.Settings.Settings;
import com.Kitowski.timetable.Settings.Settings.Setting;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SelectGroup extends Spinner implements OnItemSelectedListener {
	private final static String logcatTAG = "Select group";
	
	private ArrayAdapter<String> spinnerArrayAdapter;
	private Timetable timetable;
	
	public SelectGroup(Timetable timetable, StudyGroupLoader loader) {
		super(timetable);
		this.timetable = timetable;
		spinnerArrayAdapter = new ArrayAdapter<String>(timetable, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		for(StudyGroup gr : loader.getGroups()) spinnerArrayAdapter.add(gr.getName());
		
		this.setAdapter(spinnerArrayAdapter);
		this.setOnItemSelectedListener(this);
		
		if(Settings.getBoolean(Setting.SELECT_YEAR)) updateCurrentSelection();
	}
	
	public int getSelectedGroup() { return (int) this.getSelectedItemId(); }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.i(logcatTAG, (String)this.getSelectedItem());
		timetable.refresh(true);
	}

	@Override public void onNothingSelected(AdapterView<?> parent) { }
	
	private void updateCurrentSelection() {
		for(int i = 0; i < spinnerArrayAdapter.getCount(); ++i) {
			if(spinnerArrayAdapter.getItem(i).contentEquals(Settings.getString(Setting.SELECTED_YEAR))) {
				this.setSelection(i);
				break;
			}
		}
	}
}
