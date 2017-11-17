package com.maciejkitowski.timetable.utilities.Internet;

import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HtmlDownloaderTest {
    /*private static final String logcat = "UnitTest";
    private enum DownloadResult {BEGIN, SUCCESS, FAIL}

    private class TestObject implements DownloadListener {
        DownloadResult result;
        List<String> downloaded;

        @Override
        public void onDownloadBegin() {
            Log.i(logcat, "Download begin");
            result = DownloadResult.BEGIN;
        }

        @Override
        public void onDownloadSuccess(List<String> data) {
            Log.i(logcat, "Download success");
            result = DownloadResult.SUCCESS;
            downloaded = data;
        }

        @Override
        public void onDownloadFailed(String message) {
            Log.i(logcat, String.format("Download failed: %s", message));
            result = DownloadResult.FAIL;
        }
    }



    @Test
    public void downloadFail() throws Exception {
        TestObject obj = new TestObject();
        AsyncHtmlDownloader downloader = new AsyncHtmlDownloader(obj);
        downloader.execute("google.pl").get();

        assertEquals(obj.result, DownloadResult.FAIL);
    }*/
}
