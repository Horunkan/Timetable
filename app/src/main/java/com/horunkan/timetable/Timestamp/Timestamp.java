package com.horunkan.timetable.Timestamp;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

public class Timestamp {
    public static final int hourHeight = 60 * 2;
    public static final int startHour = 8;
    public static final int endHour = 19;

    private LinearLayout layout;

    public Timestamp(Timetable activity) {
        layout = (LinearLayout) activity.findViewById(R.id.Timestamp);

        for(int i = startHour; i <= endHour; ++i) {
            TextView buffer = new TextView(activity);

            buffer.setText(String.format("%02d:00", i));
            buffer.setPadding(10, 0, 10, 0);
            buffer.setHeight(hourHeight);
            buffer.setTextSize(13f);
            layout.addView(buffer);
        }

        new TimestampLine(activity, endHour - startHour + 1, 0);
        new ActiveTimeLine(activity);
    }
}
