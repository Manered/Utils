package dev.manere.utils.command.impl.dispatcher;

import dev.manere.utils.command.CommandResult;
import org.jetbrains.annotations.NotNull;

/**
 * An interface for executing commands based on the provided command context.
 */
public interface CommandDispatcher {
    /**
     * Executes a command based on the provided command context.
     *
     * @param context The context of the command to be executed.
     * @return the result of the execution.
     */
    @NotNull CommandResult run(@NotNull CommandContext context);
}
