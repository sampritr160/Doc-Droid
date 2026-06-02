package com.docdroid.filesystem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileOperator {
    void create(Path path) throws IOException;
    void delete(Path path) throws IOException;
    void copy(Path source, Path target) throws IOException;
    void move(Path source, Path target) throws IOException;
    void rename(Path path, String newName) throws IOException;
    void open(Path path) throws IOException;
    String getInfo(Path path) throws IOException;
}
