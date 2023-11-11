package dev.manere.utils.command.builder;

import dev.manere.utils.command.CommandType;
import dev.manere.utils.command.builder.alias.CommandAliasBuilder;
import dev.manere.utils.command.builder.dispatcher.CommandDispatcher;
import dev.manere.utils.command.builder.dispatcher.SuggestionDispatcher;
import dev.manere.utils.command.builder.permission.CommandPermissionBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class for building and configuring Bukkit plugin commands.
 */
public class CommandBuilder {
    private final String name;
    private final CommandType type;
    private CommandDispatcher dispatcher;
    private SuggestionDispatcher suggestionDispatcher;
    private final Command command;

    /**
     * Constructs a new CommandBuilder with the specified name and type.
     *
     * @param name The name of the command.
     * @param type The type of the command.
     */
    public CommandBuilder(String name, CommandType type) {
        this.name = name;
        this.type = type;
        this.command = new Command(name) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                return true;
            }
        };
    }

    /**
     * Create a new CommandBuilder with the specified name and type.
     *
     * @param name The name of the command.
     * @param type The type of the command.
     * @return A new CommandBuilder instance.
     */
    public static CommandBuilder command(String name, CommandType type) {
        return new CommandBuilder(name, type);
    }

    /**
     * Returns the underlying Bukkit Command associated with this CommandBuilder.
     *
     * @return The Bukkit Command object.
     */
    public Command command() {
        return command;
    }

    /**
     * Gets the description of the command.
     *
     * @return The description of the command.
     */
    public String description() {
        return command.getDescription();
    }

    /**
     * Sets the description of the command.
     *
     * @param description The new description for the command.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder description(String description) {
        this.command.setDescription(description);
        return this;
    }

    /**
     * Sets the description of the command.
     *
     * @param info The new description for the command.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder info(String info) {
        return description(info);
    }

    /**
     * Returns a CommandPermissionBuilder instance for setting custom permissions for the command.
     *
     * @return A CommandPermissionBuilder instance.
     */
    public CommandPermissionBuilder permission() {
        return new CommandPermissionBuilder(this);
    }

    /**
     * Sets the custom permission for the command using a CommandPermissionBuilder.
     *
     * @param builder The CommandPermissionBuilder containing custom permission information.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder permission(CommandPermissionBuilder builder) {
        this.command.setPermission(builder.custom());
        this.command.permissionMessage(builder.message());
        return this;
    }

    /**
     * Sets the usage description for the command.
     *
     * @param description The usage description for the command.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder usage(String description) {
        this.command.setUsage(TextStyle.legacy(description));

        return this;
    }

    /**
     * Returns a CommandAliasBuilder instance for managing command aliases.
     *
     * @return A CommandAliasBuilder instance.
     */
    public CommandAliasBuilder aliases() {
        return new CommandAliasBuilder(this);
    }

    /**
     * Sets the aliases for the command using a CommandAliasBuilder.
     *
     * @param builder The CommandAliasBuilder containing aliases.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder aliases(CommandAliasBuilder builder) {
        this.command.setAliases(builder.aliases());
        return this;
    }

    /**
     * Sets the usage description for the command using a Component object.
     *
     * @param description The usage description as a Component object.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder usage(Component description) {
        this.command.setUsage(TextStyle.legacy(TextStyle.text(description)));
        return this;
    }

    /**
     * Sets the CommandDispatcher for executing the command.
     *
     * @param dispatcher The CommandDispatcher for executing the command.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder executes(CommandDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        return this;
    }

    /**
     * Returns the CommandDispatcher associated with this CommandBuilder.
     *
     * @return The CommandDispatcher for executing the command.
     */
    public CommandDispatcher executes() {
        return dispatcher;
    }

    /**
     * Sets the SuggestionDispatcher for handling command suggestions.
     *
     * @param suggestionDispatcher The SuggestionDispatcher for handling command suggestions.
     * @return This CommandBuilder instance for method chaining.
     */
    public CommandBuilder completes(SuggestionDispatcher suggestionDispatcher) {
        this.suggestionDispatcher = suggestionDispatcher;
        return this;
    }

    /**
     * Returns the SuggestionDispatcher associated with this CommandBuilder.
     *
     * @return The SuggestionDispatcher for handling command suggestions.
     */
    public SuggestionDispatcher completes() {
        return suggestionDispatcher;
    }

    /**
     * Gets the name of the command.
     *
     * @return The name of the command.
     */
    public String name() {
        return name;
    }

    /**
     * Gets the type of the command.
     *
     * @return The type of the command
     */
    public CommandType type() {
        return type;
    }

    /**
     * Builds and returns a CommandBuilderHandler for handling the command.
     *
     * @return A CommandBuilderHandler for handling the command.
     */
    public CommandBuilderHandler build() {
        return new CommandBuilderHandler(this);
    }
}
