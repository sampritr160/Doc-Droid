package com.docdroid.filesystem;

import java.nio.file.Path;

public interface IndexEngine {
    void indexFile(Path path);
    void indexDirectory(Path path);
    void clearIndex();
}
