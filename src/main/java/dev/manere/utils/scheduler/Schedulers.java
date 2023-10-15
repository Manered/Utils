package dev.manere.utils.scheduler;

import dev.manere.utils.scheduler.builder.SchedulerBuilder;

/**
 * This class provides methods for retrieving scheduler types.
 */
public class Schedulers {

    /**
     * Create and return a new instance of a synchronous scheduler.
     *
     * @return A new instance of a synchronous scheduler.
     */
    public static SyncScheduler sync() {
        return new SyncScheduler();
    }

    /**
     * Create and return a new instance of an asynchronous scheduler.
     *
     * @return A new instance of an asynchronous scheduler.
     */
    public static AsyncScheduler async() {
        return new AsyncScheduler();
    }

    /**
     * Create and return a new instance of a synchronous scheduler (an alias for sync() method).
     *
     * @return A new instance of a synchronous scheduler.
     */
    public static SyncScheduler synchronous() {
        return sync();
    }

    /**
     * Create and return a new instance of an asynchronous scheduler (an alias for async() method).
     *
     * @return A new instance of an asynchronous scheduler.
     */
    public static AsyncScheduler asynchronous() {
        return async();
    }

    /**
     * Creates a new instance of SchedulerBuilder.
     *
     * @return A new instance of SchedulerBuilder.
     */
    public static SchedulerBuilder builder() {
        return SchedulerBuilder.of();
    }
}
