package com.docdroid.filesystem.indexing;

public interface FileWatcher {
    void watch(String path);
    void unwatch(String path);
}
