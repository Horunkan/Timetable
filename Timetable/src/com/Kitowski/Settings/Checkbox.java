package com.Kitowski.Settings;

import android.widget.CheckBox;
import android.view.View;
import android.view.View.OnClickListener;

public class Checkbox implements OnClickListener {
	public enum checkboxType { LEGEND, SELECT_YEAR, SELECT_GROUP }
	
	private CheckBox box;
	private SettingsActivity settings;
	private checkboxType type;
	
	public Checkbox(SettingsActivity settings, checkboxType type, CheckBox box) {
		this.box = box;
		this.settings = settings;
		this.type = type;
		box.setOnClickListener(this);
		update();
	}

	@Override
	public void onClick(View v) {
		if(type == checkboxType.LEGEND) SettingsActivity.displayLegend = !SettingsActivity.displayLegend;
		else {
			if(type == checkboxType.SELECT_YEAR) SettingsActivity.selectYear = !SettingsActivity.selectYear;
			else SettingsActivity.selectGroup = !SettingsActivity.selectGroup;
			settings.updateSpinnersState();
		}
	}
	
	private void update() {
		if(type == checkboxType.LEGEND) box.setChecked(SettingsActivity.displayLegend);
		else if(type == checkboxType.SELECT_YEAR) box.setChecked(SettingsActivity.selectYear);
		else if(type == checkboxType.SELECT_GROUP) box.setChecked(SettingsActivity.selectGroup);
	}
	
	public boolean isChecked() { return box.isChecked(); }
}
