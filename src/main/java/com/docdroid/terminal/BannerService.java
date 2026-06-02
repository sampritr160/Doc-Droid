package com.docdroid.terminal;

import org.springframework.stereotype.Service;

@Service
public class BannerService {

    public String getBanner() {
        return """

                 ____             ____                _     _
                |  _ \\  ___   ___|  _ \\ _ __ ___ (_) __| |
                | | | |/ _ \\ / __| | | | '__/ _ \\| |/ _` |
                | |_| | (_) | (__| |_| | | | (_) | | (_| |
                |____/ \\___/ \\___|____/|_|  \\___/|_|\\__,_|

                Filesystem Operating Console
                Type 'help' for available commands.
                """;
    }

    public String getMascot() {
        return """
                   .--.
                  |o_o |
                  |:_/ |
                 //   \\ \\
                (|     | )
               /'\\_   _/`\\
               \\___)=(___/
                """;
    }
}
