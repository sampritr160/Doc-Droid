package com.docdroid.terminal.banner;

import com.docdroid.terminal.renderer.Renderer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Banner implements CommandLineRunner {

    private final Renderer renderer;

    public Banner(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void run(String... args) {
        renderer.writeSuccess("******************************************");
        renderer.writeSuccess("*                                        *");
        renderer.writeSuccess("*      DOCDROID OPERATING CONSOLE        *");
        renderer.writeSuccess("*                                        *");
        renderer.writeSuccess("******************************************");
        renderer.writeInfo("Welcome to the next generation filesystem operator.");
    }
}
