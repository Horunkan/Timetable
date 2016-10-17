package com.Kitowski.Settings;

import com.Kitowski.timetable.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class Settings extends Activity {
	public static boolean displayLegend, selectYear;
	
	private CheckBox displayLegendCheckbox, selectYearCheckbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		displayLegendCheckbox = (CheckBox)findViewById(R.id.checkbox_displaylegend);
		displayLegendCheckbox.setChecked(displayLegend);
		
		selectYearCheckbox = (CheckBox)findViewById(R.id.checkbox_displayyear);
		selectYearCheckbox.setChecked(selectYear);
		
		updateSelectYearSpinnerValues();
	}
	
	public static void loadSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		
		displayLegend = pref.getBoolean("Legend", true);
		selectYear = pref.getBoolean("SelectYear", false);
	}
	
	public static void saveSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor edit = pref.edit();
		
		edit.putBoolean("Legend", displayLegend);
		edit.putBoolean("SelectYear", selectYear);
		edit.apply();
	}
	
	@Override
	public void onBackPressed() { new ToSaveAlert(this).show(); }
	
	private void updateSelectYearSpinnerValues() {
		Spinner spn = (Spinner)findViewById(R.id.spinner_selectYear);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(getIntent().getStringArrayExtra("groups"));
		spn.setAdapter(spinnerArrayAdapter);
		if(!selectYearCheckbox.isChecked()) spn.setEnabled(false);
	}
}
