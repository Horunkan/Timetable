package com.Kitowski.timetable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.CalendarContract;

public class SelectCalendarAlert extends AlertDialog.Builder implements DialogInterface.OnClickListener {
    public static int selectedCalendar = 0;

    public SelectCalendarAlert(Timetable timetable, Cursor calendars) {
        super(timetable);
        this.setTitle(R.string.eventExists_title);

        this.setSingleChoiceItems(calendars, selectedCalendar, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, this);
        this.setCancelable(false);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        selectedCalendar = ++which;
        dialog.dismiss();
    }
}
