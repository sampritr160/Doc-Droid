package com.docdroid.filesystem;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;
import java.io.File;

@Service
public class LocalDirectoryOperator implements DirectoryOperator {

    @Override
    public void create(Path path) throws IOException {
        Files.createDirectories(path);
    }

    @Override
    public void delete(Path path) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }

    @Override
    public String getTree(Path path, int depth) throws IOException {
        StringBuilder sb = new StringBuilder();
        renderTree(path, "", sb, depth, 0);
        return sb.toString();
    }

    private void renderTree(Path path, String indent, StringBuilder sb, int maxDepth, int currentDepth) throws IOException {
        String name = path.getFileName() == null ? path.toString() : path.getFileName().toString();
        sb.append(indent).append(name).append("/\n");
        if (currentDepth < maxDepth) {
            try (Stream<Path> stream = Files.list(path)) {
                Path[] children = stream.toArray(Path[]::new);
                for (int i = 0; i < children.length; i++) {
                    boolean last = (i == children.length - 1);
                    String nextIndent = indent + (last ? "    " : "│   ");
                    if (Files.isDirectory(children[i])) {
                        renderTree(children[i], nextIndent, sb, maxDepth, currentDepth + 1);
                    } else {
                        sb.append(indent).append(last ? "└── " : "├── ").append(children[i].getFileName()).append("\n");
                    }
                }
            } catch (IOException e) {
                sb.append(indent).append(" [Error reading directory]\n");
            }
        }
    }
}
