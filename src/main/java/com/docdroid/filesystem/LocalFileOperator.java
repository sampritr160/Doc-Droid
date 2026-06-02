package com.docdroid.filesystem;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.awt.Desktop;
import java.io.File;

@Service
public class LocalFileOperator implements FileOperator {

    @Override
    public void create(Path path) throws IOException {
        Files.createFile(path);
    }

    @Override
    public void delete(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    public void copy(Path source, Path target) throws IOException {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void move(Path source, Path target) throws IOException {
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void rename(Path path, String newName) throws IOException {
        Files.move(path, path.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void open(Path path) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(path.toFile());
        } else {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("linux")) {
                new ProcessBuilder("xdg-open", path.toString()).start();
            } else {
                throw new IOException("Desktop opening not supported on this platform.");
            }
        }
    }

    @Override
    public String getInfo(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(path.getFileName()).append("\n");
        sb.append("Path: ").append(path.toAbsolutePath()).append("\n");
        sb.append("Size: ").append(attrs.size()).append(" bytes\n");
        sb.append("Created: ").append(attrs.creationTime()).append("\n");
        sb.append("Modified: ").append(attrs.lastModifiedTime()).append("\n");
        sb.append("Is directory: ").append(attrs.isDirectory()).append("\n");
        return sb.toString();
    }
}
