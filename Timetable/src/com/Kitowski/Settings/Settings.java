package com.Kitowski.Settings;

import com.Kitowski.timetable.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class Settings extends Activity {
	private CheckBox displayLegend, selectYear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		displayLegend = (CheckBox)findViewById(R.id.checkbox_displaylegend);
		selectYear = (CheckBox)findViewById(R.id.checkbox_displayyear);
		
		loadCheckboxes();
		updateSelectYearSpinnerValues();
	}
	
	private void loadCheckboxes() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		displayLegend.setChecked(pref.getBoolean("Legend", true));
		selectYear.setChecked(pref.getBoolean("SelectYear", false));
	}
	
	private void updateSelectYearSpinnerValues() {
		Spinner spn = (Spinner)findViewById(R.id.spinner_selectYear);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(getIntent().getStringArrayExtra("groups"));
		spn.setAdapter(spinnerArrayAdapter);
		if(!selectYear.isChecked()) spn.setEnabled(false);
	}
}
