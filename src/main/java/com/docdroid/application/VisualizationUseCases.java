package com.docdroid.application;

import com.docdroid.filesystem.DirectoryOperator;
import com.docdroid.filesystem.NavigationProvider;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisualizationUseCases {

    private final NavigationProvider navigationProvider;
    private final DirectoryOperator directoryOperator;

    public VisualizationUseCases(NavigationProvider navigationProvider, DirectoryOperator directoryOperator) {
        this.navigationProvider = navigationProvider;
        this.directoryOperator = directoryOperator;
    }

    public String list(String pathStr) throws IOException {
        Path targetPath = pathStr == null ? navigationProvider.getCurrentPath() :
                          navigationProvider.getCurrentPath().resolve(pathStr).normalize();

        List<Path> contents = navigationProvider.listContents(targetPath);
        return contents.stream()
                .map(p -> (Files.isDirectory(p) ? "[DIR] " : "[FILE] ") + p.getFileName().toString())
                .collect(Collectors.joining("\n"));
    }

    public String tree(String pathStr, Integer depth) throws IOException {
        Path targetPath = pathStr == null ? navigationProvider.getCurrentPath() :
                          navigationProvider.getCurrentPath().resolve(pathStr).normalize();
        int maxDepth = depth == null ? 2 : depth;
        return directoryOperator.getTree(targetPath, maxDepth);
    }
}
