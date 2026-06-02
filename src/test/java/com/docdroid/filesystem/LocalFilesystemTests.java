package com.docdroid.filesystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class LocalFilesystemTests {

    @TempDir
    Path tempDir;

    LocalFileOperator fileOperator = new LocalFileOperator();
    LocalDirectoryOperator directoryOperator = new LocalDirectoryOperator();

    @Test
    public void testFileLifecycle() throws IOException {
        Path file = tempDir.resolve("test.txt");
        fileOperator.create(file);
        assertTrue(Files.exists(file));

        String info = fileOperator.getInfo(file);
        assertTrue(info.contains("test.txt"));

        Path moved = tempDir.resolve("moved.txt");
        fileOperator.move(file, moved);
        assertFalse(Files.exists(file));
        assertTrue(Files.exists(moved));

        fileOperator.delete(moved);
        assertFalse(Files.exists(moved));
    }

    @Test
    public void testDirectoryLifecycle() throws IOException {
        Path dir = tempDir.resolve("subdir");
        directoryOperator.create(dir);
        assertTrue(Files.exists(dir));
        assertTrue(Files.isDirectory(dir));

        Files.createFile(dir.resolve("inner.txt"));
        String tree = directoryOperator.getTree(dir, 1);
        assertTrue(tree.contains("inner.txt"));

        directoryOperator.delete(dir);
        assertFalse(Files.exists(dir));
    }
}
