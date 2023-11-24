package dev.manere.utils.command.builder;

import dev.manere.utils.command.CommandType;
import dev.manere.utils.command.args.Argument;
import dev.manere.utils.command.builder.alias.CommandAliasBuilder;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.command.builder.dispatcher.CommandDispatcher;
import dev.manere.utils.command.builder.dispatcher.SuggestionDispatcher;
import dev.manere.utils.command.builder.permission.CommandPermissionBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A utility class for building and configuring Bukkit plugin commands.
 */
public class CommandBuilder {
    private final String name;
    private final CommandType type;
    private CommandDispatcher dispatcher;
    private SuggestionDispatcher suggestionDispatcher;
    private final Command command;
    private final List<Predicate<CommandContext>> requirements;
    private final List<Argument<?>> args;

    /**
     * Constructs a new CommandBuilder with the specified name and type.
     *
     * @param name The name of the command.
     * @param type The type of the command.
     */
    public CommandBuilder(@NotNull String name, @NotNull CommandType type) {
        this.name = name;
        this.type = type;
        this.requirements = new ArrayList<>();
        this.args = new ArrayList<>();
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
    public static @NotNull CommandBuilder command(@NotNull String name, @NotNull CommandType type) {
        return new CommandBuilder(name, type);
    }

    /**
     * Create a new CommandBuilder with the specified name and default (PLUGIN_YML) type.
     *
     * @param name The name of the command.
     * @return A new CommandBuilder instance.
     */
    public static @NotNull CommandBuilder command(@NotNull String name) {
        return CommandBuilder.command(name, CommandType.PLUGIN_YML);
    }

    /**
     * Adds a predicate/filter to the command.
     * Return true if you want to stop the execution
     * of the command.
     *
     * @param predicate What to run.
     * @return This CommandBuilder instance for method chaining.
     */
    public @NotNull CommandBuilder requirement(@NotNull Predicate<CommandContext> predicate) {
        requirements().add(predicate);
        return this;
    }

    /**
     * Returns the list of filters.
     *
     * @return The list of filters.
     */
    public @NotNull List<Predicate<CommandContext>> requirements() {
        return this.requirements;
    }

    /**
     * Returns the underlying Bukkit Command associated with this CommandBuilder.
     *
     * @return The Bukkit Command object.
     */
    public @NotNull Command command() {
        return command;
    }

    /**
     * Gets the description of the command.
     *
     * @return The description of the command.
     */
    public @NotNull String description() {
        return command.getDescription();
    }

    /**
     * Sets the description of the command.
     *
     * @param description The new description for the command.
     * @return This CommandBuilder instance for method chaining.
     */
    public @NotNull CommandBuilder description(@NotNull String description) {
        this.command.setDescription(description);
        return this;
    }

    /**
     * Returns a CommandPermissionBuilder instance for setting custom permissions for the command.
     *
     * @return A CommandPermissionBuilder instance.
     */
    public @NotNull CommandPermissionBuilder permission() {
        return new CommandPermissionBuilder(this);
    }

    /**
     * Sets the usage description for the command.
     *
     * @param usage The usage description for the command.
     * @return This CommandBuilder instance for method chaining.
     */
    public @NotNull CommandBuilder usage(@NotNull String usage) {
        this.command.setUsage(TextStyle.legacy(usage));
        return this;
    }

    /**
     * Returns the usage message associated with this CommandBuilder.
     *
     * @return The usage message associated with this CommandBuilder.
     */
    public @NotNull String usage() {
        return this.command.getUsage();
    }

    /**
     * Returns a CommandAliasBuilder instance for managing command aliases.
     *
     * @return A CommandAliasBuilder instance.
     */
    public @NotNull CommandAliasBuilder aliases() {
        return new CommandAliasBuilder(this);
    }

    /**
     * Sets the CommandDispatcher for executing the command.
     *
     * @param dispatcher The CommandDispatcher for executing the command.
     * @return This CommandBuilder instance for method chaining.
     */
    public @NotNull CommandBuilder executes(@NotNull CommandDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        return this;
    }

    /**
     * Returns the CommandDispatcher associated with this CommandBuilder.
     *
     * @return The CommandDispatcher for executing the command.
     */
    public @Nullable CommandDispatcher executes() {
        return dispatcher;
    }

    /**
     * Sets the SuggestionDispatcher for handling command suggestions.
     *
     * @param suggestionDispatcher The SuggestionDispatcher for handling command suggestions.
     * @return This CommandBuilder instance for method chaining.
     */
    public @NotNull CommandBuilder completes(@Nullable SuggestionDispatcher suggestionDispatcher) {
        this.suggestionDispatcher = suggestionDispatcher;
        return this;
    }

    /**
     * Returns the SuggestionDispatcher associated with this CommandBuilder.
     *
     * @return The SuggestionDispatcher for handling command suggestions.
     */
    public @Nullable SuggestionDispatcher completes() {
        return suggestionDispatcher;
    }

    /**
     * Gets the name of the command.
     *
     * @return The name of the command.
     */
    public @NotNull String name() {
        return name;
    }

    /**
     * Gets the type of the command.
     *
     * @return The type of the command
     */
    public @NotNull CommandType type() {
        return type;
    }


    public @NotNull CommandBuilder argument(@NotNull Argument<?> arg) {
        this.args.add(arg);
        return this;
    }

    public @NotNull List<Argument<?>> args() {
        return args;
    }

    /**
     * Builds and returns a CommandBuilderHandler for handling the command.
     *
     * @return A CommandBuilderHandler for handling the command.
     */
    public @NotNull CommandBuilderHandler build() {
        return new CommandBuilderHandler(this);
    }
}
