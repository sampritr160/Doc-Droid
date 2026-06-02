package com.docdroid.application;

import com.docdroid.domain.FileRecord;
import com.docdroid.filesystem.operations.DirectoryOperator;
import com.docdroid.filesystem.operations.FileOperator;
import com.docdroid.shell.SearchResultRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileOperationUseCaseTest {

    private FileOperator fileOperator;
    private DirectoryOperator directoryOperator;
    private SearchResultRegistry registry;
    private FileOperationUseCase fileOperationUseCase;

    @BeforeEach
    void setUp() {
        fileOperator = mock(FileOperator.class);
        directoryOperator = mock(DirectoryOperator.class);
        registry = mock(SearchResultRegistry.class);
        fileOperationUseCase = new FileOperationUseCase(fileOperator, directoryOperator, registry);

        when(registry.getCurrentDirectory()).thenReturn(Path.of("/app"));
    }

    @Test
    void testMakeFile() throws IOException {
        fileOperationUseCase.makeFile("test.txt", null);
        verify(fileOperator).makeFile(Path.of("/app/test.txt"));
    }

    @Test
    void testMakeFileInDir() throws IOException {
        fileOperationUseCase.makeFile("test.txt", "/tmp");
        verify(fileOperator).makeFile(Path.of("/tmp/test.txt"));
    }

    @Test
    void testOpenWithIndex() throws IOException {
        FileRecord record = FileRecord.builder().path("/app/indexed.txt").build();
        when(registry.getResultByIndex(1)).thenReturn(record);

        fileOperationUseCase.open("1");

        verify(fileOperator).openFile(Path.of("/app/indexed.txt"));
    }
}
