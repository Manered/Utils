package dev.manere.utils.scheduler.builder;

import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.scheduler.builder.task.SchedulerTask;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * SchedulerBuilder is a class for building and executing scheduled tasks
 * with customizable configurations using SchedulerConfig and SchedulerTask.
 * It provides methods for configuring the task and execution details.
 */
public class SchedulerBuilder {
    private SchedulerTask schedulerTask;
    private final @NotNull SchedulerConfig config;

    /**
     * Constructs a new SchedulerBuilder with default values.
     */
    public SchedulerBuilder() {
        this.config = new SchedulerConfig();
        this.schedulerTask = null;
    }

    /**
     * Constructs a new SchedulerBuilder with a provided runnable.
     *
     * @param runnable The runnable task to be scheduled.
     */
    public SchedulerBuilder(@NotNull Runnable runnable) {
        this.config = new SchedulerConfig();
        this.schedulerTask = SchedulerTask.wrap(runnable);
    }

    /**
     * Constructs a new SchedulerBuilder with a provided task consumer.
     *
     * @param task The task consumer to be scheduled.
     */
    public SchedulerBuilder(@NotNull Consumer<BukkitTask> task) {
        this.config = new SchedulerConfig();
        this.schedulerTask = SchedulerTask.wrap(task);
    }

    /**
     * Configures the SchedulerConfig using a consumer.
     *
     * @param configConsumer The consumer for configuring SchedulerConfig.
     * @return This SchedulerBuilder instance for method chaining.
     */
    public @NotNull SchedulerBuilder config(@NotNull Consumer<SchedulerConfig> configConsumer) {
        configConsumer.accept(this.config);
        return this;
    }

    /**
     * Sets the thread type to asynchronous.
     *
     * @return This SchedulerBuilder instance for method chaining.
     */
    public @NotNull SchedulerBuilder async() {
        this.config.async();
        return this;
    }

    /**
     * Sets the thread type to synchronous.
     *
     * @return This SchedulerBuilder instance for method chaining.
     */
    public @NotNull SchedulerBuilder sync() {
        this.config.sync();
        return this;
    }

    /**
     * Sets the interval between repeated executions in ticks.
     *
     * @param everyTicks The number of ticks between each repeated execution.
     * @return This SchedulerBuilder instance for method chaining.
     */
    public @NotNull SchedulerBuilder everyTicks(int everyTicks) {
        this.config.everyTicks(everyTicks);
        return this;
    }

    /**
     * Sets the delay before the first execution in ticks.
     *
     * @param afterTicks The number of ticks to wait before the first execution.
     * @return This SchedulerBuilder instance for method chaining.
     */
    public @NotNull SchedulerBuilder afterTicks(int afterTicks) {
        this.config.afterTicks(afterTicks);
        return this;
    }

    /**
     * Sets the runnable task.
     *
     * @param runnable The runnable task to be scheduled.
     * @return This SchedulerBuilder instance for method chaining.
     */
    public @NotNull SchedulerBuilder runnable(@NotNull Runnable runnable) {
        this.schedulerTask = SchedulerTask.wrap(runnable);
        return this;
    }

    /**
     * Sets the task consumer.
     *
     * @param task The task consumer to be scheduled.
     * @return This SchedulerBuilder instance for method chaining.
     */
    public @NotNull SchedulerBuilder task(@NotNull Consumer<BukkitTask> task) {
        this.schedulerTask = SchedulerTask.wrap(task);
        return this;
    }

    /**
     * Executes the scheduled task based on the configured options.
     */
    @SuppressWarnings("unchecked")
    public void execute() {
        switch (config.threadType()) {
            case SYNC -> {
                // Immediate, SYNC
                if (config.everyTicks() == null && config.afterTicks() == null) {
                    if (schedulerTask.appropriate() instanceof Consumer<?> consumer) {
                        Consumer<BukkitTask> taskConsumer = (Consumer<BukkitTask>) consumer;
                        Schedulers.sync().execute(taskConsumer);
                    } else if (schedulerTask.appropriate() instanceof Runnable runnable) {
                        Schedulers.sync().execute(runnable);
                    }

                    break;
                }

                // Delayed, SYNC
                if (config.everyTicks() == null && config.afterTicks() != null) {
                    if (schedulerTask.appropriate() instanceof Consumer<?> consumer) {
                        Consumer<BukkitTask> taskConsumer = (Consumer<BukkitTask>) consumer;
                        Schedulers.sync().execute(taskConsumer, config.afterTicks());
                    } else if (schedulerTask.appropriate() instanceof Runnable runnable) {
                        Schedulers.sync().execute(runnable, config.afterTicks());
                    }

                    break;
                }

                // Timer, SYNC
                if (config.everyTicks() != null && config.afterTicks() != null) {
                    if (schedulerTask.appropriate() instanceof Consumer<?> consumer) {
                        Consumer<BukkitTask> taskConsumer = (Consumer<BukkitTask>) consumer;
                        Schedulers.sync().execute(taskConsumer, config.afterTicks(), config.everyTicks());
                    } else if (schedulerTask.appropriate() instanceof Runnable runnable) {
                        Schedulers.sync().execute(runnable, config.afterTicks(), config.everyTicks());
                    }

                    break;
                }

                throw new UnsupportedOperationException("Not supported");
            }
            case ASYNC -> {
                // Immediate, SYNC
                if (config.everyTicks() == null && config.afterTicks() == null) {
                    if (schedulerTask.appropriate() instanceof Consumer<?> consumer) {
                        Consumer<BukkitTask> taskConsumer = (Consumer<BukkitTask>) consumer;
                        Schedulers.async().execute(taskConsumer);
                    } else if (schedulerTask.appropriate() instanceof Runnable runnable) {
                        Schedulers.async().execute(runnable);
                    }

                    break;
                }

                // Delayed, SYNC
                if (config.everyTicks() == null && config.afterTicks() != null) {
                    if (schedulerTask.appropriate() instanceof Consumer<?> consumer) {
                        Consumer<BukkitTask> taskConsumer = (Consumer<BukkitTask>) consumer;
                        Schedulers.async().execute(taskConsumer, config.afterTicks());
                    } else if (schedulerTask.appropriate() instanceof Runnable runnable) {
                        Schedulers.async().execute(runnable, config.afterTicks());
                    }

                    break;
                }

                // Timer, SYNC
                if (config.everyTicks() != null && config.afterTicks() != null) {
                    if (schedulerTask.appropriate() instanceof Consumer<?> consumer) {
                        Consumer<BukkitTask> taskConsumer = (Consumer<BukkitTask>) consumer;
                        Schedulers.async().execute(taskConsumer, config.afterTicks(), config.everyTicks());
                    } else if (schedulerTask.appropriate() instanceof Runnable runnable) {
                        Schedulers.async().execute(runnable, config.afterTicks(), config.everyTicks());
                    }

                    break;
                }

                throw new UnsupportedOperationException("Not supported");
            }
        }
    }
}
