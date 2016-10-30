package com.Kitowski.timetable.Settings;

import android.widget.CheckBox;

import com.Kitowski.timetable.Settings.Settings.Setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

public class Checkbox extends CheckBox implements OnClickListener {
	public enum checkboxType { LEGEND, SELECT_GROUP, SELECT_SUBGROUP }
	
	private SettingsActivity settings;
	private checkboxType type;
	
	public Checkbox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(type == checkboxType.LEGEND) Settings.toggleBoolean(Setting.DISPLAY_LEGEND);
		else {
			if(type == checkboxType.SELECT_GROUP) Settings.toggleBoolean(Setting.SELECT_GROUP);
			else Settings.toggleBoolean(Setting.SELECT_SUBGROUP);
			settings.updateSpinnersState();
		}
	}
	
	public void update(SettingsActivity settings, checkboxType type) {
		this.settings = settings;
		this.type = type;
		
		if(type == checkboxType.LEGEND) this.setChecked(Settings.getBoolean(Setting.DISPLAY_LEGEND));
		else if(type == checkboxType.SELECT_GROUP) this.setChecked(Settings.getBoolean(Setting.SELECT_GROUP));
		else if(type == checkboxType.SELECT_SUBGROUP) this.setChecked(Settings.getBoolean(Setting.SELECT_SUBGROUP));
	}
	
	//public boolean isChecked() { return box.isChecked(); }
}
