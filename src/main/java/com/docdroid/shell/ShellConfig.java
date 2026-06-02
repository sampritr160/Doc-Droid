package com.docdroid.shell;

import com.docdroid.terminal.ThemeRenderer;
import org.springframework.shell.jline.PromptProvider;
import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class ShellConfig {

    private final ThemeRenderer themeRenderer;

    public ShellConfig(ThemeRenderer themeRenderer) {
        this.themeRenderer = themeRenderer;
    }

    @Bean
    public PromptProvider customPromptProvider() {
        return () -> new AttributedString(themeRenderer.renderPrompt("doc-droid:> "));
    }
}
