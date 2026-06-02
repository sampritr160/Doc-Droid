package com.docdroid.shell;

import com.docdroid.application.DirectoryModificationUseCases;
import com.docdroid.application.models.FileOperationRequest;
import com.docdroid.terminal.StatusRenderer;
import com.docdroid.terminal.ThemeRenderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import java.io.IOException;

@ShellComponent
public class DirectoryModificationCommands {

    private final DirectoryModificationUseCases directoryModificationUseCases;
    private final StatusRenderer statusRenderer;
    private final ThemeRenderer themeRenderer;

    public DirectoryModificationCommands(DirectoryModificationUseCases directoryModificationUseCases,
                                         StatusRenderer statusRenderer,
                                         ThemeRenderer themeRenderer) {
        this.directoryModificationUseCases = directoryModificationUseCases;
        this.statusRenderer = statusRenderer;
        this.themeRenderer = themeRenderer;
    }

    @ShellMethod(key = "make folder", value = "Create a new folder")
    public String makeFolder(String folder, @ShellOption(defaultValue = ShellOption.NULL) String in) {
        try {
            return statusRenderer.renderStatus(directoryModificationUseCases.makeFolder(
                FileOperationRequest.builder().filename(folder).path(in).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "delete folder", value = "Delete a folder")
    public String deleteFolder(@ShellOption(defaultValue = ShellOption.NULL) String path,
                               @ShellOption(defaultValue = ShellOption.NULL) Integer index) {
        try {
            return statusRenderer.renderStatus(directoryModificationUseCases.deleteFolder(
                FileOperationRequest.builder().path(path).resultIndex(index).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }
}
