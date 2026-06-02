package com.docdroid.application.history;

import java.util.List;

public interface HistoryService {
    void add(String command);
    List<String> getRecent(int count);
    void clear();
}
