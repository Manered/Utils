package dev.manere.utils.command.impl;

import dev.manere.utils.library.Utils;
import dev.manere.utils.registration.Registrar;
import org.jetbrains.annotations.NotNull;

/**
 * Class related to finally registering a Commands.
 */
public final class CommandsRegistrar {
    private final @NotNull Commands commandBuilder;

    /**
     * @param commandBuilder The command builder to handle.
     */
    public CommandsRegistrar(@NotNull Commands commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    /**
     * Attempts to register the command
     * with the plugin obtained by {@link Utils#plugin()}
     */
    public void register() {
        Registrar.commandMap(this);
    }

    /**
     * Returns the command builder associated with this class.
     * @return the command builder associated with this class.
     */
    public @NotNull Commands commandBuilder() {
        return commandBuilder;
    }
}
