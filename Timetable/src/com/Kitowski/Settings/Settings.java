package com.Kitowski.Settings;

import com.Kitowski.Settings.Checkbox.checkboxType;
import com.Kitowski.timetable.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

@SuppressWarnings("unused")
public class Settings extends Activity {
	public static boolean displayLegend, selectGroup;
	public static String selectGroupValue;
	
	private Checkbox legendCheckbox, yearCheckbox;
	private Spinner groupSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		legendCheckbox = new Checkbox(this, checkboxType.LEGEND, (CheckBox)findViewById(R.id.checkbox_displaylegend));
		yearCheckbox = new Checkbox(this, checkboxType.SELECT_YEAR, (CheckBox)findViewById(R.id.checkbox_displayyear));
		
		addSelectYearSpinner();
	}
	
	public static void loadSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		
		displayLegend = pref.getBoolean("Legend", true);
		selectGroup = pref.getBoolean("SelectYear", false);
		selectGroupValue = pref.getString("SelectYearValue", "NULL");
	}
	
	public static void saveSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor edit = pref.edit();
		
		edit.putBoolean("Legend", displayLegend);
		edit.putBoolean("SelectGroup", selectGroup);
		edit.putString("SelectGroupValue", selectGroupValue);
		edit.apply();
	}
	
	@Override
	public void onBackPressed() { new ToSaveAlert(this).show(); }
	
	public void updateSpinnersState() {
		groupSpinner.setEnabled(yearCheckbox.isChecked());
	}
	
	private void addSelectYearSpinner() {
		groupSpinner = (Spinner)findViewById(R.id.spinner_selectYear);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(getIntent().getStringArrayExtra("groups"));
		groupSpinner.setAdapter(spinnerArrayAdapter);
		groupSpinner.setEnabled(yearCheckbox.isChecked());
		
		for(int i = 0; i < spinnerArrayAdapter.getCount(); ++i) {
			if(spinnerArrayAdapter.getItem(i).contentEquals(selectGroupValue)) {
				groupSpinner.setSelection(i);
				break;
			}
		}
		
		groupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selectGroupValue = (String)groupSpinner.getSelectedItem(); }
			@Override public void onNothingSelected(AdapterView<?> parent) { }
		});
	}
}
