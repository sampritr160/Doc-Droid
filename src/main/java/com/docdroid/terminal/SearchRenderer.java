package com.docdroid.terminal;

import java.nio.file.Path;
import java.util.List;

public interface SearchRenderer {
    String renderResults(List<Path> results);
}
