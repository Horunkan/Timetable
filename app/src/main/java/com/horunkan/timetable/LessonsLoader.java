package com.horunkan.timetable;

import android.util.Log;

import com.horunkan.timetable.utilities.HtmlFormatter;
import com.horunkan.timetable.utilities.HttpLoader;

import java.util.ArrayList;
import java.util.List;

public class LessonsLoader {
    private static final String logcat = "LessonsLoader";
    private static final String logcatVal = logcat + "-value";

    private List<Lesson>[] lessons;
    private Timetable activity;

    public LessonsLoader(Timetable activity, ArrayList<String> dates) {
        this.activity = activity;
        lessons = new List[5];
        loadLessons("2017-03-04");
    }

    private void loadLessons(String date) {
        HttpLoader http = (HttpLoader) new HttpLoader().execute(String.format("https://inf.ug.edu.pl/plan-%s.print", date));

        try {
            ArrayList<String> toConvert = http.get();
            toConvert = getLessonsContentOnly(toConvert);
            formatLessons(toConvert);
        }
        catch (Exception e) {
            Log.e(logcat, e.getMessage());
        }
    }

    private ArrayList<String> getLessonsContentOnly(ArrayList<String> arr) {
        ArrayList<String> buffer = new ArrayList<>();
        Boolean lessonContent = false;

        Log.i(logcat, "Start array filtering");
        for(String line : arr) {
            if(line.matches("<ul>")) lessonContent = true;
            if(lessonContent) buffer.add(line);
            if(line.matches("</ul>")) lessonContent = false;
        }

        for(String str : buffer) Log.i(logcatVal, str);
        Log.i(logcat, String.format("Array filtering finished\nLines left: %d\nLines removed: %d", buffer.size(), arr.size() - buffer.size()));

        return buffer;
    }

    private void formatLessons(ArrayList<String> arr) {
        ArrayList<String> buffer = new ArrayList<>();
        //Boolean lessonContent = false;
        int index = 0;

        for(String line : arr) {
            /*if(line.matches("<ul>")) lessonContent = true;
            if(lessonContent) buffer.add(line);*/
            if(line.matches("</ul>")) {
                assignLessonsToGroups(buffer, index);
                ++index;
                buffer = new ArrayList<>();
            }
            else buffer.add(line);
        }
    }

    private void assignLessonsToGroups(ArrayList<String> arr, int index) {
        Log.i(logcat, String.format("Start assign lessons to index: %d", index));

        lessons[index] = new ArrayList<>();

        arr = HtmlFormatter.getCleanWebsite(arr);

        for(String str : arr) {
            lessons[index].add(new Lesson(activity, str));
            Log.i(logcatVal, str);
        }

        Log.i(logcat, String.format("Finished assign lessons to index: %d\nTotal lines: %d", index, lessons[index].size()));
    }
}
