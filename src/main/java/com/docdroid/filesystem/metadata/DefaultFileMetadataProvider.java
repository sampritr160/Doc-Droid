package com.docdroid.filesystem.metadata;

import com.docdroid.domain.FileRecord;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

@Service
public class DefaultFileMetadataProvider implements FileMetadataProvider {

    @Override
    public FileRecord getMetadata(Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            String fileName = path.getFileName() != null ? path.getFileName().toString() : path.toString();
            String extension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }

            return FileRecord.builder()
                    .name(fileName)
                    .path(path.toAbsolutePath().toString())
                    .extension(extension)
                    .size(attrs.size())
                    .lastModified(attrs.lastModifiedTime().toInstant())
                    .drive(path.getRoot() != null ? path.getRoot().toString() : "")
                    .createdAt(attrs.creationTime().toInstant())
                    .modifiedAt(attrs.lastModifiedTime().toInstant())
                    .build();
        } catch (IOException e) {
            return FileRecord.builder()
                    .name(path.getFileName() != null ? path.getFileName().toString() : path.toString())
                    .path(path.toAbsolutePath().toString())
                    .build();
        }
    }
}
