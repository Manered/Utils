package dev.manere.utils.scheduler;

/**
 * Enumeration representing different types of scheduler tasks.
 */
public enum TaskType {

    /**
     * Represents a normal, one-time execution task.
     */
    NORMAL,

    /**
     * Represents a task that repeats at a fixed interval.
     */
    REPEATING,

    /**
     * Represents a task that executes after a specified delay.
     */
    LATER
}
