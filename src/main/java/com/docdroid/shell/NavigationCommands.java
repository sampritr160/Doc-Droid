package com.docdroid.shell;

import com.docdroid.application.NavigationUseCases;
import com.docdroid.application.VisualizationUseCases;
import com.docdroid.application.SystemInfoUseCases;
import com.docdroid.application.models.NavigationRequest;
import com.docdroid.terminal.ThemeRenderer;
import com.docdroid.terminal.TreeRenderer;
import com.docdroid.filesystem.NavigationProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import java.io.IOException;

@ShellComponent
public class NavigationCommands {

    private final NavigationUseCases navigationUseCases;
    private final VisualizationUseCases visualizationUseCases;
    private final SystemInfoUseCases systemInfoUseCases;
    private final ThemeRenderer themeRenderer;
    private final TreeRenderer treeRenderer;
    private final NavigationProvider navigationProvider;

    public NavigationCommands(NavigationUseCases navigationUseCases,
                              VisualizationUseCases visualizationUseCases,
                              SystemInfoUseCases systemInfoUseCases,
                              ThemeRenderer themeRenderer,
                              TreeRenderer treeRenderer,
                              NavigationProvider navigationProvider) {
        this.navigationUseCases = navigationUseCases;
        this.visualizationUseCases = visualizationUseCases;
        this.systemInfoUseCases = systemInfoUseCases;
        this.themeRenderer = themeRenderer;
        this.treeRenderer = treeRenderer;
        this.navigationProvider = navigationProvider;
    }

    @ShellMethod(key = "pwd", value = "Print working directory")
    public String pwd() {
        return themeRenderer.renderInfo(navigationUseCases.pwd());
    }

    @ShellMethod(key = "cd", value = "Change directory")
    public String cd(@ShellOption(defaultValue = ".") String path) {
        try {
            return themeRenderer.renderSuccess(navigationUseCases.cd(NavigationRequest.builder().path(path).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "list", value = "List directory contents")
    public String list(@ShellOption(defaultValue = ShellOption.NULL) String path) {
        try {
            return themeRenderer.renderInfo(visualizationUseCases.list(path));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "tree", value = "Show directory tree")
    public String tree(@ShellOption(defaultValue = ShellOption.NULL) String path,
                       @ShellOption(defaultValue = "2") Integer depth) {
        java.nio.file.Path targetPath = path == null ? navigationProvider.getCurrentPath() :
                          navigationProvider.getCurrentPath().resolve(path).normalize();
        return treeRenderer.render(targetPath, depth);
    }

    @ShellMethod(key = "drives", value = "List available drives")
    public String drives() {
        return themeRenderer.renderInfo(systemInfoUseCases.listDrives());
    }

    @ShellMethod(key = "info", value = "Show file or folder information")
    public String info(@ShellOption(defaultValue = ShellOption.NULL) String path,
                       @ShellOption(defaultValue = ShellOption.NULL) Integer index) {
        try {
            return themeRenderer.renderInfo(systemInfoUseCases.info(path, index));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }
}
