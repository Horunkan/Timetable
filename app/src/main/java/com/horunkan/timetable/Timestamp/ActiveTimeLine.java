package com.horunkan.timetable.Timestamp;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.util.Log;

import com.horunkan.timetable.Timetable;

import java.util.Timer;
import java.util.TimerTask;

public class ActiveTimeLine extends TimestampLine {
    private static final String logcat = "ActiveTimeLine";
    private static final int lineColor = Color.RED;
    private static final int lineSize = 4;

    private Timer timer;
    private Calendar calendar;

    public ActiveTimeLine(Timetable activity) {
        super(activity, 1, lineSize, lineColor);
        Log.i(logcat, "Initialize ActiveTimeLine");

        calendar = Calendar.getInstance();
        canvas.drawLine(0, lineSize/2, windowWidth, lineSize/2, paint);
        setPosition();
        Log.i(logcat, "Finished ActiveTimeLine initialization");

        createDelayMovement(calendar.get(Calendar.SECOND));
    }

    private void setPosition() {
        calendar = Calendar.getInstance();
        Log.i(logcat, String.format("Active time: %02d:%02d", calendar.get(Calendar.HOUR_OF_DAY) + 1, calendar.get(Calendar.MINUTE)));

        if(calendar.get(Calendar.HOUR_OF_DAY) + 1 >= Timestamp.endHour + 1) timer.cancel();
        else {
            float positionHour = (calendar.get(Calendar.HOUR_OF_DAY) - 8 + 1) * Timestamp.hourHeight;
            float position = positionHour + (calendar.get(Calendar.MINUTE) * 2);
            Log.i(logcat, String.format("Expected position hour only/summary: %.3f/%.3f", positionHour, position));
            imageView.setTranslationY(position);
        }
    }

    private void createDelayMovement(int secondsToMinute) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() { @Override public void run() { setPosition(); }}, (60 - secondsToMinute) * 1000, 1000 * 60);
    }
}
