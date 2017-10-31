package com.maciejkitowski.timetable;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.HtmlDownloader;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static junit.framework.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HtmlDownloaderTest {
    private static final String logcat = "UnitTest";

    @Test
    public void downloadOnePageNotNull() throws Exception {
        HtmlDownloader downloader = new HtmlDownloader();
        downloader.execute("http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php");
        String[] content = downloader.get();

        for(String str : content) Log.i(logcat, str);
        assertNotNull(content);
    }

    @Test
    public void downloadMultiplePagesCount() throws Exception {
        String[] pages = new String[3];
        pages[0] = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";
        pages[1] = "https://inf.ug.edu.pl/";
        pages[2] = "https://inf.ug.edu.pl/niestacjonarne";

        HtmlDownloader downloader = new HtmlDownloader();
        downloader.execute(pages);
        String[] content = downloader.get();

        assertEquals(content.length, pages.length);
    }

    @Test
    public void downloadMultiplePagesEquals() throws Exception {
        String[] pages = new String[3];
        pages[0] = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";
        pages[1] = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";
        pages[2] = "http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php";

        HtmlDownloader downloader = new HtmlDownloader();
        downloader.execute(pages);
        String[] content = downloader.get();
        String[] pattern = new String[] {content[0], content[0], content[0]};

        assertTrue(Arrays.equals(content, pattern));
    }
}
