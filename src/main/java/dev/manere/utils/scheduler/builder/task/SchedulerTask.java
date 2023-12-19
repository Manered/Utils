package dev.manere.utils.scheduler.builder.task;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * SchedulerTask is a utility class representing a task to be scheduled.
 * It can encapsulate either a runnable or a task consumer.
 */
public class SchedulerTask {
    private final @Nullable Runnable runnable;
    private final @Nullable Consumer<BukkitTask> task;

    /**
     * Constructs a new SchedulerTask with default values.
     */
    public SchedulerTask() {
        this.runnable = null;
        this.task = null;
    }

    /**
     * Constructs a new SchedulerTask with a provided runnable.
     *
     * @param runnable The runnable task to be scheduled.
     */
    public SchedulerTask(@Nullable Runnable runnable) {
        this.runnable = runnable;
        this.task = null;
    }

    /**
     * Constructs a new SchedulerTask with a provided task consumer.
     *
     * @param task The task consumer to be scheduled.
     */
    public SchedulerTask(@Nullable Consumer<BukkitTask> task) {
        this.task = task;
        this.runnable = null;
    }

    /**
     * Wraps a provided runnable into a SchedulerTask.
     *
     * @param runnable The runnable to be wrapped.
     * @return The wrapped SchedulerTask.
     */
    public static @NotNull SchedulerTask wrap(@Nullable Runnable runnable) {
        return new SchedulerTask(runnable);
    }

    /**
     * Wraps a provided task consumer into a SchedulerTask.
     *
     * @param task The task consumer to be wrapped.
     * @return The wrapped SchedulerTask.
     */
    public static @NotNull SchedulerTask wrap(@Nullable Consumer<BukkitTask> task) {
        return new SchedulerTask(task);
    }

    /**
     * Gets the runnable task.
     *
     * @return The runnable task, or null if not set.
     */
    public @Nullable Runnable runnable() {
        return runnable;
    }

    /**
     * Gets the task consumer.
     *
     * @return The task consumer, or null if not set.
     */
    public @Nullable Consumer<BukkitTask> task() {
        return task;
    }

    /**
     * Gets the appropriate object based on the set values (runnable or task).
     *
     * @return The appropriate object, or null if neither is set.
     */
    public @Nullable Object appropriate() {
        if (task == null && runnable != null) {
            return runnable;
        }

        if (runnable == null && task != null) {
            return task;
        }

        return null;
    }
}
