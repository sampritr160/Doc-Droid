package com.docdroid.application;

import com.docdroid.application.models.FileOperationRequest;
import com.docdroid.filesystem.DirectoryOperator;
import com.docdroid.filesystem.NavigationProvider;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class DirectoryModificationUseCases {

    private final NavigationProvider navigationProvider;
    private final DirectoryOperator directoryOperator;
    private final ResultRegistry resultRegistry;

    public DirectoryModificationUseCases(NavigationProvider navigationProvider, DirectoryOperator directoryOperator, ResultRegistry resultRegistry) {
        this.navigationProvider = navigationProvider;
        this.directoryOperator = directoryOperator;
        this.resultRegistry = resultRegistry;
    }

    public String makeFolder(FileOperationRequest request) throws IOException {
        Path targetPath = request.getPath() == null ? navigationProvider.getCurrentPath() :
                          navigationProvider.getCurrentPath().resolve(request.getPath()).normalize();
        Path folder = targetPath.resolve(request.getFilename());
        directoryOperator.create(folder);
        return "Folder created: " + folder.toAbsolutePath();
    }

    public String deleteFolder(FileOperationRequest request) throws IOException {
        Path targetPath = resolvePath(request.getPath(), request.getResultIndex());
        directoryOperator.delete(targetPath);
        return "Folder deleted: " + targetPath.toAbsolutePath();
    }

    private Path resolvePath(String pathStr, Integer resultIndex) throws IOException {
        if (resultIndex != null) {
            return resultRegistry.getResult(resultIndex)
                    .orElseThrow(() -> new IOException("Invalid result index: " + resultIndex));
        }
        if (pathStr != null) {
            return navigationProvider.getCurrentPath().resolve(pathStr).normalize();
        }
        throw new IOException("Path or result index must be provided.");
    }
}
