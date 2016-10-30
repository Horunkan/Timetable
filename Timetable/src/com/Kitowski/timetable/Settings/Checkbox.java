package com.Kitowski.timetable.Settings;

import android.widget.CheckBox;

import com.Kitowski.timetable.Settings.Settings.Setting;

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
		if(type == checkboxType.LEGEND) Settings.toggleBoolean(Setting.DISPLAY_LEGEND);
		else {
			if(type == checkboxType.SELECT_YEAR) Settings.toggleBoolean(Setting.SELECT_GROUP);
			else Settings.toggleBoolean(Setting.SELECT_SUBGROUP);
			settings.updateSpinnersState();
		}
	}
	
	private void update() {
		if(type == checkboxType.LEGEND) box.setChecked(Settings.getBoolean(Setting.DISPLAY_LEGEND));
		else if(type == checkboxType.SELECT_YEAR) box.setChecked(Settings.getBoolean(Setting.SELECT_GROUP));
		else if(type == checkboxType.SELECT_GROUP) box.setChecked(Settings.getBoolean(Setting.SELECT_SUBGROUP));
	}
	
	public boolean isChecked() { return box.isChecked(); }
}
