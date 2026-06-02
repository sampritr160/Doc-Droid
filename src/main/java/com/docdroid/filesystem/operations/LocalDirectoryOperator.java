package com.docdroid.filesystem.operations;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

@Service
public class LocalDirectoryOperator implements DirectoryOperator {
    @Override
    public void makeFolder(Path path) throws IOException {
        Files.createDirectories(path);
    }

    @Override
    public void deleteFolder(Path path) throws IOException {
        Files.walk(path)
             .sorted(Comparator.reverseOrder())
             .forEach(p -> {
                 try {
                     Files.delete(p);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             });
    }
}
