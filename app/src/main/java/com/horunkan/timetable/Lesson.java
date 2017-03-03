package com.horunkan.timetable;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.horunkan.timetable.utilities.DateParser;
import com.horunkan.timetable.utilities.Timestamp;

public class Lesson extends TextView implements View.OnClickListener {
    private static final String logcat = "Lesson";
    private String startTime, endTime, title, description;
    private GradientDrawable background;
    private Timetable activity;
    
    public Lesson(Timetable activity, String rawLesson) {
        super(activity);
        this.activity = activity;

        if(rawLesson.contains("brak zajęć")) {
            startTime = "8:00";
            endTime = "19:00";
            title = activity.getResources().getString(R.string.info_noLesson);
            description = activity.getResources().getString(R.string.info_noLesson);
        }
        else {
            formatString(rawLesson);
            createBackground();
        }

        int duration = (int)DateParser.getDurationMin(startTime, endTime);
        int height = Timestamp.hourHeight * (duration/Timestamp.hourHeight) + (duration%Timestamp.hourHeight);

        this.setText(String.format("%s—%s %s", startTime, endTime, title));
        this.setTextSize(13f);
        this.setPadding(15, 15, 15, 15);
        this.setHeight(height * 2);
        this.setBackground(background);
        this.setOnClickListener(this);

        Log.i(logcat, String.format("Start: %s, End: %s, Title: %s, Descrpition: %s", startTime, endTime, title, description));
    }

    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    private void formatString(String rawLesson) {
        String lessonData[] = rawLesson.split(",");

        startTime = lessonData[0].split("—")[0];
        endTime = lessonData[0].split("—")[1];
        title = lessonData[1];
        description = formatDetails(lessonData);
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

    @Override public void onClick(View v) { displayDetails(); }

    private void displayDetails() {
        Log.i(logcat, String.format("Display description of lesson: %s", title));

        AlertDialog.Builder det  = new AlertDialog.Builder(activity);
        det.setTitle(title);
        det.setMessage(description);
        det.setCancelable(true);
        det.create().show();
    }
}
