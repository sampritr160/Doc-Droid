package com.docdroid.application;

import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResultRegistry {
    private List<Path> lastResults = new ArrayList<>();

    public void setResults(List<Path> results) {
        this.lastResults = new ArrayList<>(results);
    }

    public List<Path> getResults() {
        return lastResults;
    }

    public Optional<Path> getResult(int index) {
        if (index > 0 && index <= lastResults.size()) {
            return Optional.of(lastResults.get(index - 1));
        }
        return Optional.empty();
    }

    public void clear() {
        lastResults.clear();
    }
}
