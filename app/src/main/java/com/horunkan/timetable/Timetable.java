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

import com.horunkan.timetable.Calendar.CalendarButton;
import com.horunkan.timetable.Calendar.CalendarHelper;
import com.horunkan.timetable.Calendar.DeleteEvents;
import com.horunkan.timetable.Calendar.SelectCalendar;
import com.horunkan.timetable.Date.DateSpinner;
import com.horunkan.timetable.Date.MeetingsDates;
import com.horunkan.timetable.Group.GroupSpinner;
import com.horunkan.timetable.Group.SelectGroup;
import com.horunkan.timetable.Lesson.FreeTime;
import com.horunkan.timetable.Lesson.Lesson;
import com.horunkan.timetable.Lesson.LessonsLoader;
import com.horunkan.timetable.Timestamp.Timestamp;
import com.horunkan.timetable.Timestamp.TimestampLine;
import com.horunkan.timetable.utilities.RefreshButton;

import java.util.ArrayList;
import java.util.List;

public class Timetable extends AppCompatActivity {
    private static final String logcat = "Timetable";

    private RefreshButton refreshButton;
    private CalendarButton calendarButton;
    private GroupSpinner group;
    private DateSpinner date;
    private LinearLayout lessonLayout;
    private LessonsLoader lessons;
    private Timestamp timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.i(logcat, "Start application");
        TimestampLine.init(this);
        SelectCalendar.load(this);
        SelectGroup.load(this);

        refreshButton = new RefreshButton(this);
        calendarButton = new CalendarButton(this);
        group = new GroupSpinner(this);
        lessonLayout = (LinearLayout)findViewById(R.id.LessonLayout);
        timestamp = new Timestamp(this);

        refresh();
    }

    public void refresh() {
        if(isConnectedToInternet()) {
            MeetingsDates meetingsDates = new MeetingsDates(this);
            date = new DateSpinner(this, meetingsDates.get());
            refreshSelectedDate();
        }
        else {
            refreshButton.display();
            calendarButton.hide();
        }
    }

    public void refreshSelectedDate() {
        lessons = new LessonsLoader(this, date.get());
        refreshLessons();
    }

    public void refreshLessons() {
        lessonLayout.removeAllViewsInLayout();

        if(isConnectedToInternet()) {
            List<Lesson> buffer = new ArrayList<>();

            if(lessons.getLessons(group.get()) != null) {
                for(Lesson less : lessons.getLessons(group.get())) if(SelectGroup.canAddLesson(less)) buffer.add(less);

                lessonLayout.addView(new FreeTime(this, "8:00", buffer.get(0).getStartTime()));
                lessonLayout.addView(buffer.get(0));

                for(int i = 1; i < buffer.size(); ++i) {
                    lessonLayout.addView(new FreeTime(this, buffer.get(i-1).getEndTime(), buffer.get(i).getStartTime()));
                    lessonLayout.addView(buffer.get(i));
                }
            }
            else lessonLayout.addView(new Lesson(this, "brak zajęć"));

            calendarButton.display();
        }
        else {
            refreshButton.display();
            calendarButton.hide();
        }
    }

    public void addToCalendar() {
        if(!CalendarHelper.anyEventExists(this, date.get())) {
            List<Lesson> buffer = new ArrayList<>();

            for(Lesson less : lessons.getLessons(group.get())) if(SelectGroup.canAddLesson(less)) buffer.add(less);
            for(int i = 0; i < buffer.size(); ++i) CalendarHelper.addToCalendar(this, date.get(), buffer.get(i), i);
        }
        else {
            new DeleteEvents(this, CalendarHelper.getEvents(this, date.get())).create().show();
        }
    }

    public void forceAddToCalendar() {
        List<Lesson> buffer = new ArrayList<>();

        for(Lesson less : lessons.getLessons(group.get())) if(SelectGroup.canAddLesson(less)) buffer.add(less);
        for(int i = 0; i < buffer.size(); ++i) CalendarHelper.addToCalendar(this, date.get(), buffer.get(i), i);
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
            new SelectGroup(this).create().show();
            return true;
        }
        else if(item.getItemId() == R.id.menu_timetable_calendar) {
            Log.i(logcat, "Selected 'select calendar' menu item");
            new SelectCalendar(this).create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
