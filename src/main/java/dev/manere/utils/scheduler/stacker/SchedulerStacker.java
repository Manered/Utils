package dev.manere.utils.scheduler.stacker;

import dev.manere.utils.scheduler.AsyncScheduler;
import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.scheduler.SyncScheduler;
import dev.manere.utils.scheduler.TickTimes;
import dev.manere.utils.scheduler.builder.SchedulerBuilder;
import dev.manere.utils.scheduler.builder.TaskType;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A utility class for stacking synchronous and asynchronous Bukkit scheduler tasks.
 * Allows chaining multiple tasks with different timing options.
 *
 * @see SchedulerBuilder
 * @see AsyncScheduler
 * @see SyncScheduler
 * @see TickTimes
 * @see Schedulers
 * @see TaskType
 */
public class SchedulerStacker {

    /**
     * Creates a new instance of SchedulerStacker.
     * @return A new @NotNull SchedulerStacker instance.
     */
    public static @NotNull SchedulerStacker stacker() {
        return new SchedulerStacker();
    }

    /**
     * Adds an asynchronous task to the scheduler.
     * @param task The asynchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker thenAsync(@NotNull Consumer<BukkitTask> task) {
        Schedulers.async().now(task);
        return this;
    }

    /**
     * Adds an asynchronous task to the scheduler.
     * @param task The asynchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker thenAsync(@NotNull Runnable task) {
        Schedulers.async().now(task);
        return this;
    }

    /**
     * Adds a synchronous task to the scheduler.
     * @param task The synchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker then(@NotNull Consumer<BukkitTask> task) {
        Schedulers.sync().now(task);
        return this;
    }

    /**
     * Adds a synchronous task to the scheduler.
     * @param task The synchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker then(@NotNull Runnable task) {
        Schedulers.sync().now(task);
        return this;
    }

    /**
     * Adds an asynchronous delayed task to the scheduler.
     * @param after The delay before the task is executed, in server ticks.
     * @param task The asynchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker thenAsync(long after, @NotNull Consumer<BukkitTask> task) {
        Schedulers.async().later(task, after);
        return this;
    }

    /**
     * Adds an asynchronous delayed task to the scheduler.
     * @param after The delay before the task is executed, in server ticks.
     * @param task The asynchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker thenAsync(long after, @NotNull Runnable task) {
        Schedulers.async().later(task, after);
        return this;
    }

    /**
     * Adds a synchronous delayed task to the scheduler.
     * @param after The delay before the task is executed, in server ticks.
     * @param task The synchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker then(long after, @NotNull Consumer<BukkitTask> task) {
        Schedulers.sync().later(task, after);
        return this;
    }

    /**
     * Adds a synchronous delayed task to the scheduler.
     * @param after The delay before the task is executed, in server ticks.
     * @param task The synchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker then(long after, @NotNull Runnable task) {
        Schedulers.sync().later(task, after);
        return this;
    }

    /**
     * Adds an asynchronous repeating task to the scheduler.
     * @param after The delay before the first execution, in server ticks.
     * @param every The time between successive executions, in server ticks.
     * @param task The asynchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker thenAsync(long after, long every, @NotNull Consumer<BukkitTask> task) {
        Schedulers.async().repeating(task, after, every);
        return this;
    }

    /**
     * Adds an asynchronous repeating task to the scheduler.
     * @param after The delay before the first execution, in server ticks.
     * @param every The time between successive executions, in server ticks.
     * @param task The asynchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker thenAsync(long after, long every, @NotNull Runnable task) {
        Schedulers.async().repeating(task, after, every);
        return this;
    }

    /**
     * Adds a synchronous repeating task to the scheduler.
     * @param after The delay before the first execution, in server ticks.
     * @param every The time between successive executions, in server ticks.
     * @param task The synchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker then(long after, long every, @NotNull Consumer<BukkitTask> task) {
        Schedulers.sync().repeating(task, after, every);
        return this;
    }

    /**
     * Adds a synchronous repeating task to the scheduler.
     * @param after The delay before the first execution, in server ticks.
     * @param every The time between successive executions, in server ticks.
     * @param task The synchronous task to be executed.
     * @return The current @NotNull SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker then(long after, long every, @NotNull Runnable task) {
        Schedulers.sync().repeating(task, after, every);
        return this;
    }
}
