package dev.manere.utils.command.builder.alias;

import dev.manere.utils.command.builder.CommandBuilder;

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
    public CommandAliasBuilder(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
        this.aliases = new ArrayList<>();
    }

    /**
     * Adds an alias to the list of aliases for the associated command.
     *
     * @param alias The alias to be added.
     * @return This CommandAliasBuilder instance for method chaining.
     */
    public CommandAliasBuilder add(String alias) {
        this.aliases.add(alias);
        return this;
    }

    /**
     * Returns the list of aliases associated with the command.
     *
     * @return A list of aliases for the command.
     */
    public List<String> aliases() {
        return aliases;
    }

    /**
     * Builds and associates the list of aliases with the CommandBuilder.
     *
     * @return The CommandBuilder with the associated aliases.
     */
    public CommandBuilder build() {
        this.commandBuilder.aliases(this);
        return commandBuilder;
    }
}
