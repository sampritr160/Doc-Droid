package com.docdroid.shell;

import com.docdroid.application.FileOperationUseCase;
import com.docdroid.terminal.renderer.Renderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class FileCommands {

    private final FileOperationUseCase fileOperationUseCase;
    private final Renderer renderer;

    public FileCommands(FileOperationUseCase fileOperationUseCase, Renderer renderer) {
        this.fileOperationUseCase = fileOperationUseCase;
        this.renderer = renderer;
    }

    @ShellMethod(key = "make", value = "Create a new file. Usage: make report.txt [in D:\\Projects]")
    public void make(String name, @ShellOption(defaultValue = "in", help = "in keyword") String inKeyword, @ShellOption(defaultValue = ShellOption.NULL) String in) {
        String targetDir = in;
        if (!"in".equalsIgnoreCase(inKeyword) && in == null) {
            targetDir = inKeyword;
        }

        try {
            fileOperationUseCase.makeFile(name, targetDir);
            renderer.writeSuccess("File created: " + name);
        } catch (Exception e) {
            renderer.writeError("Failed to create file: " + e.getMessage());
        }
    }

    @ShellMethod(key = "open", value = "Open a file")
    public void open(String pathOrIndex) {
        try {
            fileOperationUseCase.open(pathOrIndex);
            renderer.writeSuccess("Opened: " + pathOrIndex);
        } catch (Exception e) {
            renderer.writeError("Failed to open file: " + e.getMessage());
        }
    }

    @ShellMethod(key = "rename", value = "Rename a file. Usage: rename report.txt [to] report-final.txt")
    public void rename(String pathOrIndex, @ShellOption(defaultValue = "to") String toKeyword, @ShellOption(defaultValue = ShellOption.NULL) String to) {
        String newName = to;
        if (!"to".equalsIgnoreCase(toKeyword) && to == null) {
            newName = toKeyword;
        }
        try {
            fileOperationUseCase.rename(pathOrIndex, newName);
            renderer.writeSuccess("Renamed " + pathOrIndex + " to " + newName);
        } catch (Exception e) {
            renderer.writeError("Failed to rename: " + e.getMessage());
        }
    }

    @ShellMethod(key = "delete", value = "Delete a file or folder")
    public void delete(String pathOrIndex) {
        try {
            fileOperationUseCase.delete(pathOrIndex);
            renderer.writeSuccess("Deleted: " + pathOrIndex);
        } catch (Exception e) {
            renderer.writeError("Failed to delete: " + e.getMessage());
        }
    }

    @ShellMethod(key = "copy", value = "Copy a file. Usage: copy report.txt [from D:\\Source] to D:\\Backup")
    public void copy(
            String pathOrIndex,
            @ShellOption(defaultValue = "to") String keyword1,
            @ShellOption(defaultValue = ShellOption.NULL) String arg1,
            @ShellOption(defaultValue = ShellOption.NULL) String keyword2,
            @ShellOption(defaultValue = ShellOption.NULL) String arg2) {

        String source = pathOrIndex;
        String destination = null;

        if ("from".equalsIgnoreCase(keyword1)) {
            source = arg1 + "/" + pathOrIndex;
            if ("to".equalsIgnoreCase(keyword2)) {
                destination = arg2;
            }
        } else if ("to".equalsIgnoreCase(keyword1)) {
            destination = arg1;
        }

        try {
            fileOperationUseCase.copy(source, destination);
            renderer.writeSuccess("Copied " + source + " to " + destination);
        } catch (Exception e) {
            renderer.writeError("Failed to copy: " + e.getMessage());
        }
    }

    @ShellMethod(key = "move", value = "Move a file. Usage: move report.txt [from D:\\Source] to D:\\Backup")
    public void move(
            String pathOrIndex,
            @ShellOption(defaultValue = "to") String keyword1,
            @ShellOption(defaultValue = ShellOption.NULL) String arg1,
            @ShellOption(defaultValue = ShellOption.NULL) String keyword2,
            @ShellOption(defaultValue = ShellOption.NULL) String arg2) {

        String source = pathOrIndex;
        String destination = null;

        if ("from".equalsIgnoreCase(keyword1)) {
            source = arg1 + "/" + pathOrIndex;
            if ("to".equalsIgnoreCase(keyword2)) {
                destination = arg2;
            }
        } else if ("to".equalsIgnoreCase(keyword1)) {
            destination = arg1;
        }

        try {
            fileOperationUseCase.move(source, destination);
            renderer.writeSuccess("Moved " + source + " to " + destination);
        } catch (Exception e) {
            renderer.writeError("Failed to move: " + e.getMessage());
        }
    }
}
