package com.docdroid.application;

import com.docdroid.application.models.DiscoveryRequest;
import com.docdroid.filesystem.LiveSearchEngine;
import com.docdroid.filesystem.NavigationProvider;
import com.docdroid.filesystem.SearchEngine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryUseCases {

    private final NavigationProvider navigationProvider;
    private final ResultRegistry resultRegistry;
    private final SearchEngine searchEngine;
    private final LiveSearchEngine liveSearchEngine;

    public DiscoveryUseCases(NavigationProvider navigationProvider,
                             ResultRegistry resultRegistry,
                             @Qualifier("SQLiteIndexEngine") SearchEngine searchEngine,
                             LiveSearchEngine liveSearchEngine) {
        this.navigationProvider = navigationProvider;
        this.resultRegistry = resultRegistry;
        this.searchEngine = searchEngine;
        this.liveSearchEngine = liveSearchEngine;
    }

    public List<Path> find(DiscoveryRequest request) throws IOException {
        List<Path> results = liveSearchEngine.search(navigationProvider.getCurrentPath(), request.getQuery());
        resultRegistry.setResults(results);
        return results;
    }

    public List<Path> search(DiscoveryRequest request) throws IOException {
        return find(request);
    }

    public List<Path> where(DiscoveryRequest request) throws IOException {
        List<Path> results = searchEngine.search(request.getQuery());
        if (results.isEmpty()) {
            // Fallback to live search across all drives if index is empty
            for (Path root : navigationProvider.listDrives()) {
                results.addAll(liveSearchEngine.search(root, request.getQuery()));
            }
        }
        resultRegistry.setResults(results);
        return results;
    }

    public List<Path> locate(DiscoveryRequest request) throws IOException {
        return where(request);
    }
}
