package com.docdroid.terminal.impl;

import com.docdroid.terminal.TreeRenderer;
import com.docdroid.terminal.ThemeRenderer;
import com.docdroid.filesystem.DirectoryOperator;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.io.IOException;

@Service
public class DefaultTreeRenderer implements TreeRenderer {
    private final ThemeRenderer themeRenderer;
    private final DirectoryOperator directoryOperator;

    public DefaultTreeRenderer(ThemeRenderer themeRenderer, DirectoryOperator directoryOperator) {
        this.themeRenderer = themeRenderer;
        this.directoryOperator = directoryOperator;
    }

    @Override
    public String render(Path root, int depth) {
        try {
            return themeRenderer.renderInfo(directoryOperator.getTree(root, depth));
        } catch (IOException e) {
            return themeRenderer.renderError("Error rendering tree: " + e.getMessage());
        }
    }
}
