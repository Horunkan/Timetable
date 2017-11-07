package com.maciejkitowski.timetable.utilities;

import java.util.List;

public interface DataReceivedListener {
    void onDataReceivedBegin();
    void onDataReceivedSuccess(List<String> data);
    void onDataReceivedFailed();
    void onDataReceivedFailed(Exception ex);
}
