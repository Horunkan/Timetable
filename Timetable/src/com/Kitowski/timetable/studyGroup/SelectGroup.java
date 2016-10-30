package com.Kitowski.timetable.studyGroup;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Timetable;
import com.Kitowski.timetable.Settings.Settings;
import com.Kitowski.timetable.Settings.Settings.Setting;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SelectGroup extends Spinner implements OnItemSelectedListener {
	private final static String logcatTAG = "Select group";
	
	private ArrayAdapter<String> spinnerArrayAdapter;
	private Timetable timetable;
	
	public SelectGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnItemSelectedListener(this);
	}
	
	public void update(Timetable timetable) {
		this.timetable = timetable; 
		spinnerArrayAdapter = new ArrayAdapter<String>(timetable, android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(timetable.getResources().getStringArray(R.array.studyGroups));
		this.setAdapter(spinnerArrayAdapter);
		
		if(Settings.getBoolean(Setting.SELECT_GROUP)) this.setSelection(Settings.getInt(Setting.SELECTED_GROUP));
	}
	
	public int getSelectedGroup() { return (int) this.getSelectedItemId(); }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Log.i(logcatTAG, (String)this.getSelectedItem());
		timetable.refresh();
	}

	@Override public void onNothingSelected(AdapterView<?> parent) { }
}
