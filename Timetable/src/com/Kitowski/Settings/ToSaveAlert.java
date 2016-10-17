package com.Kitowski.Settings;

import com.Kitowski.timetable.R;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class ToSaveAlert  extends AlertDialog.Builder implements DialogInterface.OnClickListener {
	public ToSaveAlert(Settings sett) {
		super(sett);
		
		this.setMessage(R.string.toSave_message);
		this.setPositiveButton(R.string.toSave_yes, this);
		this.setNegativeButton(R.string.toSave_no, this);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which == DialogInterface.BUTTON_POSITIVE) {
			
		}
		else {
			
		}
	}
}
