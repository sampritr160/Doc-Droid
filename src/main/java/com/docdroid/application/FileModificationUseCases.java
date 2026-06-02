package com.docdroid.application;

import com.docdroid.application.models.FileOperationRequest;
import com.docdroid.filesystem.FileOperator;
import com.docdroid.filesystem.NavigationProvider;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class FileModificationUseCases {

    private final NavigationProvider navigationProvider;
    private final FileOperator fileOperator;
    private final ResultRegistry resultRegistry;

    public FileModificationUseCases(NavigationProvider navigationProvider, FileOperator fileOperator, ResultRegistry resultRegistry) {
        this.navigationProvider = navigationProvider;
        this.fileOperator = fileOperator;
        this.resultRegistry = resultRegistry;
    }

    public String make(FileOperationRequest request) throws IOException {
        Path targetPath = request.getPath() == null ? navigationProvider.getCurrentPath() :
                          navigationProvider.getCurrentPath().resolve(request.getPath()).normalize();
        Path file = targetPath.resolve(request.getFilename());
        fileOperator.create(file);
        return "File created: " + file.toAbsolutePath();
    }

    public String open(FileOperationRequest request) throws IOException {
        Path targetPath = resolvePath(request.getPath(), request.getResultIndex());
        fileOperator.open(targetPath);
        return "Opening: " + targetPath.toAbsolutePath();
    }

    public String delete(FileOperationRequest request) throws IOException {
        Path targetPath = resolvePath(request.getPath(), request.getResultIndex());
        fileOperator.delete(targetPath);
        return "File deleted: " + targetPath.toAbsolutePath();
    }

    public String copy(FileOperationRequest request) throws IOException {
        Path source = resolvePath(request.getPath(), request.getResultIndex());
        Path target = navigationProvider.getCurrentPath().resolve(request.getTargetPath()).normalize();
        fileOperator.copy(source, target);
        return "Copied " + source.toAbsolutePath() + " to " + target.toAbsolutePath();
    }

    public String move(FileOperationRequest request) throws IOException {
        Path source = resolvePath(request.getPath(), request.getResultIndex());
        Path target = navigationProvider.getCurrentPath().resolve(request.getTargetPath()).normalize();
        fileOperator.move(source, target);
        return "Moved " + source.toAbsolutePath() + " to " + target.toAbsolutePath();
    }

    public String rename(FileOperationRequest request) throws IOException {
        Path targetPath = resolvePath(request.getPath(), request.getResultIndex());
        fileOperator.rename(targetPath, request.getNewName());
        return "Renamed " + targetPath.getFileName() + " to " + request.getNewName();
    }

    protected Path resolvePath(String pathStr, Integer resultIndex) throws IOException {
        if (resultIndex != null) {
            return resultRegistry.getResult(resultIndex)
                    .orElseThrow(() -> new IOException("Invalid result index: " + resultIndex));
        }
        if (pathStr != null) {
            // Check if pathStr is a numeric shorthand index
            try {
                int index = Integer.parseInt(pathStr);
                return resultRegistry.getResult(index)
                        .orElseThrow(() -> new IOException("Invalid result index: " + index));
            } catch (NumberFormatException e) {
                // Not a number, treat as path
                return navigationProvider.getCurrentPath().resolve(pathStr).normalize();
            }
        }
        throw new IOException("Path or result index must be provided.");
    }
}
