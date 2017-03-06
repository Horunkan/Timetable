package com.horunkan.timetable.Group;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.horunkan.timetable.Lesson.Lesson;
import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

public class SelectGroup extends AlertDialog.Builder implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    private static String logcat = "SelectGroup";
    private static String logcatVal = logcat + "-value";
    private static final String[] groupLetters = {"A", "B", "C"};
    private static final String[] groupNumbers = {"1", "2", "3", "4"};

    private static Boolean firstRun = false;
    private static Boolean[] selected = new Boolean[groupLetters.length + groupNumbers.length];

    private Timetable activity;

    public SelectGroup(Timetable activity) {
        super(activity);
        this.activity = activity;
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
        Log.i(logcat, "SelectGroup prefs loaded");
    }

    public static void save(Timetable activity) {
        Log.i(logcat, "Save SelectGroup prefs");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor edit = pref.edit();
        int lettersSize = groupLetters.length;
        int numberSize = groupNumbers.length;

        edit.putBoolean("FirstRun_Group", false);
        for(int i = 0; i < lettersSize; ++i) edit.putBoolean(String.format("DisplayGroup %s", groupLetters[i]), selected[i]);
        for(int i = 0; i < numberSize; ++i) edit.putBoolean(String.format("DisplayGroup %s", groupNumbers[i]), selected[i + lettersSize]);

        edit.apply();
        Log.i(logcat, "SelectGroup prefs saved");
    }

    public static Boolean canAddLesson(Lesson lesson) {
        String lessonDescription = lesson.getDescription();

        if(lessonDescription.contains("gr.")) {
            for(int i = 0; i < groupLetters.length; ++i) {
                if(selected[i]) {
                    if(lessonDescription.contains(String.format(" %s,", groupLetters[i]))) return true;
                    else if(lessonDescription.contains(String.format(" %s ", groupLetters[i]))) return true;
                    else if(lessonDescription.contains(String.format(" i %s", groupLetters[i]))) return true;
                    else if(lessonDescription.contains(String.format("%s i ", groupLetters[i]))) return true;
                }
            }

            for(int i = 0; i < groupNumbers.length; ++i) {
                if(selected[i + groupLetters.length]) {
                    if(lessonDescription.contains(String.format(" %s,", groupNumbers[i]))) return true;
                    else if(lessonDescription.contains(String.format(" %s ", groupNumbers[i]))) return true;
                    else if(lessonDescription.contains(String.format(" i %s", groupNumbers[i]))) return true;
                    else if(lessonDescription.contains(String.format("%s i ", groupNumbers[i]))) return true;
                }
            }
        }
        else return true;

        return false;
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

    @Override //Multi check
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        Log.i(logcat, String.format("Changed value: index - %d, value: %s", which, isChecked));
        selected[which] = isChecked;
    }

    @Override //Confirm button
    public void onClick(DialogInterface dialog, int which) {
        Log.i(logcat, "Pressed confirm button");
        activity.refreshLessons();
        save(activity);
    }
}
