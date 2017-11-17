package com.maciejkitowski.timetable.utilities.AsyncDataSource;

import java.util.List;

public interface AsyncDataSourceListener {
    void onLoadBegin();
    void onLoadSuccess(List<String> data);
    void onLoadFail(String message);
}
