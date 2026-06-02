package com.docdroid.filesystem.indexing;

import java.nio.file.Path;

public interface IndexEngine {
    void startIndexing();
    void stopIndexing();
    void rebuildIndex();
    IndexStatus getStatus();

    interface IndexStatus {
        long getFilesScanned();
        long getDirectoriesScanned();
        String getCurrentDrive();
        String getCurrentPath();
        long getElapsedTime();
        boolean isRunning();
    }
}
