package com.docdroid.terminal.renderer;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ConsoleRenderer implements Renderer {

    private final Terminal terminal;

    public ConsoleRenderer(@Lazy Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void write(String message) {
        terminal.writer().println(message);
        terminal.flush();
    }

    @Override
    public void writeInfo(String message) {
        print(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN));
    }

    @Override
    public void writeSuccess(String message) {
        print(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

    @Override
    public void writeWarning(String message) {
        print(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

    @Override
    public void writeError(String message) {
        print(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
    }

    private void print(String message, AttributedStyle style) {
        AttributedStringBuilder builder = new AttributedStringBuilder();
        builder.append(message, style);
        terminal.writer().println(builder.toAnsi());
        terminal.flush();
    }
}
