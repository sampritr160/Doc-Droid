package com.docdroid.terminal;

public interface ThemeRenderer {
    String renderInfo(String message);
    String renderError(String message);
    String renderSuccess(String message);
    String renderWarning(String message);
    String renderPrompt(String prompt);
    String renderBanner(String banner);
}
