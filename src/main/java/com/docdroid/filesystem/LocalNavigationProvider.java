package com.docdroid.filesystem;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocalNavigationProvider implements NavigationProvider {

    private Path currentPath = Paths.get(".").toAbsolutePath().normalize();

    @Override
    public Path getCurrentPath() {
        return currentPath;
    }

    @Override
    public void setCurrentPath(Path path) {
        this.currentPath = path.toAbsolutePath().normalize();
    }

    @Override
    public List<Path> listContents(Path path) throws IOException {
        try (Stream<Path> stream = Files.list(path)) {
            return stream.collect(Collectors.toList());
        }
    }

    @Override
    public List<Path> listDrives() {
        List<Path> drives = new ArrayList<>();
        for (Path root : FileSystems.getDefault().getRootDirectories()) {
            drives.add(root);
        }
        return drives;
    }
}
