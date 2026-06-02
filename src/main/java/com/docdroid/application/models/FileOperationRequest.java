package com.docdroid.application.models;

import lombok.Builder;
import lombok.Data;
import java.nio.file.Path;

@Data
@Builder
public class FileOperationRequest {
    private String filename;
    private String path;
    private Integer resultIndex;
    private String targetPath;
    private String newName;
}
