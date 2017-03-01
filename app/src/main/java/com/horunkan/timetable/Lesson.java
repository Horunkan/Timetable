package com.horunkan.timetable;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.widget.TextView;

import com.horunkan.timetable.utilities.DateParser;

public class Lesson extends TextView {
    private static final String logcat = "Lesson";
    private String startTime, endTime, title, description;
    private GradientDrawable background;
    
    public Lesson(Timetable activity, String rawLesson) {
        super(activity);

        formatString(rawLesson);
        createBackground();

        this.setText(String.format("%s—%s %s", startTime, endTime, title));
        this.setPadding(15, 15, 15, (int)DateParser.getDurationMin(startTime, endTime));
        this.setBackground(background);
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

    private void createBackground() {
        background = new GradientDrawable();
        background.setColor(Color.GREEN);
        background.setStroke(4, Color.LTGRAY);
    }
}
