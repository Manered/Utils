package dev.manere.utils.scheduler;

import dev.manere.utils.scheduler.async.AsyncScheduler;
import dev.manere.utils.scheduler.builder.SchedulerBuilder;
import dev.manere.utils.scheduler.stacker.SchedulerStacker;
import dev.manere.utils.scheduler.sync.SyncScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

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
     * Create and return a new instance of a scheduler builder.
     *
     * @return A new instance of a scheduler builder.
     */
    public static @NotNull SchedulerBuilder builder() {
        return new SchedulerBuilder();
    }

    /**
     * Create and return a new instance of a scheduler builder.
     *
     * @return A new instance of a scheduler builder.
     */
    public static @NotNull SchedulerBuilder builder(@Nullable Runnable runnable) {
        if (runnable != null) {
            return new SchedulerBuilder(runnable);
        }

        return new SchedulerBuilder();
    }

    /**
     * Create and return a new instance of a scheduler builder.
     *
     * @return A new instance of a scheduler builder.
     */
    public static @NotNull SchedulerBuilder builder(@Nullable Consumer<BukkitTask> task) {
        if (task != null) {
            return new SchedulerBuilder(task);
        }

        return new SchedulerBuilder();
    }

    /**
     * Create and return a new instance of a scheduler stacker.
     *
     * @return A new instance of a scheduler stacker.
     */
    public static @NotNull SchedulerStacker stacker() {
        return new SchedulerStacker();
    }
}
