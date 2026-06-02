package com.docdroid.shell;

import com.docdroid.application.FileSearchUseCase;
import com.docdroid.domain.FileRecord;
import com.docdroid.terminal.renderer.Renderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class DiscoveryCommands {

    private final FileSearchUseCase fileSearchUseCase;
    private final Renderer renderer;

    public DiscoveryCommands(FileSearchUseCase fileSearchUseCase, Renderer renderer) {
        this.fileSearchUseCase = fileSearchUseCase;
        this.renderer = renderer;
    }

    @ShellMethod(key = "find", value = "Find files by name in current directory")
    public void find(String query) {
        displayResults(fileSearchUseCase.find(query));
    }

    @ShellMethod(key = "where", value = "Find files by name globally")
    public void where(String query) {
        displayResults(fileSearchUseCase.findGlobal(query));
    }

    @ShellMethod(key = "search", value = "Search for files")
    public void search(String query) {
        displayResults(fileSearchUseCase.find(query));
    }

    @ShellMethod(key = "locate", value = "Locate a file and show with indices")
    public void locate(String query) {
        displayResults(fileSearchUseCase.findGlobal(query));
    }

    @ShellMethod(key = "info", value = "Show information about a file")
    public void info(String pathOrIndex) {
        FileRecord record = fileSearchUseCase.info(pathOrIndex);
        if (record != null) {
            renderer.writeInfo("File Information:");
            renderer.write("  Name: " + record.getName());
            renderer.write("  Path: " + record.getPath());
            renderer.write("  Size: " + record.getSize() + " bytes");
            renderer.write("  Extension: " + record.getExtension());
            renderer.write("  Last Modified: " + record.getLastModified());
        } else {
            renderer.writeError("File not found: " + pathOrIndex);
        }
    }

    private void displayResults(List<FileRecord> results) {
        if (results.isEmpty()) {
            renderer.writeWarning("No results found.");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            FileRecord record = results.get(i);
            renderer.write((i + 1) + ". " + record.getPath());
        }
    }
}
