package com.docdroid.filesystem;

import com.docdroid.infrastructure.FileIndexRepository;
import com.docdroid.domain.FileIndex;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SQLiteIndexEngine implements SearchEngine, IndexEngine {

    private final FileIndexRepository repository;

    public SQLiteIndexEngine(FileIndexRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Path> search(String query) {
        return repository.findByNameContainingIgnoreCase(query).stream()
                .map(fileIndex -> Paths.get(fileIndex.getPath()))
                .collect(Collectors.toList());
    }

    @Override
    public void indexFile(Path path) {
        FileIndex entity = new FileIndex();
        entity.setPath(path.toAbsolutePath().toString());
        entity.setName(path.getFileName().toString());
        // Additional metadata could be set here
        repository.save(entity);
    }

    @Override
    public void indexDirectory(Path path) {
        // Implementation for recursive indexing
    }

    @Override
    public void clearIndex() {
        repository.deleteAll();
    }
}
