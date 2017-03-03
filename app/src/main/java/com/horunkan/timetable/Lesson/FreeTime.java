package com.horunkan.timetable.Lesson;

import android.util.Log;
import android.widget.TextView;

import com.horunkan.timetable.Timetable;
import com.horunkan.timetable.utilities.DateParser;
import com.horunkan.timetable.utilities.Timestamp;

public class FreeTime extends TextView {
    private static final String logcat = "FreeTime";

    public FreeTime(Timetable activity, String lastLessonEndTime, String nextLessonStartTime) {
        super(activity);

        int duration = (int)DateParser.getDurationMin(lastLessonEndTime, nextLessonStartTime);
        int height = Timestamp.hourHeight * (duration/Timestamp.hourHeight) + (duration%Timestamp.hourHeight);
        this.setPadding(15, 15, 15, 15);
        this.setHeight(height * 2);

        Log.i(logcat, String.format("Start: %s, End: %s, Duration: %d", lastLessonEndTime, nextLessonStartTime, duration));
    }
}
