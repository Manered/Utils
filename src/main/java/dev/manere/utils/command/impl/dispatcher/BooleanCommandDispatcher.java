package dev.manere.utils.command.impl.dispatcher;

import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.CommandResultWrapper;
import org.jetbrains.annotations.NotNull;

public interface BooleanCommandDispatcher extends CommandDispatcher {
    /**
     * Executes a command based on the provided command context.
     *
     * @param context The context of the command to be executed.
     * @return the result of the execution.
     */
    boolean runBoolean(@NotNull CommandContext context);

    @Override
    default @NotNull CommandResult run(@NotNull CommandContext context) {
        return CommandResultWrapper.wrap(runBoolean(context));
    }
}
