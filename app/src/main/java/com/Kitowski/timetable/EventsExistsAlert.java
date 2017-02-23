package com.Kitowski.timetable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.Kitowski.timetable.DeleteEvents.DeleteEvents;
import com.Kitowski.timetable.date.SelectDate;

public class EventsExistsAlert extends AlertDialog.Builder implements DialogInterface.OnClickListener {
	private final Timetable timetable;
	private final SelectDate selectDate;
	
	public EventsExistsAlert(final Timetable timetable, final SelectDate selectDate) {
		super(timetable);
		this.timetable = timetable;
		this.selectDate = selectDate;
		this.setTitle(R.string.eventExists_title);
		this.setMessage(R.string.eventExists_message);
		this.setNegativeButton(R.string.eventExists_deleteall, this);
		this.setNeutralButton(R.string.eventExists_ignore, this);
		this.setPositiveButton(R.string.eventExists_deleteselect, this);
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which == DialogInterface.BUTTON_POSITIVE) {
			Intent intent = new Intent(timetable, DeleteEvents.class);
			intent.putExtra("date", selectDate.getSelected());
			timetable.startActivityForResult(intent,DeleteEvents.requestCode);
		}
		else {
			if(which == DialogInterface.BUTTON_NEGATIVE) timetable.deleteAllEvents();
			timetable.addEvents();
		}
	}
}
