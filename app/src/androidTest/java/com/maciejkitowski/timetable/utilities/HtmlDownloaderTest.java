package com.maciejkitowski.timetable.utilities;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HtmlDownloaderTest {
    private static final String logcat = "UnitTest";

    private static class TestObject implements IDownloadable {
        public ArrayList<String> downloaded = null;
        enum DownloadStatus {STARTED, FAILED, FINISHED}
        public DownloadStatus status;

        @Override
        public void downloadStarted() {
            Log.i(logcat, "Download started");
            status = DownloadStatus.STARTED;
        }

        @Override
        public void downloadSuccessful(ArrayList<String> content) {
            Log.i(logcat, "Download success");
            status = DownloadStatus.FINISHED;
            downloaded = content;
            for(String str : downloaded) Log.i(logcat + "-val", str);
        }

        @Override
        public void downloadFailed(Exception exception) {
            Log.i(logcat, String.format("Download failed with exception: %s", exception.getLocalizedMessage()));
            status = DownloadStatus.FAILED;
        }
    }

    @Test
    public void downloadOnePage() throws Exception {
        TestObject obj = new TestObject();
        HtmlDownloader downloader = new HtmlDownloader(obj);
        downloader.execute("https://google.pl").get();

        assertNotNull(obj.downloaded);
    }

    @Test
    public void downloadMultiplePages() throws Exception {
        String[] pages = new String[] {"https://google.pl", "https://www.wp.pl/", "https://github.com/"};
        TestObject obj = new TestObject();
        HtmlDownloader downloader = new HtmlDownloader(obj);
        downloader.execute(pages).get();

        assertTrue(obj.downloaded.size() == pages.length);
    }
}
