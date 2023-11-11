package dev.manere.utils.scheduler.builder;

import dev.manere.utils.library.Utils;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

/**
 * Utility class for building Bukkit scheduler tasks.
 */
public class SchedulerBuilder {

    private boolean asynchronous;
    private long delay;
    private long period;
    private Consumer<BukkitTask> task;
    private TaskType type;

    /**
     * Constructs a new SchedulerBuilder with default settings.
     */
    public SchedulerBuilder() {
        this.asynchronous = false;
        this.delay = 0;
        this.period = 0;
        this.task = null;
        this.type = TaskType.NORMAL;
    }

    /**
     * Creates a new instance of SchedulerBuilder.
     *
     * @return A new instance of SchedulerBuilder.
     */
    public static SchedulerBuilder scheduler() {
        return new SchedulerBuilder();
    }

    /**
     * Sets whether the task should be executed asynchronously.
     *
     * @param async true if the task should be asynchronous, false otherwise.
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder async(boolean async) {
        this.asynchronous = async;
        return this;
    }

    /**
     * Sets the delay and period (in ticks) of the task.
     *
     * @param delay The delay (in ticks).
     * @param period The period (in ticks).
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder time(long delay, long period) {
        this.delay = delay;
        this.period = period;
        return this;
    }

    /**
     * Sets the delay (in ticks) before the task starts.
     *
     * @param delay The delay (in ticks).
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder after(long delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Sets the type of the task.
     *
     * @param type The type of the task.
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder type(TaskType type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the period (in ticks) between task executions for repeating tasks.
     *
     * @param period The period (in ticks).
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder every(long period) {
        this.period = period;
        return this;
    }

    /**
     * Sets the task to be executed.
     *
     * @param task The task to be executed.
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder task(Consumer<BukkitTask> task) {
        this.task = task;
        return this;
    }

    /**
     * Builds and schedules the task according to the provided settings.
     * If the task is null, nothing is scheduled.
     */
    public void build() {
        if (task == null) return;

        if (type == TaskType.NORMAL) {
            if (asynchronous) Utils.plugin().getServer().getScheduler().runTaskAsynchronously(Utils.plugin(), task);

            else Utils.plugin().getServer().getScheduler().runTask(Utils.plugin(), task);
        }

        if (type == TaskType.LATER) {
            if (asynchronous) Utils.plugin().getServer().getScheduler().runTaskLaterAsynchronously(Utils.plugin(), task, delay);

            else Utils.plugin().getServer().getScheduler().runTaskLater(Utils.plugin(), task, delay);
        }

        if (type == TaskType.REPEATING) {
            if (asynchronous) Utils.plugin().getServer().getScheduler().runTaskTimerAsynchronously(Utils.plugin(), task, delay, period);

            else Utils.plugin().getServer().getScheduler().runTaskTimer(Utils.plugin(), task, delay, period);
        }
    }

    /**
     * Returns whether the task will be executed asynchronously.
     *
     * @return true if the task is asynchronous, false otherwise.
     */
    public boolean isAsynchronous() {
        return asynchronous;
    }

    /**
     * Returns the delay (in ticks) before the task starts.
     *
     * @return The delay (in ticks).
     */
    public long delay() {
        return delay;
    }

    /**
     * Returns the period (in ticks) between task executions for repeating tasks.
     *
     * @return The period (in ticks).
     */
    public long period() {
        return period;
    }

    /**
     * Returns the task to be executed.
     *
     * @return The task to be executed.
     */
    public Consumer<BukkitTask> task() {
        return task;
    }

    /**
     * Returns the type of the task.
     *
     * @return The type of the task.
     */
    public TaskType type() {
        return type;
    }
}
