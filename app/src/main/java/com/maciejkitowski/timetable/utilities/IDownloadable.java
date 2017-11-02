package com.maciejkitowski.timetable.utilities;

import java.util.ArrayList;

public interface IDownloadable {
    void downloadStarted();
    void downloadSuccessful(ArrayList<String> content);
    void downloadFailed(Exception exception);
}
