package dev.manere.utils.scheduler;

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
    public static SchedulerBuilder of() {
        return new SchedulerBuilder();
    }

    /**
     * Sets whether the task should be executed asynchronously.
     *
     * @param asynchronous true if the task should be asynchronous, false otherwise.
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }

    /**
     * Sets the delay (in ticks) before the task starts.
     *
     * @param delay The delay (in ticks).
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Sets the type of the task.
     *
     * @param type The type of the task.
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder setType(TaskType type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the period (in ticks) between task executions for repeating tasks.
     *
     * @param period The period (in ticks).
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder setPeriod(long period) {
        this.period = period;
        return this;
    }

    /**
     * Sets the task to be executed.
     *
     * @param task The task to be executed.
     * @return This SchedulerBuilder for method chaining.
     */
    public SchedulerBuilder setTask(Consumer<BukkitTask> task) {
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
            if (asynchronous) Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), task);

            else Utils.getPlugin().getServer().getScheduler().runTask(Utils.getPlugin(), task);
        }

        if (type == TaskType.LATER) {
            if (asynchronous) Utils.getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(Utils.getPlugin(), task, delay);

            else Utils.getPlugin().getServer().getScheduler().runTaskLater(Utils.getPlugin(), task, delay);
        }

        if (type == TaskType.REPEATING) {
            if (asynchronous) Utils.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(Utils.getPlugin(), task, delay, period);

            else Utils.getPlugin().getServer().getScheduler().runTaskTimer(Utils.getPlugin(), task, delay, period);
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
    public long getDelay() {
        return delay;
    }

    /**
     * Returns the period (in ticks) between task executions for repeating tasks.
     *
     * @return The period (in ticks).
     */
    public long getPeriod() {
        return period;
    }

    /**
     * Returns the task to be executed.
     *
     * @return The task to be executed.
     */
    public Consumer<BukkitTask> getTask() {
        return task;
    }

    /**
     * Returns the type of the task.
     *
     * @return The type of the task.
     */
    public TaskType getType() {
        return type;
    }
}
