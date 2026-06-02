package com.docdroid.filesystem.operations;

import org.springframework.stereotype.Service;
import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class LocalFileOperator implements FileOperator {
    @Override
    public void makeFile(Path path) throws IOException {
        Files.createFile(path);
    }

    @Override
    public void deleteFile(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public void moveFile(Path source, Path target) throws IOException {
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void copyFile(Path source, Path target) throws IOException {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void renameFile(Path path, String newName) throws IOException {
        Files.move(path, path.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void openFile(Path path) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(path.toFile());
        } else {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", path.toString()});
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"open", path.toString()});
            } else {
                Runtime.getRuntime().exec(new String[]{"xdg-open", path.toString()});
            }
        }
    }
}
