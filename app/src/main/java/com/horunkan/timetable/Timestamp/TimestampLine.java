package com.horunkan.timetable.Timestamp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.horunkan.timetable.R;
import com.horunkan.timetable.Timetable;

public class TimestampLine {
    private static final String logcat = "TimestampLine";
    private static final String logcatVal = logcat + "-value";

    private final int lineStroke;
    private final int lineColor;

    private int windowWidth;
    private int windowHeight;
    private ImageView imageView;
    private RelativeLayout layout;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    
    //Default constructor
    public TimestampLine(Timetable activity, int count, int stroke, int color) {
        Log.i(logcat, "Initialize TimestampLine");
        lineStroke = stroke;
        lineColor = color;
        initializeBitmap(activity, count);
        initializePaint();
        initializeImageView(activity);
        Log.i(logcat, "Finished TimestampLine initialization");
    }

    public TimestampLine(Timetable activity, int count, float startPosY) {
        this(activity, count, 3, Color.LTGRAY);
        addLines(count, startPosY);
    }

    private void addLines(int count, float startPosY) {
        for(int i = 0; i < count; ++i) {
            float posY = startPosY + (i * Timestamp.hourHeight);
            Log.i(logcatVal, String.format("Add line on position: %.2f", posY));
            canvas.drawLine(0, posY, windowWidth, posY, paint);
        }

        Log.i(logcat, String.format("Lines added: %d", count));
    }

    private void initializeBitmap(Timetable activity, int lineCount) {
        DisplayMetrics display = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(display);

        windowWidth = display.widthPixels;
        windowHeight = lineCount * Timestamp.hourHeight + lineStroke;

        bitmap = Bitmap.createBitmap(windowWidth, windowHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    private void initializePaint() {
        paint = new Paint();
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineStroke);
    }

    private void initializeImageView(Timetable activity) {
        imageView = new ImageView(activity);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        layout = (RelativeLayout) activity.findViewById(R.id.ScrollViewContent);
        layout.addView(imageView);
    }
}
