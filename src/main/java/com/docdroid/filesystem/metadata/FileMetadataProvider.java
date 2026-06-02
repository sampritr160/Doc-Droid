package com.docdroid.filesystem.metadata;

import com.docdroid.domain.FileRecord;
import java.nio.file.Path;

public interface FileMetadataProvider {
    FileRecord getMetadata(Path path);
}
