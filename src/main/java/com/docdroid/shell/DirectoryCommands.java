package com.docdroid.shell;

import com.docdroid.application.FileOperationUseCase;
import com.docdroid.terminal.renderer.Renderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class DirectoryCommands {

    private final FileOperationUseCase fileOperationUseCase;
    private final Renderer renderer;

    public DirectoryCommands(FileOperationUseCase fileOperationUseCase, Renderer renderer) {
        this.fileOperationUseCase = fileOperationUseCase;
        this.renderer = renderer;
    }

    @ShellMethod(key = "make folder", value = "Create a new folder. Usage: make folder Notes [in D:\\Projects]")
    public void makeFolder(String name, @ShellOption(defaultValue = "in") String inKeyword, @ShellOption(defaultValue = ShellOption.NULL) String in) {
        String targetDir = in;
        if (!"in".equalsIgnoreCase(inKeyword) && in == null) {
            targetDir = inKeyword;
        }
        try {
            fileOperationUseCase.makeFolder(name, targetDir);
            renderer.writeSuccess("Folder created: " + name);
        } catch (Exception e) {
            renderer.writeError("Failed to create folder: " + e.getMessage());
        }
    }

    @ShellMethod(key = "delete folder", value = "Delete a folder")
    public void deleteFolder(String name) {
        try {
            fileOperationUseCase.delete(name);
            renderer.writeSuccess("Folder deleted: " + name);
        } catch (Exception e) {
            renderer.writeError("Failed to delete folder: " + e.getMessage());
        }
    }
}
