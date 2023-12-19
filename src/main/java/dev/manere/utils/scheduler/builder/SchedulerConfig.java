package dev.manere.utils.scheduler.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * SchedulerConfig represents the configuration options for scheduling tasks.
 * It includes parameters such as everyTicks, afterTicks, and threadType.
 */
public class SchedulerConfig {
    private @Nullable Integer everyTicks;
    private @Nullable Integer afterTicks;
    private @NotNull SchedulerThreadType threadType;

    /**
     * Constructs a new SchedulerConfig with default values.
     */
    public SchedulerConfig() {
        this.everyTicks = null;
        this.afterTicks = null;
        this.threadType = SchedulerThreadType.SYNC;
    }

    /**
     * Gets the value of everyTicks.
     *
     * @return The value of everyTicks.
     */
    public @Nullable Integer everyTicks() {
        return everyTicks;
    }

    /**
     * Sets the value of everyTicks.
     *
     * @param everyTicks The value to set for everyTicks.
     * @return This SchedulerConfig instance for method chaining.
     */
    public @NotNull SchedulerConfig everyTicks(@Nullable Integer everyTicks) {
        this.everyTicks = everyTicks;
        return this;
    }

    /**
     * Gets the value of afterTicks.
     *
     * @return The value of afterTicks.
     */
    public @Nullable Integer afterTicks() {
        return afterTicks;
    }

    /**
     * Sets the value of afterTicks.
     *
     * @param afterTicks The value to set for afterTicks.
     * @return This SchedulerConfig instance for method chaining.
     */
    public @NotNull SchedulerConfig afterTicks(@Nullable Integer afterTicks) {
        this.afterTicks = afterTicks;
        return this;
    }

    /**
     * Sets the thread type to asynchronous.
     *
     * @return This SchedulerConfig instance for method chaining.
     */
    public @NotNull SchedulerConfig async() {
        this.threadType = SchedulerThreadType.ASYNC;
        return this;
    }

    /**
     * Sets the thread type to synchronous.
     *
     * @return This SchedulerConfig instance for method chaining.
     */
    public @NotNull SchedulerConfig sync() {
        this.threadType = SchedulerThreadType.SYNC;
        return this;
    }

    /**
     * Gets the thread type.
     *
     * @return The thread type.
     */
    public @NotNull SchedulerThreadType threadType() {
        return threadType;
    }

    /**
     * Sets the thread type.
     *
     * @param threadType The thread type to set.
     * @return This SchedulerConfig instance for method chaining.
     */
    public @NotNull SchedulerConfig threadType(@NotNull SchedulerThreadType threadType) {
        this.threadType = threadType;
        return this;
    }
}
