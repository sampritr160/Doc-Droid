package com.docdroid.filesystem;

import java.io.IOException;
import java.nio.file.Path;

public interface DirectoryOperator {
    void create(Path path) throws IOException;
    void delete(Path path) throws IOException;
    String getTree(Path path, int depth) throws IOException;
}
