package dev.manere.utils.scheduler;

import dev.manere.utils.library.Utils;
import dev.manere.utils.server.ServerUtils;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The AsyncScheduler class provides methods for scheduling tasks to run asynchronously.
 * This class is designed for use in Spigot plugins.
 */
public class AsyncScheduler {

    /**
     * Schedules a task to run asynchronously.
     *
     * @param task The task to be scheduled.
     */
    public void now(@NotNull Consumer<BukkitTask> task) {
        ServerUtils.scheduler().runTaskAsynchronously(Utils.plugin(), task);
    }

    /**
     * Schedules a runnable to run asynchronously.
     *
     * @param runnable The runnable to be scheduled.
     */
    public void now(@NotNull Runnable runnable) {
        ServerUtils.scheduler().runTaskAsynchronously(Utils.plugin(), runnable);
    }

    /**
     * Schedules a task to run asynchronously after a specified delay.
     *
     * @param runnable The task to be scheduled.
     * @param after    The delay (in ticks) before the task runs.
     */
    public void later(@NotNull Consumer<BukkitTask> runnable, long after) {
        ServerUtils.scheduler().runTaskLaterAsynchronously(Utils.plugin(), runnable, after);
    }

    /**
     * Schedules a runnable to run asynchronously after a specified delay.
     *
     * @param runnable The runnable to be scheduled.
     * @param after    The delay (in ticks) before the runnable runs.
     */
    public void later(@NotNull Runnable runnable, long after) {
        ServerUtils.scheduler().runTaskLaterAsynchronously(Utils.plugin(), runnable, after);
    }

    /**
     * Schedules a task to run asynchronously repeatedly, starting after a specified delay.
     *
     * @param task  The task to be scheduled.
     * @param after The delay (in ticks) before the first execution.
     * @param every The interval (in ticks) between subsequent executions.
     */
    public void repeating(@NotNull Consumer<BukkitTask> task, long after, long every) {
        ServerUtils.scheduler().runTaskTimerAsynchronously(Utils.plugin(), task, after, every);
    }

    /**
     * Schedules a runnable to run asynchronously repeatedly, starting after a specified delay.
     *
     * @param runnable The runnable to be scheduled.
     * @param after    The delay (in ticks) before the first execution.
     * @param every    The interval (in ticks) between subsequent executions.
     */
    public void repeating(@NotNull Runnable runnable, long after, long every) {
        ServerUtils.scheduler().runTaskTimerAsynchronously(Utils.plugin(), runnable, after, every);
    }
}
