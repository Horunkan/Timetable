package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import android.os.AsyncTask;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AsyncHtmlDownloaderTest {
    @Test
    public void download_singlePage() {
        TestClassObject obj = new TestClassObject();
        AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
        downloader.addListener(obj);

        downloader.execute("https://google.pl");
        while (downloader.getStatus() == AsyncTask.Status.RUNNING || downloader.getStatus() == AsyncTask.Status.PENDING) continue;

        assertTrue(obj.success);
    }

    @Test
    public void download_multiplePages() {
        TestClassObject obj = new TestClassObject();
        AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
        downloader.addListener(obj);
        String[] pages = new String[] {"https://google.pl", "https://www.wp.pl/", "https://github.com/"};

        downloader.execute(pages);
        while (downloader.getStatus() == AsyncTask.Status.RUNNING || downloader.getStatus() == AsyncTask.Status.PENDING) continue;

        assertTrue(obj.success);
    }

    @Test
    public void download_singlePage_fail() {
        TestClassObject obj = new TestClassObject();
        AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
        downloader.addListener(obj);

        downloader.execute("google.pl");
        while (downloader.getStatus() == AsyncTask.Status.RUNNING || downloader.getStatus() == AsyncTask.Status.PENDING) continue;

        assertTrue(obj.fail);
    }

    @Test
    public void download_multiplePages_fail() {
        TestClassObject obj = new TestClassObject();
        AsyncHtmlDownloader downloader = new AsyncHtmlDownloader();
        downloader.addListener(obj);
        String[] pages = new String[] {"https://google.pl", "https://www.wp.pl/", "github.com"};

        downloader.execute(pages);
        while (downloader.getStatus() == AsyncTask.Status.RUNNING || downloader.getStatus() == AsyncTask.Status.PENDING) continue;

        assertTrue(obj.fail);
    }
}
