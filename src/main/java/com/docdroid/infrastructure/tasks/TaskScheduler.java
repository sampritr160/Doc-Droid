package com.docdroid.infrastructure.tasks;

public interface TaskScheduler {
    void schedule(Runnable task, String name);
    void cancel(String name);
}
