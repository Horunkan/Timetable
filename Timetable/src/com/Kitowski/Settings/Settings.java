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
	private CheckBox legend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		legend = (CheckBox)findViewById(R.id.checkbox_displaylegend);
		
		load();
		updateSelectYearSpinnerValues();
	}
	
	private void load() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);		
		legend.setChecked(preferences.getBoolean("Legend", true));
	}
	
	private void updateSelectYearSpinnerValues() {
		Spinner spn = (Spinner)findViewById(R.id.spinner_selectYear);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(getIntent().getStringArrayExtra("groups"));
		spn.setAdapter(spinnerArrayAdapter);
	}
}
