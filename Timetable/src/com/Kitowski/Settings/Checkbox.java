package com.Kitowski.Settings;

import android.widget.CheckBox;
import android.view.View;
import android.view.View.OnClickListener;

public class Checkbox implements OnClickListener {
	public enum checkboxType { LEGEND, SELECT_YEAR, SELECT_GROUP }
	
	private CheckBox box;
	private Settings settings;
	private checkboxType type;
	
	public Checkbox(Settings settings, checkboxType type, CheckBox box) {
		this.box = box;
		this.settings = settings;
		this.type = type;
		box.setOnClickListener(this);
		update();
	}

	@Override
	public void onClick(View v) {
		if(type == checkboxType.LEGEND) Settings.displayLegend = !Settings.displayLegend;
		else {
			if(type == checkboxType.SELECT_YEAR) Settings.selectYear = !Settings.selectYear;
			else Settings.selectGroup = !Settings.selectGroup;
			settings.updateSpinnersState();
		}
	}
	
	private void update() {
		if(type == checkboxType.LEGEND) box.setChecked(Settings.displayLegend);
		else if(type == checkboxType.SELECT_YEAR) box.setChecked(Settings.selectYear);
		else if(type == checkboxType.SELECT_GROUP) box.setChecked(Settings.selectGroup);
	}
	
	public boolean isChecked() { return box.isChecked(); }
}
