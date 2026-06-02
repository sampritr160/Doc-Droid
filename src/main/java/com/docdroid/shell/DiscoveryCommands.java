package com.docdroid.shell;

import com.docdroid.application.DiscoveryUseCases;
import com.docdroid.application.models.DiscoveryRequest;
import com.docdroid.terminal.SearchRenderer;
import com.docdroid.terminal.ThemeRenderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import java.io.IOException;

@ShellComponent
public class DiscoveryCommands {

    private final DiscoveryUseCases discoveryUseCases;
    private final SearchRenderer searchRenderer;
    private final ThemeRenderer themeRenderer;

    public DiscoveryCommands(DiscoveryUseCases discoveryUseCases, SearchRenderer searchRenderer, ThemeRenderer themeRenderer) {
        this.discoveryUseCases = discoveryUseCases;
        this.searchRenderer = searchRenderer;
        this.themeRenderer = themeRenderer;
    }

    @ShellMethod(key = "find", value = "Find files by name in current directory")
    public String find(String query) {
        try {
            return searchRenderer.renderResults(discoveryUseCases.find(DiscoveryRequest.builder().query(query).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "search", value = "Search for files")
    public String search(String query) {
        try {
            return searchRenderer.renderResults(discoveryUseCases.search(DiscoveryRequest.builder().query(query).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "where", value = "Locate files globally")
    public String where(String query) {
        try {
            return searchRenderer.renderResults(discoveryUseCases.where(DiscoveryRequest.builder().query(query).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }

    @ShellMethod(key = "locate", value = "Locate files globally")
    public String locate(String query) {
        try {
            return searchRenderer.renderResults(discoveryUseCases.locate(DiscoveryRequest.builder().query(query).build()));
        } catch (IOException e) {
            return themeRenderer.renderError(e.getMessage());
        }
    }
}
