package com.maciejkitowski.timetable.Date;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DateWwwLoaderTest {
    private static final String logcat = "UnitTest";

    @Test
    public void downloadDates() throws Exception {
        DateWwwLoader loader = new DateWwwLoader();
        loader.load();
        Thread.sleep(1000);

        assertNotNull(loader.getJson());
    }
}
