package com.docdroid.filesystem;

import java.nio.file.Path;
import java.util.List;

public interface SearchEngine {
    List<Path> search(String query);
}
