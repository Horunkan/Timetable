package com.horunkan.timetable.utilities;

import android.util.Log;

import java.util.ArrayList;

public class HtmlFormatter {
    private static final String logcat = "Html-Formatter";
    private static final String logcatVal = logcat + "-value";

    public static ArrayList<String> getRawArray(ArrayList<String> arr) {
        ArrayList<String> noTabs = removeTabulators(arr);
        ArrayList<String> noHtml = removeHtmlTags(noTabs);

        Log.i(logcat, "\t\t\tStart HTML formatter");
        for(String str : noHtml) Log.i(logcatVal, str);
        Log.i(logcat, String.format("HTML formatter finished\n\tTotal lines: %d", noHtml.size()));

        return noHtml;
    }

    private static ArrayList<String> removeTabulators(ArrayList<String> arr) {
        ArrayList<String> buffer = new ArrayList<String>();

        for(String line : arr) {
            line = line.replaceAll("\t", "");
            buffer.add(line);
        }

        return buffer;
    }

    private static ArrayList<String> removeHtmlTags(ArrayList<String> arr) {
        ArrayList<String> buffer = new ArrayList<String>();
        final String regex = "<(.|\\n)+?>";

        for(String line : arr) {
            line = line.replaceAll(regex, "");
            if(!line.contentEquals("")) buffer.add(line);
        }

        return buffer;
    }
}
