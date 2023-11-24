package dev.manere.utils.command.builder.alias;

import dev.manere.utils.command.builder.CommandBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for managing aliases for a command associated with a CommandBuilder.
 */
public class CommandAliasBuilder {
    private final CommandBuilder commandBuilder;
    private final List<String> aliases;

    /**
     * Constructs a new CommandAliasBuilder associated with the specified CommandBuilder.
     *
     * @param commandBuilder The CommandBuilder associated with this CommandAliasBuilder.
     */
    public CommandAliasBuilder(@NotNull CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
        this.aliases = commandBuilder.command().getAliases().isEmpty() ? new ArrayList<>() : commandBuilder.command().getAliases();
    }

    /**
     * Adds an alias to the list of aliases for the associated command.
     *
     * @param alias The alias to be added.
     * @return This CommandAliasBuilder instance for method chaining.
     */
    public @NotNull CommandAliasBuilder add(@NotNull String alias) {
        this.aliases.add(alias);
        return this;
    }

    /**
     * Returns the list of aliases associated with the command.
     *
     * @return A list of aliases for the command.
     */
    public @NotNull List<String> aliases() {
        return aliases;
    }


    public @NotNull CommandAliasBuilder aliases(@NotNull List<String> aliases) {
        this.aliases.clear();

        for (String alias : aliases) {
            this.aliases().add(alias);
        }

        return this;
    }

    /**
     * Gets the associated CommandBuilder.
     *
     * @return The CommandBuilder associated with this CommandAliasBuilder.
     */
    public @NotNull CommandBuilder commandBuilder() {
        return commandBuilder;
    }

    /**
     * Builds and associates the list of aliases with the CommandBuilder.
     *
     * @return The CommandBuilder with the associated aliases.
     */
    public @NotNull CommandBuilder build() {
        if (!aliases.isEmpty()) {
            commandBuilder().command().setAliases(aliases);
        }

        return commandBuilder;
    }
}
