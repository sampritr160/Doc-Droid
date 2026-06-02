package com.docdroid.application;

import com.docdroid.application.models.*;
import com.docdroid.filesystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class FilesystemIntegrationTests {

    @TempDir
    Path tempDir;

    @Autowired
    NavigationUseCases navigationUseCases;

    @Autowired
    FileModificationUseCases fileModificationUseCases;

    @Autowired
    DiscoveryUseCases discoveryUseCases;

    @Autowired
    NavigationProvider navigationProvider;

    @BeforeEach
    public void setup() {
        navigationProvider.setCurrentPath(tempDir);
    }

    @Test
    public void testNavigationAndModification() throws IOException {
        // Create a file
        String result = fileModificationUseCases.make(FileOperationRequest.builder().filename("hello.txt").build());
        assertTrue(result.contains("hello.txt"));
        assertTrue(Files.exists(tempDir.resolve("hello.txt")));

        // Find the file
        List<Path> findResult = discoveryUseCases.find(DiscoveryRequest.builder().query("hello").build());
        assertEquals(1, findResult.size());

        // Use result index to delete
        String deleteResult = fileModificationUseCases.delete(FileOperationRequest.builder().resultIndex(1).build());
        assertTrue(deleteResult.contains("deleted"));
        assertFalse(Files.exists(tempDir.resolve("hello.txt")));
    }

    @Test
    public void testCrossDriveOperations() throws IOException {
        Path src = tempDir.resolve("src.txt");
        Files.createFile(src);
        Path targetDir = tempDir.resolve("targetDir");
        Files.createDirectories(targetDir);

        fileModificationUseCases.move(FileOperationRequest.builder().path("src.txt").targetPath("targetDir/dest.txt").build());
        assertFalse(Files.exists(src));
        assertTrue(Files.exists(targetDir.resolve("dest.txt")));
    }
}
