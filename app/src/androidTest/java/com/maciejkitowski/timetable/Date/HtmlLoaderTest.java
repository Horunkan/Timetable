package com.maciejkitowski.timetable.Date;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.maciejkitowski.timetable.utilities.AsyncDataListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class HtmlLoaderTest {
    private static final String logcat = "UnitTest";
    private enum ReceiveResult {BEGIN, SUCCESS, FAIL}

    private class TestObject implements AsyncDataListener {
        ReceiveResult result;
        List<String> received;

        @Override
        public void onReceiveBegin() {
            Log.i(logcat, "Receive begin");
            result = ReceiveResult.BEGIN;
        }

        @Override
        public void onReceiveSuccess(List<String> data) {
            Log.i(logcat, "Receive success");
            received = data;
            result = ReceiveResult.SUCCESS;
        }

        @Override
        public void onReceiveFail(String message) {
            Log.i(logcat, String.format("Receive fail: %s", message));
            result = ReceiveResult.FAIL;
        }
    }


    @Test
    public void downloadDates() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        TestObject obj = new TestObject();
        HtmlLoader loader = new HtmlLoader(context, obj);
        loader.load();
        Thread.sleep(1000);

        assertNotNull(obj.received);
    }
}
