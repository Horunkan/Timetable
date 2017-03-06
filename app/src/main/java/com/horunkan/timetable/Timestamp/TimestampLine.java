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
    private static final int lineColor = Color.LTGRAY;
    private static final int lineStroke = 3;

    private static Bitmap bitmap;
    private static int windowWidth, windowHeight;
    private static Canvas canvas;
    private static Paint paint;
    private static RelativeLayout layout;
    private static ImageView imageView;

    public TimestampLine(Timetable activity, int posY) {
        if(bitmap == null) init(activity);

        canvas.drawLine(0, posY, windowWidth, posY, paint);
    }

    public static void init(Timetable activity) {
        Log.i(logcat, "Initialize TimestampLine");
        initializeBitmap(activity);
        initializePaint();
        initializeImageView(activity);
    }

    private static void initializeBitmap(Timetable activity) {
        DisplayMetrics display = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(display);

        windowWidth = display.widthPixels;
        windowHeight = display.heightPixels;

        bitmap = Bitmap.createBitmap(windowWidth, windowHeight + 150, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    private static void initializePaint() {
        paint = new Paint();
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineStroke);
    }

    private static void initializeImageView(Timetable activity) {
        imageView = new ImageView(activity);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        layout = (RelativeLayout) activity.findViewById(R.id.ScrollViewContent);
        layout.addView(imageView);
    }
}
