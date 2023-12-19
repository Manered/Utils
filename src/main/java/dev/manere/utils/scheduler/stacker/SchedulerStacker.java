package dev.manere.utils.scheduler.stacker;

import dev.manere.utils.scheduler.builder.SchedulerBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SchedulerStacker is a utility class that allows the stacking of multiple {@link SchedulerBuilder} instances
 * for efficient execution of scheduled tasks. It provides methods to stack individual builders, a list of builders,
 * or an array of builders. The stacked builders can then be executed in sequence using the {@code execute} method.
 */
public class SchedulerStacker {
    private final @NotNull List<SchedulerBuilder> builders;

    /**
     * Constructs a new SchedulerStacker with an empty list of builders.
     */
    public SchedulerStacker() {
        this.builders = new ArrayList<>();
    }

    /**
     * Stacks a single {@link SchedulerBuilder} instance onto the list of builders.
     *
     * @param builder The SchedulerBuilder to be stacked.
     * @return This SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker stack(@NotNull SchedulerBuilder builder) {
        this.builders.add(builder);
        return this;
    }

    /**
     * Stacks a list of {@link SchedulerBuilder} instances onto the list of builders.
     *
     * @param builders The list of SchedulerBuilders to be stacked.
     * @return This SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker stack(@NotNull List<SchedulerBuilder> builders) {
        this.builders.addAll(builders);
        return this;
    }

    /**
     * Stacks an array of {@link SchedulerBuilder} instances onto the list of builders.
     *
     * @param builders The array of SchedulerBuilders to be stacked.
     * @return This SchedulerStacker instance for method chaining.
     */
    public @NotNull SchedulerStacker stack(@NotNull SchedulerBuilder... builders) {
        this.builders.addAll(Arrays.asList(builders));
        return this;
    }

    /**
     * Executes each stacked {@link SchedulerBuilder} in sequence, triggering the execution
     * of their respective scheduled tasks.
     */
    public void execute() {
        for (SchedulerBuilder builder : builders) {
            builder.execute();
        }
    }
}
