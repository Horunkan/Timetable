package com.maciejkitowski.timetable.utilities;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class HtmlDownloaderTest {
    private static final String logcat = "UnitTest";
    private enum DownloadResult {BEGIN, SUCCESS, FAIL}

    private class TestObject {
        DownloadResult result;
        List<String> downloaded;

        /*@Override
        public void onDataReceivedBegin() {
            Log.i(logcat, "Download begin");
            result = DownloadResult.BEGIN;
        }

        @Override
        public void onDataReceivedSuccess(List<String> data) {
            Log.i(logcat, "Download success");
            result = DownloadResult.SUCCESS;
            downloaded = data;
        }

        @Override
        public void onDataReceivedFailed() {
            Log.i(logcat, "Download failed (no exception)");
            result = DownloadResult.FAIL;
        }

        @Override
        public void onDataReceivedFailed(Exception ex) {
            Log.i(logcat, String.format("Download failed: %s", ex.getMessage()));
            result = DownloadResult.FAIL;
        }*/
    }

    @Test
    public void downloadSinglePage() throws Exception {
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

        assertNotNull(obj.downloaded);
    }

    @Test
    public void downloadFail() throws Exception {
        TestObject obj = new TestObject();
        HtmlDownloader downloader = new HtmlDownloader(obj);
        downloader.execute("google.pl").get();

        assertEquals(obj.result, DownloadResult.FAIL);
    }
}
