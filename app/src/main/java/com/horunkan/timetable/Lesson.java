package com.horunkan.timetable;

import android.util.Log;
import android.widget.TextView;

public class Lesson extends TextView {
    private static final String logcat = "Lesson";
    private String startTime, endTime, title, description;


    public Lesson(Timetable activity, String rawLesson) {
        super(activity);

        formatString(rawLesson);

        this.setText(String.format("%s—%s %s", startTime, endTime, title));
        this.setPadding(15, 15, 15, 15);
    }

    private void formatString(String rawLesson) {
        String lessonData[] = rawLesson.split(",");

        startTime = lessonData[0].split("—")[0];
        endTime = lessonData[0].split("—")[1];
        title = lessonData[1];
        description = formatDetails(lessonData);

        Log.i(logcat, String.format("Start: %s, End: %s, Title: %s, Descrpition: %s", startTime, endTime, title, description));
    }

    private String formatDetails(String lessonData[]) {
        String buffer = lessonData[2] + "\n";

        for(int i = 3; i < lessonData.length; ++i) {
            if(lessonData[i].contains("gr.")) buffer += " - " + lessonData[i]; //group
            else if(lessonData[i].replaceAll("\\s", "").matches("-?\\d+(\\.\\d+)?")) buffer += ", sala: " + lessonData[i] + "\n"; //room
            else buffer += lessonData[i];
        }
        return buffer;
    }
}
