package com.maciejkitowski.timetable.Schedule.Group;

import android.util.Log;

import org.json.JSONObject;

public class Group {
    private static final String logcat = "Group";
    private String name;

    public Group(JSONObject json) throws Exception {
        Log.i(logcat, "Initialize");
        formatData(json);
    }

    private void formatData(JSONObject object) throws Exception {
        Log.i(logcat, "Format received data");

        if(object.has("name")) name = object.getString("name");
    }

    public String toString() {
        return name;
    }
}
