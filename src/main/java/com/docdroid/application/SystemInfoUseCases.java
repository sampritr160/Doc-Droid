package com.docdroid.application;

import com.docdroid.filesystem.FileOperator;
import com.docdroid.filesystem.NavigationProvider;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemInfoUseCases {

    private final NavigationProvider navigationProvider;
    private final FileOperator fileOperator;
    private final ResultRegistry resultRegistry;

    public SystemInfoUseCases(NavigationProvider navigationProvider, FileOperator fileOperator, ResultRegistry resultRegistry) {
        this.navigationProvider = navigationProvider;
        this.fileOperator = fileOperator;
        this.resultRegistry = resultRegistry;
    }

    public String listDrives() {
        List<Path> drives = navigationProvider.listDrives();
        return drives.stream()
                .map(Path::toString)
                .collect(Collectors.joining("\n"));
    }

    public String info(String pathStr, Integer resultIndex) throws IOException {
        Path targetPath = resolvePath(pathStr, resultIndex);
        return fileOperator.getInfo(targetPath);
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
