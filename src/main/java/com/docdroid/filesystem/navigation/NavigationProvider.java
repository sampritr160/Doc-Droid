package com.docdroid.filesystem.navigation;

import java.nio.file.Path;
import java.util.List;

public interface NavigationProvider {
    List<Path> listContents(Path path);
    String getTree(Path path, int depth);
    List<String> getDrives();
}
