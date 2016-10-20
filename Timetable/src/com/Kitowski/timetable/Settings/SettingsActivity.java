package com.Kitowski.timetable.Settings;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Settings.Checkbox.checkboxType;

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
public class SettingsActivity extends Activity {
	public static boolean displayLegend, selectYear, selectGroup;
	public static String selectYearValue, selectGroupValueLetter, selectGroupValueNumber;
	
	private Checkbox legendCheckbox, yearCheckbox, groupCheckbox;
	private Spinner yearSpinner, groupSpinnerLetter, groupSpinnerNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		legendCheckbox = new Checkbox(this, checkboxType.LEGEND, (CheckBox)findViewById(R.id.checkbox_displaylegend));
		yearCheckbox = new Checkbox(this, checkboxType.SELECT_YEAR, (CheckBox)findViewById(R.id.checkbox_displayyear));
		groupCheckbox = new Checkbox(this, checkboxType.SELECT_GROUP, (CheckBox)findViewById(R.id.checkbox_selectgroup));
		
		addSelectYearSpinner();
		addSelectGroupLetterSpinner();
		addSelectGroupNumberSpinner();
	}
	
	public static void loadSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		
		displayLegend = pref.getBoolean("Legend", true);
		selectYear = pref.getBoolean("SelectYear", false);
		selectYearValue = pref.getString("SelectYearValue", "NULL");
		selectGroup = pref.getBoolean("SelectGroup", false);
		selectGroupValueLetter = pref.getString("SelectGroupValueLetter", "NULL");
		selectGroupValueNumber = pref.getString("SelectGroupValueNumber", "NULL");
	}
	
	public static void saveSettings(Context con) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor edit = pref.edit();
		
		edit.putBoolean("Legend", displayLegend);
		edit.putBoolean("SelectGroup", selectYear);
		edit.putString("SelectYearValue", selectYearValue);
		edit.putBoolean("SelectGroup", selectGroup);
		edit.putString("SelectGroupValueLetter", selectGroupValueLetter);
		edit.putString("SelectGroupValueNumber", selectGroupValueNumber);
		edit.apply();
	}
	
	@Override
	public void onBackPressed() { new ToSaveAlert(this).show(); }
	
	public void updateSpinnersState() {
		yearSpinner.setEnabled(selectYear);
		groupSpinnerLetter.setEnabled(selectGroup);
		groupSpinnerNumber.setEnabled(selectGroup);
	}
	
	private void addSelectYearSpinner() {
		yearSpinner = (Spinner)findViewById(R.id.spinner_selectYear);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(getIntent().getStringArrayExtra("groups"));
		yearSpinner.setAdapter(spinnerArrayAdapter);
		yearSpinner.setEnabled(yearCheckbox.isChecked());
		
		for(int i = 0; i < spinnerArrayAdapter.getCount(); ++i) {
			if(spinnerArrayAdapter.getItem(i).contentEquals(selectYearValue)) {
				yearSpinner.setSelection(i);
				break;
			}
		}
		
		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selectYearValue = (String)yearSpinner.getSelectedItem(); }
			@Override public void onNothingSelected(AdapterView<?> parent) { }
		});
	}
	
	private void addSelectGroupLetterSpinner() {
		groupSpinnerLetter = (Spinner)findViewById(R.id.spinner_selectgroup_letter);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(new String[] {"Grupa A", "Grupa B", "Grupa C"});
		groupSpinnerLetter.setAdapter(spinnerArrayAdapter);
		groupSpinnerLetter.setEnabled(groupCheckbox.isChecked());
		
		for(int i = 0; i < spinnerArrayAdapter.getCount(); ++i) {
			if(spinnerArrayAdapter.getItem(i).contentEquals(selectGroupValueLetter)) {
				groupSpinnerLetter.setSelection(i);
				break;
			}
		}
		
		groupSpinnerLetter.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selectGroupValueLetter = (String)groupSpinnerLetter.getSelectedItem(); }
			@Override public void onNothingSelected(AdapterView<?> parent) { }
		});
	}
	
	private void addSelectGroupNumberSpinner() {
		groupSpinnerNumber = (Spinner)findViewById(R.id.spinner_selectgroup_number);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(new String[] {"Grupa 1", "Grupa 2", "Grupa 3", "Grupa 4"});
		groupSpinnerNumber.setAdapter(spinnerArrayAdapter);
		groupSpinnerNumber.setEnabled(groupCheckbox.isChecked());
		
		for(int i = 0; i < spinnerArrayAdapter.getCount(); ++i) {
			if(spinnerArrayAdapter.getItem(i).contentEquals(selectGroupValueNumber)) {
				groupSpinnerNumber.setSelection(i);
				break;
			}
		}
		
		groupSpinnerNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selectGroupValueNumber = (String)groupSpinnerNumber.getSelectedItem(); }
			@Override public void onNothingSelected(AdapterView<?> parent) { }
		});
	}
}
