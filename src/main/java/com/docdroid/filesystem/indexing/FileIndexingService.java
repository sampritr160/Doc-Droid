package com.docdroid.filesystem.indexing;

import com.docdroid.domain.FileRecord;
import com.docdroid.infrastructure.persistence.FileIndexRepository;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FileIndexingService implements IndexEngine {

    private final FileIndexRepository repository;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicLong filesScanned = new AtomicLong(0);
    private final AtomicLong directoriesScanned = new AtomicLong(0);
    private String currentDrive = "";
    private String currentPath = "";
    private long startTime = 0;

    public FileIndexingService(FileIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public void startIndexing() {
        if (running.compareAndSet(false, true)) {
            new Thread(this::runIndexing).start();
        }
    }

    @Override
    public void stopIndexing() {
        running.set(false);
    }

    @Override
    public void rebuildIndex() {
        stopIndexing();
        repository.deleteAll();
        startIndexing();
    }

    private void runIndexing() {
        startTime = System.currentTimeMillis();
        filesScanned.set(0);
        directoriesScanned.set(0);

        File[] roots = File.listRoots();
        for (File root : roots) {
            if (!running.get()) break;
            currentDrive = root.getAbsolutePath();
            indexDrive(root.toPath());
        }
        running.set(false);
    }

    private void indexDrive(Path rootPath) {
        List<FileRecord> batch = new ArrayList<>();
        int batchSize = 1000;

        try {
            Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!running.get()) return FileVisitResult.TERMINATE;
                    directoriesScanned.incrementAndGet();
                    currentPath = dir.toString();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!running.get()) return FileVisitResult.TERMINATE;

                    FileRecord record = toRecord(file, attrs);
                    batch.add(record);

                    if (batch.size() >= batchSize) {
                        saveBatch(batch);
                        batch.clear();
                    }

                    filesScanned.incrementAndGet();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });

            if (!batch.isEmpty()) {
                saveBatch(batch);
            }
        } catch (IOException ignored) {}
    }

    private void saveBatch(List<FileRecord> batch) {
        for (FileRecord record : batch) {
            List<FileRecord> existing = repository.findByPath(record.getPath());
            if (existing.isEmpty()) {
                repository.save(record);
            } else {
                FileRecord e = existing.get(0);
                record.setId(e.getId());
                repository.save(record);
            }
        }
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
                .lastIndexedAt(Instant.now())
                .createdAt(attrs.creationTime().toInstant())
                .modifiedAt(attrs.lastModifiedTime().toInstant())
                .build();
    }

    @Override
    public IndexStatus getStatus() {
        return new IndexStatus() {
            @Override public long getFilesScanned() { return filesScanned.get(); }
            @Override public long getDirectoriesScanned() { return directoriesScanned.get(); }
            @Override public String getCurrentDrive() { return currentDrive; }
            @Override public String getCurrentPath() { return currentPath; }
            @Override public long getElapsedTime() { return System.currentTimeMillis() - startTime; }
            @Override public boolean isRunning() { return running.get(); }
        };
    }
}
