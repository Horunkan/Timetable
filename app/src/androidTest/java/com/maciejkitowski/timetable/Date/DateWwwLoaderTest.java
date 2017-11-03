package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DateWwwLoaderTest {
    private static final String logcat = "UnitTest";

    @Test
    public void downloadDates() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        DateWwwLoader loader = new DateWwwLoader();
        loader.load(context);
        Thread.sleep(1000);

        assertNotNull(loader.getJson());
    }
}
