package com.Kitowski.timetable.Settings;

import com.Kitowski.timetable.R;
import com.Kitowski.timetable.Settings.Checkbox.checkboxType;
import com.Kitowski.timetable.Settings.Settings.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

@SuppressWarnings("unused")
public class SettingsActivity extends Activity {
	private Checkbox legendCheckbox, groupCheckbox, subgroupCheckbox;
	private Spinner groupSpinner, subgroupSpinnerLetter, subgroupSpinnerNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		
		legendCheckbox = new Checkbox(this, checkboxType.LEGEND, (CheckBox)findViewById(R.id.checkbox_displaylegend));
		groupCheckbox = new Checkbox(this, checkboxType.SELECT_YEAR, (CheckBox)findViewById(R.id.checkbox_displayyear));
		subgroupCheckbox = new Checkbox(this, checkboxType.SELECT_GROUP, (CheckBox)findViewById(R.id.checkbox_selectgroup));
		
		addGroupYearSpinner();
		addSelectGroupLetterSpinner();
		addSelectGroupNumberSpinner();
	}
	
	@Override
	public void onBackPressed() { new ToSaveAlert(this).show(); }
	
	public void updateSpinnersState() {
		groupSpinner.setEnabled(Settings.getBoolean(Setting.SELECT_GROUP));
		subgroupSpinnerLetter.setEnabled(Settings.getBoolean(Setting.SELECT_SUBGROUP));
		subgroupSpinnerNumber.setEnabled(Settings.getBoolean(Setting.SELECT_SUBGROUP));
	}
	
	private void addGroupYearSpinner() {
		groupSpinner = (Spinner)findViewById(R.id.spinner_selectYear);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(getResources().getStringArray(R.array.studyGroups));
		groupSpinner.setAdapter(spinnerArrayAdapter);
		groupSpinner.setEnabled(groupCheckbox.isChecked());
		groupSpinner.setSelection(Settings.getInt(Setting.SELECTED_GROUP));
		
		groupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { Settings.setInt(Setting.SELECTED_GROUP, groupSpinner.getSelectedItemPosition()); }
			@Override public void onNothingSelected(AdapterView<?> parent) { }
		});
	}
	
	private void addSelectGroupLetterSpinner() {
		subgroupSpinnerLetter = (Spinner)findViewById(R.id.spinner_selectgroup_letter);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(new String[] {"Grupa A", "Grupa B", "Grupa C"});
		subgroupSpinnerLetter.setAdapter(spinnerArrayAdapter);
		subgroupSpinnerLetter.setEnabled(subgroupCheckbox.isChecked());
		subgroupSpinnerLetter.setSelection(Settings.getInt(Setting.SELECTED_GROUP_LETT));

		subgroupSpinnerLetter.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { Settings.setInt(Setting.SELECTED_GROUP_LETT, subgroupSpinnerLetter.getSelectedItemPosition()); }
			@Override public void onNothingSelected(AdapterView<?> parent) { }
		});
	}
	
	private void addSelectGroupNumberSpinner() {
		subgroupSpinnerNumber = (Spinner)findViewById(R.id.spinner_selectgroup_number);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.addAll(new String[] {"Grupa 1", "Grupa 2", "Grupa 3", "Grupa 4"});
		subgroupSpinnerNumber.setAdapter(spinnerArrayAdapter);
		subgroupSpinnerNumber.setEnabled(subgroupCheckbox.isChecked());
		subgroupSpinnerNumber.setSelection(Settings.getInt(Setting.SELECTED_GROUP_NUM));

		subgroupSpinnerNumber.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { Settings.setInt(Setting.SELECTED_GROUP_NUM, subgroupSpinnerNumber.getSelectedItemPosition()); }
			@Override public void onNothingSelected(AdapterView<?> parent) { }
		});
	}
}
