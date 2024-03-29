package dev.manere.utils.scheduler;

import dev.manere.utils.library.Utils;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An abstract base class for custom schedulers.
 * This class provides access to various Bukkit/Paper schedulers and Folia region-related schedulers.
 */
public abstract class SchedulerBase {
    /**
     * Executes a task using the Bukkit scheduler.
     *
     * @param task The task to be executed.
     */
    public abstract void execute(@NotNull Consumer<BukkitTask> task);

    /**
     * Executes a runnable task using the Bukkit scheduler.
     *
     * @param runnable The runnable task to be executed.
     */
    public abstract void execute(@NotNull Runnable runnable);

    /**
     * Executes a task using the Bukkit scheduler after a specified number of ticks.
     *
     * @param task       The task to be executed.
     * @param afterTicks The number of ticks to wait before execution.
     */
    public abstract void execute(@NotNull Consumer<BukkitTask> task, int afterTicks);

    /**
     * Executes a runnable task using the Bukkit scheduler after a specified number of ticks.
     *
     * @param runnable   The runnable task to be executed.
     * @param afterTicks The number of ticks to wait before execution.
     */
    public abstract void execute(@NotNull Runnable runnable, int afterTicks);

    /**
     * Executes a task using the Bukkit scheduler after a specified number of ticks,
     * and then repeats the task every specified number of ticks.
     *
     * @param task       The task to be executed.
     * @param afterTicks The number of ticks to wait before the first execution.
     * @param everyTicks The number of ticks between each repeated execution.
     */
    public abstract void execute(@NotNull Consumer<BukkitTask> task, int afterTicks, int everyTicks);

    /**
     * Executes a runnable task using the Bukkit scheduler after a specified number of ticks,
     * and then repeats the task every specified number of ticks.
     *
     * @param runnable   The runnable task to be executed.
     * @param afterTicks The number of ticks to wait before the first execution.
     * @param everyTicks The number of ticks between each repeated execution.
     */
    public abstract void execute(@NotNull Runnable runnable, int afterTicks, int everyTicks);

    /**
     * Executes a supplier task and returns the result.
     *
     * @param supplier The supplier task to be executed.
     * @return The result of the supplier task.
     */
    public abstract @Nullable Object execute(@NotNull Supplier<?> supplier);

    /**
     * Executes a supplier task and returns the result.
     *
     * @param supplier The supplier task to be executed.
     * @return The result of the supplier task.
     */
    public abstract @Nullable <T> T supply(@NotNull Supplier<T> supplier);

    /**
     * Retrieves the Bukkit scheduler associated with this scheduler.
     *
     * @return The Bukkit scheduler.
     */
    public final @NotNull BukkitScheduler scheduler() {
        return Utils.plugin().getServer().getScheduler();
    }
}
