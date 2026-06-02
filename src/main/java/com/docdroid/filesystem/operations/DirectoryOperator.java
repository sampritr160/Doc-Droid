package com.docdroid.filesystem.operations;

import java.io.IOException;
import java.nio.file.Path;

public interface DirectoryOperator {
    void makeFolder(Path path) throws IOException;
    void deleteFolder(Path path) throws IOException;
}
