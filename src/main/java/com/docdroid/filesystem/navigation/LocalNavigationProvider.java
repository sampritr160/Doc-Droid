package com.docdroid.filesystem.navigation;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocalNavigationProvider implements NavigationProvider {
    @Override
    public List<Path> listContents(Path path) {
        try (Stream<Path> stream = Files.list(path)) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String getTree(Path path, int depth) {
        StringBuilder sb = new StringBuilder();
        renderTree(path, "", sb, depth);
        return sb.toString();
    }

    private void renderTree(Path path, String indent, StringBuilder sb, int depth) {
        if (depth < 0) return;
        sb.append(indent).append("├── ").append(path.getFileName()).append("\n");
        if (Files.isDirectory(path)) {
            try (Stream<Path> stream = Files.list(path)) {
                List<Path> contents = stream.collect(Collectors.toList());
                for (Path p : contents) {
                    renderTree(p, indent + "│   ", sb, depth - 1);
                }
            } catch (IOException ignored) {}
        }
    }

    @Override
    public List<String> getDrives() {
        List<String> drives = new ArrayList<>();
        for (File root : File.listRoots()) {
            drives.add(root.getAbsolutePath());
        }
        return drives;
    }
}
