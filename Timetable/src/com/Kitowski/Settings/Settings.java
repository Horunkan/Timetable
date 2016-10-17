package com.Kitowski.Settings;

import com.Kitowski.Settings.Checkbox.checkboxType;
import com.Kitowski.timetable.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

@SuppressWarnings("unused")
public class Settings extends Activity {
	public static boolean displayLegend, selectYear;
	
	private Checkbox legendCheckbox, yearCheckbox;
	private Spinner yearSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		legendCheckbox = new Checkbox(this, checkboxType.LEGEND, (CheckBox)findViewById(R.id.checkbox_displaylegend));
		yearCheckbox = new Checkbox(this, checkboxType.SELECT_YEAR, (CheckBox)findViewById(R.id.checkbox_displayyear));
		
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
	
	public void updateSpinnersState() {
		yearSpinner.setEnabled(yearCheckbox.isChecked());
	}
	
	private void updateSelectYearSpinnerValues() {
		yearSpinner = (Spinner)findViewById(R.id.spinner_selectYear);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(getIntent().getStringArrayExtra("groups"));
		yearSpinner.setAdapter(spinnerArrayAdapter);
		yearSpinner.setEnabled(yearCheckbox.isChecked());
	}
}
