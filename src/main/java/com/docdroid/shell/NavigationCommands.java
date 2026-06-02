package com.docdroid.shell;

import com.docdroid.application.NavigationUseCase;
import com.docdroid.terminal.renderer.Renderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.nio.file.Path;
import java.util.List;

@ShellComponent
public class NavigationCommands {

    private final NavigationUseCase navigationUseCase;
    private final Renderer renderer;

    public NavigationCommands(NavigationUseCase navigationUseCase, Renderer renderer) {
        this.navigationUseCase = navigationUseCase;
        this.renderer = renderer;
    }

    @ShellMethod(key = "cd", value = "Change current directory")
    public void cd(@ShellOption(defaultValue = ".") String path) {
        try {
            navigationUseCase.cd(path);
            renderer.writeInfo("Current directory: " + navigationUseCase.pwd());
        } catch (Exception e) {
            renderer.writeError(e.getMessage());
        }
    }

    @ShellMethod(key = "pwd", value = "Print current directory")
    public void pwd() {
        renderer.writeInfo(navigationUseCase.pwd().toString());
    }

    @ShellMethod(key = "list", value = "List contents of current directory")
    public void list() {
        List<Path> contents = navigationUseCase.list();
        for (Path p : contents) {
            if (java.nio.file.Files.isDirectory(p)) {
                renderer.writeInfo("[DIR]  " + p.getFileName());
            } else {
                renderer.write("       " + p.getFileName());
            }
        }
    }

    @ShellMethod(key = "tree", value = "Show directory tree")
    public void tree(@ShellOption(defaultValue = "2") int depth) {
        renderer.write(navigationUseCase.tree(depth));
    }

    @ShellMethod(key = "drives", value = "List available drives")
    public void drives() {
        List<String> drives = navigationUseCase.drives();
        renderer.writeInfo("Available drives:");
        for (String drive : drives) {
            renderer.write("  " + drive);
        }
    }
}
