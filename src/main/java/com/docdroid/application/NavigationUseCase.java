package com.docdroid.application;

import com.docdroid.filesystem.navigation.NavigationProvider;
import com.docdroid.shell.SearchResultRegistry;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class NavigationUseCase {
    private final NavigationProvider navigationProvider;
    private final SearchResultRegistry registry;

    public NavigationUseCase(NavigationProvider navigationProvider, SearchResultRegistry registry) {
        this.navigationProvider = navigationProvider;
        this.registry = registry;
    }

    public void cd(String path) {
        Path newPath = registry.getCurrentDirectory().resolve(path).normalize();
        if (java.nio.file.Files.isDirectory(newPath)) {
            registry.setCurrentDirectory(newPath);
        } else {
            throw new IllegalArgumentException("Not a directory: " + path);
        }
    }

    public Path pwd() {
        return registry.getCurrentDirectory();
    }

    public List<Path> list() {
        return navigationProvider.listContents(registry.getCurrentDirectory());
    }

    public String tree(int depth) {
        return navigationProvider.getTree(registry.getCurrentDirectory(), depth);
    }

    public List<String> drives() {
        return navigationProvider.getDrives();
    }
}
