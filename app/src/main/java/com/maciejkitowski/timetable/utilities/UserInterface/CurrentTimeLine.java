package com.maciejkitowski.timetable.utilities.UserInterface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maciejkitowski.timetable.R;

public final class CurrentTimeLine {
    private static final String logcat = "CurrentTimeLine";

    private final int strokeWidth = 4;
    private final int color = Color.RED;
    private final int hourHeight;
    private final int firstTimestampPosition;

    private Activity activity;
    private int width;
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private RelativeLayout layout;
    private ImageView view;

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

        //view.setTranslationY(hourHeight*0);
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
}
