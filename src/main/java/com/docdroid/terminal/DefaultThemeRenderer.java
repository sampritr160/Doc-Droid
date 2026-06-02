package com.docdroid.terminal;

import org.springframework.stereotype.Service;

@Service
public class DefaultThemeRenderer implements ThemeRenderer {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_CYAN = "\u001B[36m";

    @Override
    public String renderInfo(String message) {
        return ANSI_BLUE + message + ANSI_RESET;
    }

    @Override
    public String renderError(String message) {
        return ANSI_RED + message + ANSI_RESET;
    }

    @Override
    public String renderSuccess(String message) {
        return ANSI_GREEN + message + ANSI_RESET;
    }

    @Override
    public String renderWarning(String message) {
        return ANSI_YELLOW + message + ANSI_RESET;
    }

    @Override
    public String renderPrompt(String prompt) {
        return ANSI_CYAN + prompt + ANSI_RESET;
    }

    @Override
    public String renderBanner(String banner) {
        return ANSI_CYAN + banner + ANSI_RESET;
    }
}
