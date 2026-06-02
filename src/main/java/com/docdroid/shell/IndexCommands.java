package com.docdroid.shell;

import com.docdroid.filesystem.indexing.IndexEngine;
import com.docdroid.infrastructure.persistence.FileIndexRepository;
import com.docdroid.terminal.renderer.Renderer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.io.File;

@ShellComponent
public class IndexCommands {

    private final IndexEngine indexEngine;
    private final FileIndexRepository repository;
    private final Renderer renderer;

    public IndexCommands(IndexEngine indexEngine, FileIndexRepository repository, Renderer renderer) {
        this.indexEngine = indexEngine;
        this.repository = repository;
        this.renderer = renderer;
    }

    @ShellMethod(key = "index start", value = "Start background indexing")
    public void indexStart() {
        indexEngine.startIndexing();
        renderer.writeSuccess("Indexing started in background.");
    }

    @ShellMethod(key = "index stop", value = "Stop background indexing")
    public void indexStop() {
        indexEngine.stopIndexing();
        renderer.writeInfo("Indexing stop requested.");
    }

    @ShellMethod(key = "index rebuild", value = "Clear and rebuild index")
    public void indexRebuild() {
        indexEngine.rebuildIndex();
        renderer.writeSuccess("Index cleared. Rebuild started in background.");
    }

    @ShellMethod(key = "index status", value = "Show indexing status")
    public void indexStatus() {
        IndexEngine.IndexStatus status = indexEngine.getStatus();
        renderer.writeInfo("Indexing Status:");
        renderer.write("  Running: " + status.isRunning());
        renderer.write("  Files Scanned: " + status.getFilesScanned());
        renderer.write("  Directories Scanned: " + status.getDirectoriesScanned());
        renderer.write("  Current Drive: " + status.getCurrentDrive());
        renderer.write("  Current Path: " + status.getCurrentPath());
        renderer.write("  Elapsed Time: " + (status.getElapsedTime() / 1000) + "s");
    }

    @ShellMethod(key = "index stats", value = "Show index statistics")
    public void indexStats() {
        long count = repository.count();
        File dbFile = new File("docdroid.db");

        renderer.writeInfo("Index Statistics:");
        renderer.write("  Total Indexed Files: " + count);
        if (dbFile.exists()) {
            renderer.write("  Database Size: " + (dbFile.length() / 1024 / 1024) + " MB");
        }
        renderer.write("  Database Path: " + dbFile.getAbsolutePath());
    }
}
