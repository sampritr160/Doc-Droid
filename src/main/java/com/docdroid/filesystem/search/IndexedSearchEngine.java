package com.docdroid.filesystem.search;

import com.docdroid.domain.FileRecord;
import com.docdroid.infrastructure.persistence.FileIndexRepository;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexedSearchEngine implements SearchEngine {

    private final FileIndexRepository repository;

    public IndexedSearchEngine(FileIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FileRecord> search(String query, Path root) {
        List<FileRecord> results = repository.findByNameContainingIgnoreCase(query);
        if (root != null) {
            String rootPath = root.toAbsolutePath().toString();
            return results.stream()
                    .filter(r -> r.getPath().startsWith(rootPath))
                    .collect(Collectors.toList());
        }
        return results;
    }

    @Override
    public List<FileRecord> findByExtension(String extension, Path root) {
        List<FileRecord> results = repository.findByExtensionIgnoreCase(extension);
        if (root != null) {
            String rootPath = root.toAbsolutePath().toString();
            return results.stream()
                    .filter(r -> r.getPath().startsWith(rootPath))
                    .collect(Collectors.toList());
        }
        return results;
    }

    @Override
    public FileRecord getInfo(Path path) {
        List<FileRecord> results = repository.findByPath(path.toAbsolutePath().toString());
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public boolean isAvailable() {
        return repository.count() > 0;
    }
}
