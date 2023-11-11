package dev.manere.utils.scheduler;

import dev.manere.utils.library.Utils;
import dev.manere.utils.server.ServerUtils;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

/**
 * The SyncScheduler class provides methods for scheduling tasks to run synchronously.
 * This class is designed for use in Spigot plugins.
 */
public class SyncScheduler {

    /**
     * Schedules a task to run synchronously.
     *
     * @param task The task to be scheduled.
     */
    public void now(Consumer<BukkitTask> task) {
        ServerUtils.scheduler().runTask(Utils.plugin(), task);
    }

    /**
     * Schedules a runnable to run synchronously.
     *
     * @param runnable The runnable to be scheduled.
     */
    public void now(Runnable runnable) {
        ServerUtils.scheduler().runTask(Utils.plugin(), runnable);
    }

    /**
     * Schedules a task to run synchronously after a specified delay.
     *
     * @param runnable The task to be scheduled.
     * @param after    The delay (in ticks) before the task runs.
     */
    public void later(Consumer<BukkitTask> runnable, long after) {
        ServerUtils.scheduler().runTaskLater(Utils.plugin(), runnable, after);
    }

    /**
     * Schedules a runnable to run synchronously after a specified delay.
     *
     * @param runnable The runnable to be scheduled.
     * @param after    The delay (in ticks) before the runnable runs.
     */
    public void later(Runnable runnable, long after) {
        ServerUtils.scheduler().runTaskLater(Utils.plugin(), runnable, after);
    }

    /**
     * Schedules a task to run synchronously repeatedly, starting after a specified delay.
     *
     * @param task  The task to be scheduled.
     * @param after The delay (in ticks) before the first execution.
     * @param every The interval (in ticks) between subsequent executions.
     */
    public void repeating(Consumer<BukkitTask> task, long after, long every) {
        ServerUtils.scheduler().runTaskTimer(Utils.plugin(), task, after, every);
    }

    /**
     * Schedules a runnable to run synchronously repeatedly, starting after a specified delay.
     *
     * @param runnable The runnable to be scheduled.
     * @param after    The delay (in ticks) before the first execution.
     * @param every    The interval (in ticks) between subsequent executions.
     */
    public void repeating(Runnable runnable, long after, long every) {
        ServerUtils.scheduler().runTaskTimer(Utils.plugin(), runnable, after, every);
    }
}
