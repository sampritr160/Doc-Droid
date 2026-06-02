package com.docdroid.filesystem.search;

import com.docdroid.domain.FileRecord;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.util.List;

@Service
@Primary
public class SearchRouter implements SearchEngine {

    private final IndexedSearchEngine indexedSearchEngine;
    private final LiveSearchEngine liveSearchEngine;

    public SearchRouter(IndexedSearchEngine indexedSearchEngine, LiveSearchEngine liveSearchEngine) {
        this.indexedSearchEngine = indexedSearchEngine;
        this.liveSearchEngine = liveSearchEngine;
    }

    @Override
    public List<FileRecord> search(String query, Path root) {
        return getBestEngine().search(query, root);
    }

    @Override
    public List<FileRecord> findByExtension(String extension, Path root) {
        return getBestEngine().findByExtension(extension, root);
    }

    @Override
    public FileRecord getInfo(Path path) {
        return getBestEngine().getInfo(path);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    private SearchEngine getBestEngine() {
        if (indexedSearchEngine.isAvailable()) {
            return indexedSearchEngine;
        }
        return liveSearchEngine;
    }
}
