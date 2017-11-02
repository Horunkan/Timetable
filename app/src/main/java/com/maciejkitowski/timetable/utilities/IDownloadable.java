package com.maciejkitowski.timetable.utilities;

public interface IDownloadable {
    void downloadStarted();
    void downloadSuccessful();
    void downloadFailed();
}
