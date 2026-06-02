package com.docdroid.terminal.impl;

import com.docdroid.terminal.StatusRenderer;
import com.docdroid.terminal.ThemeRenderer;
import org.springframework.stereotype.Service;

@Service
public class DefaultStatusRenderer implements StatusRenderer {
    private final ThemeRenderer themeRenderer;

    public DefaultStatusRenderer(ThemeRenderer themeRenderer) {
        this.themeRenderer = themeRenderer;
    }

    @Override
    public String renderStatus(String status) {
        return themeRenderer.renderSuccess(status);
    }
}
