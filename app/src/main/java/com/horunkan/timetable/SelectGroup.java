package com.horunkan.timetable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SelectGroup extends AlertDialog.Builder implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    private static String logcat = "SelectGroup";
    private static String logcatVal = logcat + "-value";
    private static final String[] groupLetters = {"A", "B", "C"};
    private static final String[] groupNumbers = {"1", "2", "3", "4"};

    private static Boolean firstRun = false;
    private static Boolean[] selected = new Boolean[groupLetters.length + groupNumbers.length];

    public SelectGroup(Timetable activity) {
        super(activity);
        Log.i(logcat, "Display menu");

        this.setTitle(activity.getResources().getString(R.string.selectGroup_title));
        setChoices();
        this.setPositiveButton(activity.getResources().getString(R.string.selectGroup_confirm), this);
        if(firstRun) this.setCancelable(false);
    }

    public static void load(Timetable activity) {
        Log.i(logcat, "Load SelectGroup prefs");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        firstRun = pref.getBoolean("FirstRun_Group", true);

        int lettersSize = groupLetters.length;
        int numberSize = groupNumbers.length;

        for(int i = 0; i < lettersSize; ++i) selected[i] = pref.getBoolean(String.format("DisplayGroup %s", groupLetters[i]), true);
        for(int i = lettersSize; i < lettersSize + numberSize; ++i) selected[i] = pref.getBoolean(String.format("DisplayGroup %s", groupNumbers[i - lettersSize]), true);

        Log.i(logcatVal, "Loaded values:");
        for(Boolean bool : selected) Log.i(logcatVal, bool.toString());
        if(firstRun) new SelectGroup(activity).create().show();
    }

    private void setChoices() {
        String[] buffer = new String[groupLetters.length + groupNumbers.length];

        for(int i = 0; i < groupLetters.length; ++i) buffer[i] = String.format("Grupa %s", groupLetters[i]);
        for(int i = groupLetters.length; i < groupLetters.length + groupNumbers.length; ++i) buffer[i] = String.format("Grupa %s", groupNumbers[i - groupLetters.length]);
        Log.i(logcatVal, "Available groups:");
        for(String str : buffer) Log.i(logcatVal, str);

        boolean[] boolBuffer = new boolean[selected.length];
        for(int i = 0; i < boolBuffer.length; ++i) boolBuffer[i] = selected[i].booleanValue();

        this.setMultiChoiceItems(buffer, boolBuffer, this);
    }

    @Override //Multicheck
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        Log.i(logcat, String.format("Changed value: index - %d, value: %s", which, isChecked));
        selected[which] = isChecked;
    }

    @Override //Confirm button
    public void onClick(DialogInterface dialog, int which) {
        Log.i(logcat, "Pressed confirm button");
    }
}
