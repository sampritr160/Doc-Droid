package com.docdroid.shell;

import com.docdroid.domain.FileRecord;
import com.docdroid.filesystem.search.IndexedSearchEngine;
import com.docdroid.filesystem.search.LiveSearchEngine;
import com.docdroid.terminal.renderer.Renderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.nio.file.Path;
import java.util.List;

@ShellComponent
public class BenchmarkCommands {

    private final LiveSearchEngine liveSearchEngine;
    private final IndexedSearchEngine indexedSearchEngine;
    private final SearchResultRegistry registry;
    private final Renderer renderer;

    public BenchmarkCommands(LiveSearchEngine liveSearchEngine, IndexedSearchEngine indexedSearchEngine, SearchResultRegistry registry, Renderer renderer) {
        this.liveSearchEngine = liveSearchEngine;
        this.indexedSearchEngine = indexedSearchEngine;
        this.registry = registry;
        this.renderer = renderer;
    }

    @ShellMethod(key = "benchmark", value = "Benchmark Live vs Indexed search")
    public void benchmark(String query) {
        Path root = registry.getCurrentDirectory();
        renderer.writeInfo("Benchmarking query: " + query + " in " + root);

        long startLive = System.currentTimeMillis();
        List<FileRecord> liveResults = liveSearchEngine.search(query, root);
        long endLive = System.currentTimeMillis();
        long liveTime = endLive - startLive;

        long startIndexed = System.currentTimeMillis();
        List<FileRecord> indexedResults = indexedSearchEngine.search(query, root);
        long endIndexed = System.currentTimeMillis();
        long indexedTime = endIndexed - startIndexed;

        renderer.writeInfo("Results:");
        renderer.write("  Live Search:    " + liveTime + "ms (" + liveResults.size() + " results)");
        renderer.write("  Indexed Search: " + indexedTime + "ms (" + indexedResults.size() + " results)");

        if (indexedTime < liveTime && indexedTime > 0) {
            renderer.writeSuccess("Indexed search is " + (liveTime / (double)indexedTime) + "x faster!");
        }
    }
}
