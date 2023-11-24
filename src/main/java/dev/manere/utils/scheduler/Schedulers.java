package dev.manere.utils.scheduler;

import dev.manere.utils.scheduler.builder.SchedulerBuilder;
import dev.manere.utils.scheduler.stacker.SchedulerStacker;
import org.jetbrains.annotations.NotNull;

/**
 * This class provides methods for retrieving scheduler types.
 */
public class Schedulers {

    /**
     * Create and return a new instance of a synchronous scheduler.
     *
     * @return A new instance of a synchronous scheduler.
     */
    public static @NotNull SyncScheduler sync() {
        return new SyncScheduler();
    }

    /**
     * Create and return a new instance of an asynchronous scheduler.
     *
     * @return A new instance of an asynchronous scheduler.
     */
    public static @NotNull AsyncScheduler async() {
        return new AsyncScheduler();
    }

    /**
     * Create and return a new instance of a synchronous scheduler (an alias for sync() method).
     *
     * @return A new instance of a synchronous scheduler.
     */
    public static @NotNull SyncScheduler synchronous() {
        return sync();
    }

    /**
     * Create and return a new instance of an asynchronous scheduler (an alias for async() method).
     *
     * @return A new instance of an asynchronous scheduler.
     */
    public static @NotNull AsyncScheduler asynchronous() {
        return async();
    }

    /**
     * Creates a new instance of SchedulerBuilder.
     *
     * @return A new instance of SchedulerBuilder.
     */
    public static @NotNull SchedulerBuilder builder() {
        return SchedulerBuilder.scheduler();
    }

    /**
     * Creates a new instance of SchedulerStacker.
     *
     * @return A new instance of SchedulerStacker.
     */
    public static @NotNull SchedulerStacker stacker() {
        return SchedulerStacker.stacker();
    }
}
