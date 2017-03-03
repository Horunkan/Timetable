package com.horunkan.timetable;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.horunkan.timetable.utilities.Timestamp;
import com.horunkan.timetable.utilities.TimestampLine;

public class Timetable extends AppCompatActivity {
    private RefreshButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        refreshButton = new RefreshButton(this);
        new GroupSpinner(this);

        refresh();
    }

    public void refresh() {
        if(isConnectedToInternet()) {
            MeetingsDates meetingsDates = new MeetingsDates(this);
            TimestampLine.init(this);

            new Timestamp(this);
            new DateSpinner(this, meetingsDates.get());
            new LessonsLoader(this, meetingsDates.get());

            LinearLayout lessonLayout = (LinearLayout)findViewById(R.id.LessonLayout);


            //Lesson[] testLessons = new Lesson[5];
            //testLessons[0] = new Lesson(this, "8:00—9:00, Lekcja 1, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003");
            //testLessons[1] = new Lesson(this, "9:10—10:00, Lekcja 2, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003");
            //testLessons[2] = new Lesson(this, "10:30—12:00, Lekcja 3, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003");
            //testLessons[3] = new Lesson(this, "12:05—15:00, Lekcja 4, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003");
            //testLessons[4] = new Lesson(this, "16:00—19:40, Lekcja 5, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003");

            //lessonLayout.addView(testLessons[0]);
            /*for(int i = 1; i < 5; ++i) {
                lessonLayout.addView(new FreeTime(this, testLessons[i-1].getEndTime(), testLessons[i].getStartTime()));
                lessonLayout.addView(testLessons[i]);
            }*/
        }
        else refreshButton.display();
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
