package com.docdroid.filesystem.operations;

import java.io.IOException;
import java.nio.file.Path;

public interface FileOperator {
    void makeFile(Path path) throws IOException;
    void deleteFile(Path path) throws IOException;
    void moveFile(Path source, Path target) throws IOException;
    void copyFile(Path source, Path target) throws IOException;
    void renameFile(Path path, String newName) throws IOException;
    void openFile(Path path) throws IOException;
}
