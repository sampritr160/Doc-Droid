package com.docdroid.filesystem.search;

import com.docdroid.domain.FileRecord;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class LiveSearchEngine implements SearchEngine {
    @Override
    public List<FileRecord> search(String query, Path root) {
        if (root == null) return new ArrayList<>();
        List<FileRecord> results = new ArrayList<>();
        try {
            Files.walkFileTree(root, EnumSet.noneOf(FileVisitOption.class), 5, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.getFileName() != null && file.getFileName().toString().toLowerCase().contains(query.toLowerCase())) {
                        results.add(toRecord(file, attrs));
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (dir.getFileName() != null && dir.getFileName().toString().toLowerCase().contains(query.toLowerCase())) {
                        results.add(toRecord(dir, attrs));
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ignored) {}
        return results;
    }

    @Override
    public List<FileRecord> findByExtension(String extension, Path root) {
        if (root == null) return new ArrayList<>();
        List<FileRecord> results = new ArrayList<>();
        try {
            Files.walkFileTree(root, EnumSet.noneOf(FileVisitOption.class), 5, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.getFileName() != null && file.getFileName().toString().toLowerCase().endsWith("." + extension.toLowerCase())) {
                        results.add(toRecord(file, attrs));
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ignored) {}
        return results;
    }

    @Override
    public FileRecord getInfo(Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            return toRecord(path, attrs);
        } catch (IOException e) {
            return FileRecord.builder()
                    .name(path.getFileName() != null ? path.getFileName().toString() : path.toString())
                    .path(path.toAbsolutePath().toString())
                    .build();
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    private FileRecord toRecord(Path path, BasicFileAttributes attrs) {
        String fileName = path.getFileName() != null ? path.getFileName().toString() : path.toString();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }

        return FileRecord.builder()
                .name(fileName)
                .path(path.toAbsolutePath().toString())
                .extension(extension)
                .size(attrs.size())
                .lastModified(attrs.lastModifiedTime().toInstant())
                .drive(path.getRoot() != null ? path.getRoot().toString() : "")
                .build();
    }
}
