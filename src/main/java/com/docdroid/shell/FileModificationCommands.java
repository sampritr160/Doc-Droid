package com.docdroid.shell;

import com.docdroid.application.FileModificationUseCases;
import com.docdroid.application.models.FileOperationRequest;
import com.docdroid.terminal.StatusRenderer;
import com.docdroid.terminal.ThemeRenderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import java.io.IOException;

@ShellComponent
public class FileModificationCommands {

    private final FileModificationUseCases fileModificationUseCases;
    private final StatusRenderer statusRenderer;
    private final ThemeRenderer themeRenderer;

    public FileModificationCommands(FileModificationUseCases fileModificationUseCases,
                                    StatusRenderer statusRenderer,
                                    ThemeRenderer themeRenderer) {
        this.fileModificationUseCases = fileModificationUseCases;
        this.statusRenderer = statusRenderer;
        this.themeRenderer = themeRenderer;
    }

    @ShellMethod(key = "make", value = "Create a new file")
    public String make(String filename, @ShellOption(defaultValue = ShellOption.NULL) String in) {
        try {
            return statusRenderer.renderStatus(fileModificationUseCases.make(
                FileOperationRequest.builder().filename(filename).path(in).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "open", value = "Open a file")
    public String open(@ShellOption(defaultValue = ShellOption.NULL) String path,
                       @ShellOption(defaultValue = ShellOption.NULL) Integer index) {
        try {
            return statusRenderer.renderStatus(fileModificationUseCases.open(
                FileOperationRequest.builder().path(path).resultIndex(index).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "delete", value = "Delete a file")
    public String delete(@ShellOption(defaultValue = ShellOption.NULL) String path,
                         @ShellOption(defaultValue = ShellOption.NULL) Integer index) {
        try {
            return statusRenderer.renderStatus(fileModificationUseCases.delete(
                FileOperationRequest.builder().path(path).resultIndex(index).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "copy", value = "Copy a file")
    public String copy(@ShellOption(defaultValue = ShellOption.NULL) String from,
                       @ShellOption(defaultValue = ShellOption.NULL) Integer index,
                       String to) {
        try {
            return statusRenderer.renderStatus(fileModificationUseCases.copy(
                FileOperationRequest.builder().path(from).resultIndex(index).targetPath(to).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "move", value = "Move a file")
    public String move(@ShellOption(defaultValue = ShellOption.NULL) String from,
                       @ShellOption(defaultValue = ShellOption.NULL) Integer index,
                       @ShellOption(defaultValue = ShellOption.NULL) String to) {
        try {
            return statusRenderer.renderStatus(fileModificationUseCases.move(
                FileOperationRequest.builder().path(from).resultIndex(index).targetPath(to).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "rename", value = "Rename a file")
    public String rename(@ShellOption(defaultValue = ShellOption.NULL) String path,
                         @ShellOption(defaultValue = ShellOption.NULL) Integer index,
                         String to) {
        try {
            return statusRenderer.renderStatus(fileModificationUseCases.rename(
                FileOperationRequest.builder().path(path).resultIndex(index).newName(to).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }
}
