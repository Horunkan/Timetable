package com.maciejkitowski.timetable.utilities.UserInterface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maciejkitowski.timetable.R;

import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;

import java.util.Timer;
import java.util.TimerTask;

public final class CurrentTimeLine {
    private static final String logcat = "CurrentTimeLine";

    private final int strokeWidth = 4;
    private final int color = Color.RED;
    private final int startHour = 7;
    private final int endHour = 20;
    private final Minutes refreshDelay = Minutes.minutes(2);
    private final int hourHeight;
    private final int firstTimestampPosition;

    private Activity activity;
    private int width;
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private RelativeLayout layout;
    private ImageView view;
    private Timer timer;

    public CurrentTimeLine(Activity activity) {
        Log.i(logcat, "Initialize");
        this.activity = activity;
        int height = activity.getResources().getDimensionPixelSize(R.dimen.timestamp_height); //Height of single timestamp
        hourHeight = activity.getResources().getDimensionPixelSize(R.dimen.timestamp_marginbottom) + height;
        firstTimestampPosition = height / 2;

        initializeBitmap(height);
        initializePaint();
        initializeCanvas();
        initializeImageView();
        hide();
    }

    public void hide() {
        Log.i(logcat, "Hide current time pointer");
        if(view != null) view.setVisibility(View.INVISIBLE);
    }

    public void display() {
        Log.i(logcat, "Display current time pointer");
        if(view != null) {
            view.setVisibility(View.VISIBLE);
            if(timer == null) startDisplaying();
        }
    }

    private void initializePaint() {
        Log.i(logcat, "Initialize paint");
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
    }

    private void initializeCanvas() {
        Log.i(logcat, "Initialize canvas");

        canvas = new Canvas(bitmap);
        int positionY = firstTimestampPosition + (hourHeight * 0);
        canvas.drawLine(0, positionY, width, positionY, paint);
    }

    private void initializeImageView() {
        Log.i(logcat, "Initialize ImageView");
        layout = activity.findViewById(R.id.ScrollViewContent);

        view = new ImageView(activity);
        view.setImageBitmap(bitmap);
        view.setScaleType(ImageView.ScaleType.FIT_XY);

        layout.addView(view);
    }

    private void initializeBitmap(int height) {
        Log.i(logcat, "Initialize bitmap");
        DisplayMetrics display = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(display);

        width = display.widthPixels;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    private void setPosition() {
        LocalTime time = LocalTime.now();
        Log.i(logcat, String.format("Current time: %02d:%02d", time.getHourOfDay(), time.getMinuteOfHour()));
        int currentHour = time.getHourOfDay();
        int currentMinute = time.getMinuteOfHour();

        if(currentHour < startHour || currentHour > endHour) {
            Log.i(logcat, "Time out of timestamp");
            if(timer != null) timer.cancel();
            hide();
        }
        else {
            if(view.getVisibility() == View.INVISIBLE) display();

            int hourPosition = hourHeight * (currentHour - startHour);
            float minutePosition = (hourHeight/60f)*currentMinute;
            float newPosition = hourPosition + minutePosition;
            Log.i(logcat, String.format("hour position: %d, minute position: %f, final position: %f", hourPosition, minutePosition, newPosition));

            view.setTranslationY(newPosition);
        }
    }

    private void startDisplaying() {
        Log.i(logcat, "Start pointer movement");
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHourOfDay();

        if(currentHour < startHour) {
            LocalTime startTime = LocalTime.parse(String.format("%02d:00", startHour));
            Duration duration = new Duration(currentTime.toDateTimeToday(), startTime.toDateTimeToday());

            Log.i(logcat, String.format("Started before first timestamp, wait for: %02d:%02d:%02d(%d milliseconds) to display current time.", duration.getStandardHours(), duration.getStandardMinutes(), duration.getStandardSeconds(), duration.getMillis()));
            startDelayedTimer(duration.getMillis());
        }
        else if(currentHour > endHour) {
            Log.i(logcat, String.format("Started after 20:00"));
        }
        else startTimer();
    }

    private void startDelayedTimer(long milliseconds) {
        Log.i(logcat, String.format("Start counting to 02d:00 - %d milliseconds left", startHour, milliseconds));
        Timer delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() { @Override public void run() { startTimer(); }}, milliseconds );
    }

    private void startTimer() {
        Log.i(logcat, "Start timer");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() { @Override public void run() { activity.runOnUiThread(() -> setPosition()); }}, 0, refreshDelay.toStandardDuration().getMillis());
    }
}
