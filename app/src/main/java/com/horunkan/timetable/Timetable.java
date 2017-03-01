package com.horunkan.timetable;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class Timetable extends AppCompatActivity {
    private RefreshButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        refreshButton = new RefreshButton(this);
        new GroupSpinner(this);

        refresh();
    }

    public void refresh() {
        if(isConnectedToInternet()) {
            MeetingsDates meetingsDates = new MeetingsDates(this);
            new DateSpinner(this, meetingsDates.get());

            new LessonsLoader(this, meetingsDates.get());

            //TODO Change to tableLayout?
            LinearLayout la = (LinearLayout)findViewById(R.id.LessonLayout);

            la.addView(new Lesson(this, "8:00—11:15, Lekcja 1, dr Test, gr. 2, sala: 000"));
            la.addView(new Lesson(this, "11:30—13:00, Lekcja 2, dr Test, gr. 2, sala: 000"));
            la.addView(new Lesson(this, "13:30—15:40, Lekcja 3, dr Test, gr. 2, sala: 000"));
            la.addView(new Lesson(this, "15:45—17:40, Lekcja 4, dr Test, gr. 2, sala: 000"));
            la.addView(new Lesson(this, "17:45—19:40, Lekcja 5, dr Test, gr. 2, sala: 000"));
        }
        else refreshButton.display();
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
