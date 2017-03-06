package com.horunkan.timetable.Lesson;

import android.util.Log;

import com.horunkan.timetable.Timetable;
import com.horunkan.timetable.utilities.HtmlFormatter;
import com.horunkan.timetable.utilities.HttpLoader;

import java.util.ArrayList;
import java.util.List;

public class LessonsLoader {
    private static final String logcat = "LessonsLoader";
    private static final String logcatVal = logcat + "-value";

    private List<Lesson>[] lessons;
    private Timetable activity;

    public LessonsLoader(Timetable activity, String date) {
        this.activity = activity;
        lessons = new List[5];
        loadLessons(date);
    }

    public List<Lesson> getLessons(int index) { return lessons[index]; }

    private void loadLessons(String date) {
        HttpLoader http = (HttpLoader) new HttpLoader().execute(String.format("https://inf.ug.edu.pl/plan-%s.print", date));

        try {
            ArrayList<String> toConvert = HtmlFormatter.getCleanWebsite(http.get());
            for(String str: toConvert) Log.i(logcatVal, str);
            formatLessons(toConvert);
        }
        catch (Exception e) {
            Log.e(logcat, e.getMessage());
        }
    }

    private void formatLessons(ArrayList<String> arr) {
        ArrayList<String> buffer = new ArrayList<>();
        int index = 0;

        for(String line : arr) {
            if(line.contains("—") || line.contains("brak")) buffer.add(line);
            else if(line.contains("Studia") && buffer.size() > 0) {
                assignLessonsToGroups(buffer, index);
                ++index;
                buffer = new ArrayList<>();
            }
            if(index >= 5) break;
        }

        assignLessonsToGroups(buffer, index); //Last group
    }

    private void assignLessonsToGroups(ArrayList<String> arr, int index) {
        Log.i(logcat, String.format("Start assign lessons to index: %d", index));
        lessons[index] = new ArrayList<>();

        for(String str : arr) {
            if(!str.contains("zajęcia zostały")) lessons[index].add(new Lesson(activity, str)); //TODO Canceled lessons generate bugs with lessons loading
            Log.i(logcatVal, str);
        }

        Log.i(logcat, String.format("Finished assign lessons to index: %d\nTotal lines: %d", index, lessons[index].size()));
    }
}
