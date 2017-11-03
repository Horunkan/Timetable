package com.maciejkitowski.timetable.Date;

import android.content.Context;

import java.util.ArrayList;

interface ILoader {
    void load(Context context);
    ArrayList<String> getJson();
}
