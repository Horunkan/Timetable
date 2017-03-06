package com.horunkan.timetable.Timestamp;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

public class Timestamp {
    public static final int hourHeight = 60 * 2;

    private final int startHour = 8;
    private final int endHour = 19;
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

        addLines(activity, endHour - startHour);
    }

    private void addLines(Timetable activity, int lineCount) {
        for(int i = 0; i <= lineCount; ++i) new TimestampLine(activity, i * hourHeight);
    }
}
