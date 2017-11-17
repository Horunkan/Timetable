package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AsyncFileLoaderTest {
    private static final String logcat = "UnitTest";

    private String[] getNotCreatedFiles(int count) {
        String[] buffer = new String[count];

        for(int i = 0; i < count; ++i) {
            String name = String.format("test_notCreated_%d.txt", i + 1);
            buffer[i] = name;
        }

        return buffer;
    }

    private String[] getCreatedFiles(Context context, int count) throws Exception {
        String[] buffer = new String[count];

        for(int i = 0; i < count; ++i) {
            String name = String.format("test_created_%d.txt", i + 1);
            File file = new File(context.getFilesDir(), name);
            file.createNewFile();
            buffer[i] = name;
        }

        return buffer;
    }

    private void deleteCreatedFiles(Context context, String[] files) {
        for(String fl : files) {
            File file = new File(context.getFilesDir(), fl);
            file.delete();
        }
    }

    @Test
    public void load_fileNotExists() {
        Context context = InstrumentationRegistry.getTargetContext();
        AsyncFileLoader loader = new AsyncFileLoader(context);
        TestClassObject obj = new TestClassObject();
        loader.addListener(obj);

        loader.execute(getNotCreatedFiles(1));
        while (loader.getStatus() == AsyncTask.Status.RUNNING || loader.getStatus() == AsyncTask.Status.PENDING) continue;

        assertTrue(obj.fail);
    }

    @Test
    public void load_fileExists() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AsyncFileLoader loader = new AsyncFileLoader(context);
        TestClassObject obj = new TestClassObject();
        loader.addListener(obj);

        String[] createdFiles = getCreatedFiles(context, 1);
        loader.execute(createdFiles);
        while (loader.getStatus() == AsyncTask.Status.RUNNING || loader.getStatus() == AsyncTask.Status.PENDING) continue;

        deleteCreatedFiles(context, createdFiles);
        assertTrue(obj.success);
    }

    @Test
    public void multiple_load_someOfFilesNotExists() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AsyncFileLoader loader = new AsyncFileLoader(context);
        TestClassObject obj = new TestClassObject();
        loader.addListener(obj);

        String[] notExists = getNotCreatedFiles(2);
        String[] exists = getCreatedFiles(context, 3);
        String[] files = new String[2+3];
        for(int i = 0; i < 2; ++i) files[i] = notExists[i];
        for(int i = 0; i < 3; ++i) files[i+2] = exists[i];

        loader.execute(files);
        while (loader.getStatus() == AsyncTask.Status.RUNNING || loader.getStatus() == AsyncTask.Status.PENDING) continue;

        deleteCreatedFiles(context, exists);
        assertTrue(obj.fail);
    }

    @Test
    public void multiple_load_allFilesExists() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AsyncFileLoader loader = new AsyncFileLoader(context);
        TestClassObject obj = new TestClassObject();
        loader.addListener(obj);

        String[] exists = getCreatedFiles(context, 5);
        loader.execute(exists);
        while (loader.getStatus() == AsyncTask.Status.RUNNING || loader.getStatus() == AsyncTask.Status.PENDING) continue;

        deleteCreatedFiles(context, exists);
        assertTrue(obj.success);
    }
}
