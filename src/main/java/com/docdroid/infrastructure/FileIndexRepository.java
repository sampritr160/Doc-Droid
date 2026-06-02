package com.docdroid.infrastructure;

import com.docdroid.domain.FileIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FileIndexRepository extends JpaRepository<FileIndex, Long> {
    List<FileIndex> findByNameContainingIgnoreCase(String name);
    List<FileIndex> findByExtensionIgnoreCase(String extension);
}
