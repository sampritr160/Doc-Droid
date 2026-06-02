package com.docdroid.application;

import com.docdroid.domain.FileRecord;
import com.docdroid.filesystem.operations.DirectoryOperator;
import com.docdroid.filesystem.operations.FileOperator;
import com.docdroid.shell.SearchResultRegistry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class FileOperationUseCase {
    private final FileOperator fileOperator;
    private final DirectoryOperator directoryOperator;
    private final SearchResultRegistry registry;

    public FileOperationUseCase(FileOperator fileOperator, DirectoryOperator directoryOperator, SearchResultRegistry registry) {
        this.fileOperator = fileOperator;
        this.directoryOperator = directoryOperator;
        this.registry = registry;
    }

    public void makeFile(String path, String inDir) throws IOException {
        Path targetDir = inDir != null ? Path.of(inDir) : registry.getCurrentDirectory();
        fileOperator.makeFile(targetDir.resolve(path).normalize());
    }

    public void makeFolder(String name, String inDir) throws IOException {
        Path targetDir = inDir != null ? Path.of(inDir) : registry.getCurrentDirectory();
        directoryOperator.makeFolder(targetDir.resolve(name).normalize());
    }

    public void open(String pathOrIndex) throws IOException {
        fileOperator.openFile(resolvePath(pathOrIndex));
    }

    public void rename(String pathOrIndex, String newName) throws IOException {
        fileOperator.renameFile(resolvePath(pathOrIndex), newName);
    }

    public void delete(String pathOrIndex) throws IOException {
        Path path = resolvePath(pathOrIndex);
        if (java.nio.file.Files.isDirectory(path)) {
            directoryOperator.deleteFolder(path);
        } else {
            fileOperator.deleteFile(path);
        }
    }

    public void copy(String pathOrIndex, String target) throws IOException {
        fileOperator.copyFile(resolvePath(pathOrIndex), Path.of(target).normalize());
    }

    public void move(String pathOrIndex, String target) throws IOException {
        fileOperator.moveFile(resolvePath(pathOrIndex), Path.of(target).normalize());
    }

    private Path resolvePath(String pathOrIndex) {
        try {
            int index = Integer.parseInt(pathOrIndex);
            FileRecord record = registry.getResultByIndex(index);
            if (record != null) {
                return Path.of(record.getPath());
            }
        } catch (NumberFormatException ignored) {}
        return registry.getCurrentDirectory().resolve(pathOrIndex).normalize();
    }
}
