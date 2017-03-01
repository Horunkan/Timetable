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


            LinearLayout la = (LinearLayout)findViewById(R.id.LessonLayout);



            la.addView(new Lesson(this, "9:00—10:00, Tytuł, dr Test, gr. 2, sala: 000"));
        }
        else refreshButton.display();
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
