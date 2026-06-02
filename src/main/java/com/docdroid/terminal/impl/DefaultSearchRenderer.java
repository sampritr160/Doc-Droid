package com.docdroid.terminal.impl;

import com.docdroid.terminal.SearchRenderer;
import com.docdroid.terminal.ThemeRenderer;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.util.List;

@Service
public class DefaultSearchRenderer implements SearchRenderer {
    private final ThemeRenderer themeRenderer;

    public DefaultSearchRenderer(ThemeRenderer themeRenderer) {
        this.themeRenderer = themeRenderer;
    }

    @Override
    public String renderResults(List<Path> results) {
        if (results.isEmpty()) {
            return themeRenderer.renderWarning("No results found.");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            sb.append(i + 1).append(". ").append(results.get(i).toAbsolutePath()).append("\n");
        }
        return themeRenderer.renderInfo(sb.toString());
    }
}
