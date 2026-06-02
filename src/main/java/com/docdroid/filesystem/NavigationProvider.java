package com.docdroid.filesystem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface NavigationProvider {
    Path getCurrentPath();
    void setCurrentPath(Path path);
    List<Path> listContents(Path path) throws IOException;
    List<Path> listDrives();
}
