package com.docdroid.shell;

import com.docdroid.terminal.renderer.Renderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@ShellComponent
public class SystemCommands {

    private final Renderer renderer;

    public SystemCommands(Renderer renderer) {
        this.renderer = renderer;
    }

    @ShellMethod(key = "status", value = "Show system status")
    public void status() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();

        renderer.writeInfo("DocDroid Status:");
        renderer.write("  Uptime: " + (uptime / 1000) + " seconds");
        renderer.write("  Memory: " + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + " MB");
        renderer.write("  OS: " + System.getProperty("os.name"));
        renderer.write("  Java Version: " + System.getProperty("java.version"));
    }

    @ShellMethod(key = "config", value = "Show or set configuration")
    public void config() {
        renderer.writeInfo("Configuration:");
        renderer.write("  Database: docdroid.db");
        renderer.write("  Search Depth: 10");
        renderer.write("  Theme: Default (ANSI)");
    }
}
