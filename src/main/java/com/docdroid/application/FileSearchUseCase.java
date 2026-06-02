package com.docdroid.application;

import com.docdroid.domain.FileRecord;
import com.docdroid.filesystem.search.SearchEngine;
import com.docdroid.shell.SearchResultRegistry;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSearchUseCase {
    private final SearchEngine searchEngine;
    private final SearchResultRegistry registry;

    public FileSearchUseCase(SearchEngine searchEngine, SearchResultRegistry registry) {
        this.searchEngine = searchEngine;
        this.registry = registry;
    }

    public List<FileRecord> find(String query) {
        List<FileRecord> results = searchEngine.search(query, registry.getCurrentDirectory());
        results = results.stream()
                .filter(r -> Path.of(r.getPath()).startsWith(registry.getCurrentDirectory()))
                .collect(Collectors.toList());
        registry.setLastSearchResults(results);
        return results;
    }

    public List<FileRecord> findGlobal(String query) {
        Path root = registry.getCurrentDirectory().getRoot();
        if (root == null) {
            root = Path.of("/");
        }
        List<FileRecord> results = searchEngine.search(query, root);
        registry.setLastSearchResults(results);
        return results;
    }

    public List<FileRecord> findByExtension(String extension) {
        List<FileRecord> results = searchEngine.findByExtension(extension, registry.getCurrentDirectory());
        registry.setLastSearchResults(results);
        return results;
    }

    public FileRecord info(String pathOrIndex) {
        Path path = resolvePath(pathOrIndex);
        return searchEngine.getInfo(path);
    }

    private Path resolvePath(String pathOrIndex) {
        try {
            int index = Integer.parseInt(pathOrIndex);
            FileRecord record = registry.getResultByIndex(index);
            if (record != null) {
                return Path.of(record.getPath());
            }
        } catch (NumberFormatException ignored) {}
        return registry.getCurrentDirectory().resolve(pathOrIndex).normalize();
    }
}
