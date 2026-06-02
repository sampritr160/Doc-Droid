package com.docdroid.terminal;

import java.nio.file.Path;
import java.util.List;

public interface TreeRenderer {
    String render(Path root, int depth);
}
