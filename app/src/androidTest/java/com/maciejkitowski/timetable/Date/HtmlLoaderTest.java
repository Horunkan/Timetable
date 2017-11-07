package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class HtmlLoaderTest {
    private static final String logcat = "UnitTest";

    @Test
    public void downloadDates() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        Loader loader = new Loader(context);
        HtmlLoader html = new HtmlLoader(context, loader);
        Thread.sleep(1000);

        assertNotNull(html.downloaded);
    }
}
