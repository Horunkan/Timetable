package com.horunkan.timetable.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

import java.util.ArrayList;

public class DeleteEvents extends AlertDialog.Builder  implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    private static final String logcat = "DeleteEvents";


    public DeleteEvents(Timetable activity, ArrayList<String> events) {
        super(activity);

        this.setTitle(activity.getResources().getString(R.string.DeleteEvents_title));
        this.setPositiveButton(activity.getResources().getString(R.string.DeleteEvents_confirm), this);
        this.setNegativeButton(activity.getResources().getString(R.string.DeleteEvents_cancel), this);

        boolean[] boolBuffer = new boolean[events.size()];
        String[] buffer = new String[events.size()];
        for(int i = 0; i < events.size(); ++i) buffer[i] = events.get(i).split(",")[1];
        for(int i = 0; i < events.size(); ++i) boolBuffer[i] = true;

        this.setMultiChoiceItems(buffer, boolBuffer, this);
    }

    @Override  //Multi check
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

    }

    @Override //Buttons
    public void onClick(DialogInterface dialog, int which) {
        if(which == AlertDialog.BUTTON_POSITIVE) {
            Log.i(logcat, "Pressed confirm");
        }
        else if(which == AlertDialog.BUTTON_NEGATIVE) {
            Log.i(logcat, "Pressed cancel");
        }
    }
}
