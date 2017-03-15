package com.horunkan.timetable.Timestamp;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.util.Log;

import com.horunkan.timetable.Timetable;

public class ActiveTimeLine extends TimestampLine {
    private static final String logcat = "ActiveTimeLine";

    private static final int lineColor = Color.RED;
    private static final int lineSize = 4;

    public ActiveTimeLine(Timetable activity) {
        super(activity, 1, lineSize, lineColor);
        Log.i(logcat, "Initialize ActiveTimeLine");

        Calendar cal = Calendar.getInstance();
        Log.i(logcat, String.format("Active time: %02d:%02d", cal.get(Calendar.HOUR_OF_DAY) + 1, cal.get(Calendar.MINUTE)));
        float positionHour = (cal.get(Calendar.HOUR_OF_DAY) - 8 + 1) * Timestamp.hourHeight;
        float position = positionHour + (cal.get(Calendar.MINUTE) * 2);
        Log.i(logcat, String.format("Expected position hour only/summary: %.3f/%.3f", positionHour, position));

        canvas.drawLine(0, lineSize/2, windowWidth, lineSize/2, paint);
        imageView.setTranslationY(position);
        Log.i(logcat, "Finished ActiveTimeLine initialization");
    }
}
