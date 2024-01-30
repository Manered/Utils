package dev.manere.utils.command.impl;

import dev.manere.utils.command.CommandTypes;
import dev.manere.utils.command.args.Argument;
import dev.manere.utils.command.impl.alias.CommandAliasBuilder;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.command.impl.dispatcher.CommandDispatcher;
import dev.manere.utils.command.impl.dispatcher.SuggestionDispatcher;
import dev.manere.utils.command.impl.permission.CommandPermission;
import dev.manere.utils.command.impl.permission.CommandPermissionBuilder;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import dev.manere.utils.library.Utils;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Allows you to easily create commands non-reliant on Brigadier,
 * made very simple and easy to use,
 * allows for a variety of options
 * and a lot of customisable features.
 * <P></P>
 * <strong>WARNING:</strong><P>
 * When using {@code new Commands(Command command)} or {@link Commands#legacy(Command)} you will lose
 * the Argument<?> arguments. Don't use that unless you need to.</P>
 * <P></P>
 * <strong>REQUIREMENTS:</strong>
 * <P>When adding a requirement using {@link Commands#requirement(Predicate)} you should return:</P>
 * - <strong>{@code true}</strong> if you want to stop execution of the command,
 * <P>
 * - <strong>{@code false}</strong> if you want to continue execution of the command.
 * <P></P>
 * <strong>COMMAND TYPES:</strong>
 * <P>You will find documentation for Command Types in {@link CommandTypes}</P>
 *
 * @see CommandDispatcher
 * @see SuggestionDispatcher
 * @see CommandTypes
 * @see Argument
 * @see CommandContext
 */
public class Commands {
    private final String name;
    private final CommandTypes type;
    private CommandDispatcher dispatcher;
    private SuggestionDispatcher suggestionDispatcher;
    private final Command command;
    private final List<Predicate<CommandContext>> requirements;
    private final List<Argument<?>> args;
    private String namespace;

    /**
     * Constructs a new Commands with the specified name and type.
     *
     * @param name The name of the command.
     * @param type The type of the command.
     */
    public Commands(@NotNull String name, @NotNull CommandTypes type) {
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
     * Constructs a new Commands with the specified name and the default type.
     *
     * @param name The name of the command.
     */
    public Commands(@NotNull String name) {
        this(name, CommandTypes.commandMap());
    }

    /**
     * Constructs a new Commands with the specified Bukkit command.
     *
     * @param command The Bukkit command.
     */
    private Commands(Command command) {
        CommandTypes commandType = Utils.plugin().getCommand(command.getName()) != null
                ? CommandTypes.PLUGIN_YML
                : CommandTypes.COMMAND_MAP;

        this.name = command.getName();
        this.type = commandType;
        this.requirements = new ArrayList<>();
        this.args = new ArrayList<>();
        this.command = command;

        this.dispatcher = ctx -> CommandResultWrapper.wrap(this.command.execute(
                ctx.sender(),
                ctx.commandRan(),
                ctx.rawArgs().toArray(new String[0])
        ));

        this.suggestionDispatcher = ctx -> Suggestions.wrap(this.command.tabComplete(
                ctx.sender(),
                ctx.commandRan(),
                ctx.rawArgs().toArray(new String[0])
        ));
    }

    /**
     * Constructs a new Commands with the specified Bukkit command.
     * If you're using this, you're doing something wrong.
     *
     * @param command The Bukkit command.
     */
    @ApiStatus.Internal
    public static @NotNull Commands legacy(Command command) {
        return new Commands(command);
    }

    /**
     * Create a new Commands with the specified name and type.
     *
     * @param name The name of the command.
     * @param type The type of the command.
     * @return A new Commands instance.
     */
    public static @NotNull Commands command(@NotNull String name, @NotNull CommandTypes type) {
        return new Commands(name, type);
    }

    /**
     * Create a new Commands with the specified name and default (PLUGIN_YML) type.
     *
     * @param name The name of the command.
     * @return A new Commands instance.
     */
    public static @NotNull Commands command(@NotNull String name) {
        return new Commands(name);
    }

    /**
     * Edits the information associated with this command.
     * @param infoConsumer The consumer in which the information is modified in.
     * @return This Commands instance for method chaining.
     */
    public Commands info(Consumer<CommandInfo> infoConsumer) {
        CommandInfo info = new CommandInfo(this);
        infoConsumer.accept(info);

        if (info.description() != null) description(info.description());
        if (info.namespace() != null) namespace(info.namespace());
        if (info.aliases() != null) aliases(info.aliases());
        if (info.permission() != null) permission(info.permission());
        if (info.permissionMessage() != null) permissionMessage(info.permissionMessage());
        if (info.usage() != null) usage(info.usage());

        return this;
    }

    /**
     * Returns the command registration namespace.
     *
     * @return The command registration namespace.
     */
    public @NotNull String namespace() {
        return namespace == null ? Utils.plugin().getName() : namespace;
    }

    /**
     * Sets the command registration namespace.
     *
     * @param namespace The new the command registration namespace.
     * @return This Commands instance for method chaining.
     */
    public Commands namespace(@NotNull String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * Adds a predicate/filter to the command.
     * Return true if you want to stop the execution
     * of the command.
     *
     * @param predicate What to run.
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands requirement(@NotNull Predicate<CommandContext> predicate) {
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
     * Returns the underlying Bukkit Command associated with this command.
     *
     * @return The Bukkit Command object.
     */
    public @NotNull Command bukkitCommand() {
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
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands description(@NotNull String description) {
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
     * Sets the required permission for the command.
     *
     * @param permission The permission to require for the command.
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands permission(@NotNull String permission) {
        return permission()
                .type(CommandPermission.CUSTOM)
                .custom(permission)
                .build();
    }

    /**
     * Sets the required permission for the command.
     *
     * @param prefix The permission prefix to require for the command.
     *               If your permission is `<strong>kits</strong> . kit` then the prefix
     *               is {@code kits}
     * @param suffix The permission suffix to require for the command.
     *               If your permission is `kits . <strong>kit</strong>` then the suffix
     *               is {@code kit}
     *
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands permission(@NotNull String prefix, @NotNull String suffix) {
        return permission()
                .type(CommandPermission.CUSTOM)
                .custom(prefix, suffix)
                .build();
    }

    /**
     * Sets the 'insufficient permissions' message for the command.
     *
     * @param permissionMessage The 'insufficient permissions' message for the command.
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands permissionMessage(@NotNull Component permissionMessage) {
        bukkitCommand().permissionMessage(permissionMessage);
        return this;
    }

    /**
     * Sets the usage description for the command.
     *
     * @param usage The usage description for the command.
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands usage(@NotNull String usage) {
        this.command.setUsage(TextStyle.legacy(usage));
        return this;
    }

    /**
     * Returns the usage message associated with this command.
     *
     * @return The usage message associated with this command.
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
     * Sets the aliases for the command.
     *
     * @param aliases The aliases for the command.
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands aliases(@NotNull String... aliases) {
        return aliases(Arrays.asList(aliases));
    }

    /**
     * Sets the aliases for the command.
     *
     * @param aliases The aliases for the command.
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands aliases(@NotNull List<String> aliases) {
        return aliases().aliases(aliases).build();
    }

    /**
     * Sets the CommandDispatcher for executing the command.
     *
     * @param dispatcher The CommandDispatcher for executing the command.
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands executes(@NotNull CommandDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        return this;
    }

    /**
     * Returns the CommandDispatcher associated with this command.
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
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands completes(@Nullable SuggestionDispatcher suggestionDispatcher) {
        if (suggestionDispatcher == null) {
            this.suggestionDispatcher = ctx -> Suggestions.empty();
            return this;
        }

        this.suggestionDispatcher = suggestionDispatcher;
        return this;
    }

    /**
     * Returns the SuggestionDispatcher associated with this command.
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
    public @NotNull CommandTypes type() {
        return type;
    }

    /**
     * Adds an argument to the command.
     * @param argument The argument to add
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands argument(@NotNull Argument<?> argument) {
        this.args.add(argument);
        return this;
    }

    /**
     * Adds an argument to the command.
     * @param argument The argument to add
     * @return This Commands instance for method chaining.
     */
    public @NotNull Commands arg(@NotNull Argument<?> argument) {
        return argument(argument);
    }

    /**
     * Returns the command custom arguments.
     * @return The command custom arguments.
     */
    @NotNull
    public List<Argument<?>> args() {
        return args;
    }

    /**
     * Builds and returns a CommandsRegistrar for handling the command.
     *
     * @return A CommandsRegistrar for handling the command.
     */
    public @NotNull CommandsRegistrar build() {
        return new CommandsRegistrar(this);
    }

    /**
     * Attempts to register the command
     * with the plugin obtained by {@link Utils#plugin()}
     */
    public void register() {
        build().register();
    }
}
