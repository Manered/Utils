package dev.manere.utils.command.impl.dispatcher;

import org.jetbrains.annotations.NotNull;

/**
 * An interface for executing commands based on the provided command context.
 */
public interface CommandDispatcher {
    /**
     * Executes a command based on the provided command context.
     *
     * @param context The context of the command to be executed.
     * @return true if the command was executed successfully, false otherwise.
     */
    boolean run(@NotNull CommandContext context);
}
