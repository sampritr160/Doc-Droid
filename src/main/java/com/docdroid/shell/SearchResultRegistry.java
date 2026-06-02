package com.docdroid.shell;

import com.docdroid.domain.FileRecord;
import org.springframework.stereotype.Component;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SearchResultRegistry {
    private Path currentDirectory = Paths.get("").toAbsolutePath();
    private final List<FileRecord> lastSearchResults = new ArrayList<>();
    private final Map<Integer, Long> expiryMap = new ConcurrentHashMap<>();
    private static final long EXPIRY_MS = 10 * 60 * 1000; // 10 minutes

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Path currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public List<FileRecord> getLastSearchResults() {
        return new ArrayList<>(lastSearchResults);
    }

    public void setLastSearchResults(List<FileRecord> results) {
        lastSearchResults.clear();
        lastSearchResults.addAll(results);
        long now = System.currentTimeMillis();
        expiryMap.clear();
        for (int i = 1; i <= results.size(); i++) {
            expiryMap.put(i, now + EXPIRY_MS);
        }
    }

    public FileRecord getResultByIndex(int index) {
        if (index > 0 && index <= lastSearchResults.size()) {
            Long expiry = expiryMap.get(index);
            if (expiry != null && System.currentTimeMillis() < expiry) {
                return lastSearchResults.get(index - 1);
            }
        }
        return null;
    }
}
