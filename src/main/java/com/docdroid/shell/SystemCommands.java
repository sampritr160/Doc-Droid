package com.docdroid.shell;

import com.docdroid.terminal.ThemeRenderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SystemCommands {

    private final ThemeRenderer themeRenderer;

    public SystemCommands(ThemeRenderer themeRenderer) {
        this.themeRenderer = themeRenderer;
    }

    @ShellMethod(key = "status", value = "Show system status")
    public String status() {
        return themeRenderer.renderSuccess("DocDroid is running. All systems go.");
    }
}
