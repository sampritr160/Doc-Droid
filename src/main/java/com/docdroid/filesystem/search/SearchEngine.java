package com.docdroid.filesystem.search;

import com.docdroid.domain.FileRecord;
import java.nio.file.Path;
import java.util.List;

public interface SearchEngine {
    List<FileRecord> search(String query, Path root);
    List<FileRecord> findByExtension(String extension, Path root);
    FileRecord getInfo(Path path);
    boolean isAvailable();
}
