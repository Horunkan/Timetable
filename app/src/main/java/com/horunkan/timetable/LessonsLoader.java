package com.horunkan.timetable;

import android.util.Log;

import com.horunkan.timetable.utilities.HttpLoader;

import java.util.ArrayList;

public class LessonsLoader {
    private static final String logcat = "LessonsLoader";
    private static final String logcatVal = logcat + "-value";

    public LessonsLoader(Timetable activity, ArrayList<String> dates) {
    loadLessons("2017-03-04");
    }

    private void loadLessons(String date) {
        HttpLoader http = (HttpLoader) new HttpLoader().execute(String.format("https://inf.ug.edu.pl/plan-%s.print", date));

        try {
            ArrayList<String> toConvert = http.get();
            toConvert = getLessonsContentOnly(toConvert);
        }
        catch (Exception e) {
            Log.e(logcat, e.getMessage());
        }
    }

    private ArrayList<String> getLessonsContentOnly(ArrayList<String> arr) {
        ArrayList<String> buffer = new ArrayList<>();
        Boolean lessonContent = false;

        Log.i(logcat, "Start array filtering");
        for(String line : arr) {
            if(line.matches("<ul>")) lessonContent = true;
            if(lessonContent) buffer.add(line);
            if(line.matches("</ul>")) lessonContent = false;
        }

        for(String str : buffer) Log.i(logcatVal, str);
        Log.i(logcat, String.format("Array filtering finished\nLines left: %d\nLines removed: %d", buffer.size(), arr.size() - buffer.size()));

        return buffer;
    }
}
