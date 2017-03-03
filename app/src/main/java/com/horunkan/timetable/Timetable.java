package com.horunkan.timetable;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.horunkan.timetable.Date.DateSpinner;
import com.horunkan.timetable.Date.MeetingsDates;
import com.horunkan.timetable.Lesson.FreeTime;
import com.horunkan.timetable.Lesson.Lesson;
import com.horunkan.timetable.Lesson.LessonsLoader;
import com.horunkan.timetable.utilities.GroupSpinner;
import com.horunkan.timetable.utilities.RefreshButton;
import com.horunkan.timetable.utilities.Timestamp;
import com.horunkan.timetable.utilities.TimestampLine;

import java.util.List;

public class Timetable extends AppCompatActivity {
    private static final String logcat = "Timetable";

    private RefreshButton refreshButton;
    private GroupSpinner group;
    private DateSpinner date;
    private LinearLayout lessonLayout;
    private LessonsLoader lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.i(logcat, "Start application");

        refreshButton = new RefreshButton(this);
        group = new GroupSpinner(this);
        lessonLayout = (LinearLayout)findViewById(R.id.LessonLayout);

        refresh();
    }

    public void refresh() {
        if(isConnectedToInternet()) {
            MeetingsDates meetingsDates = new MeetingsDates(this);
            TimestampLine.init(this);
            new Timestamp(this);

            date = new DateSpinner(this, meetingsDates.get());
            refreshSelectedDate();
        }
        else refreshButton.display();
    }

    public void refreshSelectedDate() {
        lessons = new LessonsLoader(this, date.get());
        refreshLessons();
    }

    public void refreshLessons() {
        lessonLayout.removeAllViewsInLayout();
        List<Lesson> buffer = lessons.getLessons(group.get());

        lessonLayout.addView(new FreeTime(this, "8:00", buffer.get(0).getStartTime()));
        lessonLayout.addView(buffer.get(0));
        for(int i = 1; i < buffer.size(); ++i) {
            lessonLayout.addView(new FreeTime(this, buffer.get(i-1).getEndTime(), buffer.get(i).getStartTime()));
            lessonLayout.addView(buffer.get(i));
        }
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timetable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_timetable_group) {
            Log.i(logcat, "Selected 'select group' menu item");
            return true;
        }
        else if(item.getItemId() == R.id.menu_timetable_calendar) {
            Log.i(logcat, "Selected 'select calendar' menu item");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
