package com.horunkan.timetable.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.util.Log;

import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

public class SelectCalendar extends AlertDialog.Builder implements DialogInterface.OnClickListener {
    private static final String logcat = "SelectCalendar";
    private static int selectedCalendar = 0;

    private static Boolean firstRun = false;

    public SelectCalendar(Timetable activity) {
        super(activity);

        this.setSingleChoiceItems(CalendarHelper.getAvailableCalendars(activity), selectedCalendar, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, this);
        this.setTitle(activity.getResources().getString(R.string.selectCalendar_title));
        if(firstRun) this.setCancelable(false);
    }

    public static void load(Timetable activity) {
        Log.i(logcat, "Load SelectCalendar prefs");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);

        firstRun = pref.getBoolean("FirstRun_Calendar", true);
        selectedCalendar = pref.getInt("DefaultCalendar_ID", 2);

        if(firstRun) new SelectCalendar(activity).create().show();
        Log.i(logcat, "SelectCalendar prefs loaded");
    }

    public static int get() { return selectedCalendar; }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.i(logcat, String.format("Selected calendar ID: %d", which+1));
        selectedCalendar = ++which;
        dialog.dismiss();
    }
}
