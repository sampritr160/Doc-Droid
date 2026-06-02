package com.docdroid.future.ai;

import java.nio.file.Path;

public interface FileAnalysisProvider {
    String analyze(Path path);
}
