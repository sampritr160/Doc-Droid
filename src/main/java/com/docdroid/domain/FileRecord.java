package com.docdroid.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_records", indexes = {
    @Index(name = "idx_file_name", columnList = "name"),
    @Index(name = "idx_extension", columnList = "extension"),
    @Index(name = "idx_path", columnList = "path", unique = true)
})
public class FileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String path;
    private String name;
    private String extension;
    private Long size;
    private Instant lastModified;
    private String drive;

    private Instant createdAt;
    private Instant modifiedAt;
    private Instant lastIndexedAt;
}
