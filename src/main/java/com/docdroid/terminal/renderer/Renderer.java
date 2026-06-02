package com.docdroid.terminal.renderer;

public interface Renderer {
    void write(String message);
    void writeInfo(String message);
    void writeSuccess(String message);
    void writeWarning(String message);
    void writeError(String message);
}
