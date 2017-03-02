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

            LinearLayout la = (LinearLayout)findViewById(R.id.LessonLayout);

            //refreshButton.display();

            la.addView(new Lesson(this, "8:00—9:00, Lekcja 1, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003"));
            //la.addView(new Lesson(this, "9:10—10:00, Lekcja 2, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003"));
            //la.addView(new Lesson(this, "10:30—12:00, Lekcja 3, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003"));
            //la.addView(new Lesson(this, "12:05—15:00, Lekcja 4, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003"));
            //la.addView(new Lesson(this, "16:30—19:40, Lekcja 5, laboratorium, dr Naucz1, gr. 1, 000, mgr Naucz2, gr. 2, 001, mgr Naucz3, gr. 3, 003"));
        }
        else refreshButton.display();
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
