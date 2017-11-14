package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import java.util.List;

public interface AsyncDataListener {
    void onReceiveBegin();
    void onReceiveSuccess(List<String> data);
    void onReceiveFail(String message);
}
