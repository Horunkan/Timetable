package com.maciejkitowski.timetable.utilities;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HtmlDownloaderTest {
    private static final String logcat = "UnitTest";

    @Test
    public void downloadOnePageNotNull() throws Exception {
        HtmlDownloader downloader = new HtmlDownloader();
        downloader.execute("http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php");
        String[] content = downloader.get();

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

    //Tests with response after finish
    private static class DownloadInterfaceTest implements IDownloadable {
        enum runResult {STARTED, SUCCESS, FAIL }
        runResult result = null;
        @Override
        public void downloadStarted() {
            Log.i(logcat, "Download started");
            result = runResult.STARTED;
        }

        @Override
        public void downloadSuccessful() {
            Log.i(logcat, "Download finished");
            result = runResult.SUCCESS;
        }

        @Override
        public void downloadFailed() {
            Log.i(logcat, "Download failed");
            result = runResult.FAIL;
        }
    }

    @Test
    public void interfaceDownloadStatus() throws Exception {
        DownloadInterfaceTest inter = new DownloadInterfaceTest();
        HtmlDownloader downloader = new HtmlDownloader(inter);
        downloader.execute("http://sigma.inf.ug.edu.pl/~mkitowski/Timetable/Date.php");
        downloader.get();

        assertTrue(inter.result != null);
    }
}
