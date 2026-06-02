package com.docdroid.infrastructure.persistence;

import com.docdroid.domain.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileIndexRepository extends JpaRepository<FileRecord, Long> {
    List<FileRecord> findByNameContainingIgnoreCase(String name);
    List<FileRecord> findByNameIgnoreCase(String name);
    List<FileRecord> findByExtensionIgnoreCase(String extension);
    List<FileRecord> findByPath(String path);
    void deleteByDrive(String drive);
}
