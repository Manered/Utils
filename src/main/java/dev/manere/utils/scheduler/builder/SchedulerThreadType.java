package dev.manere.utils.scheduler.builder;

/**
 * An enumeration representing the types of threads for scheduling tasks.
 */
public enum SchedulerThreadType {
    /**
     * Synchronous execution on the main server thread.
     */
    SYNC,

    /**
     * Asynchronous execution on a separate thread.
     */
    ASYNC
}
