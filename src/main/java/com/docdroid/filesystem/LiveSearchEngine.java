package com.docdroid.filesystem;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@Service
public class LiveSearchEngine implements SearchEngine {

    @Override
    public List<Path> search(String query) {
        // This is a dummy implementation since we don't have a starting path here.
        // In a real scenario, this would be injected with configuration.
        return new ArrayList<>();
    }

    public List<Path> search(Path startPath, String query) throws IOException {
        List<Path> results = new ArrayList<>();
        if (!Files.exists(startPath)) return results;

        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().contains(query)) {
                    results.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.getFileName().toString().contains(query)) {
                    results.add(dir);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.SKIP_SUBTREE;
            }
        });
        return results;
    }
}
