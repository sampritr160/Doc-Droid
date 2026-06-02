package com.docdroid.application;

import com.docdroid.application.models.NavigationRequest;
import com.docdroid.filesystem.NavigationProvider;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class NavigationUseCases {

    private final NavigationProvider navigationProvider;

    public NavigationUseCases(NavigationProvider navigationProvider) {
        this.navigationProvider = navigationProvider;
    }

    public String pwd() {
        return navigationProvider.getCurrentPath().toString();
    }

    public String cd(NavigationRequest request) throws IOException {
        String pathStr = request.getPath();
        Path targetPath;
        if (pathStr.equals("..")) {
            targetPath = navigationProvider.getCurrentPath().getParent();
        } else {
            targetPath = navigationProvider.getCurrentPath().resolve(pathStr).normalize();
        }

        if (targetPath != null && Files.exists(targetPath) && Files.isDirectory(targetPath)) {
            navigationProvider.setCurrentPath(targetPath);
            return "Changed directory to: " + navigationProvider.getCurrentPath();
        } else {
            throw new IOException("Directory not found: " + pathStr);
        }
    }
}
