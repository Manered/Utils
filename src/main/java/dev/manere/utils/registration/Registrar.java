package dev.manere.utils.registration;

import dev.manere.utils.command.CommandType;
import dev.manere.utils.command.Commander;
import dev.manere.utils.command.args.Argument;
import dev.manere.utils.command.builder.CommandBuilder;
import dev.manere.utils.command.builder.CommandBuilderHandler;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.command.builder.permission.CommandPermission;
import dev.manere.utils.library.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A utility class for registering various components in a Bukkit plugin.
 */
public class Registrar {

    /**
     * Registers a listener with the provided plugin.
     *
     * @param plugin   The JavaPlugin instance.
     * @param listener The listener to register.
     */
    public static void events(@NotNull JavaPlugin plugin, @NotNull Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * Registers a command executor with the provided plugin.
     *
     * @param plugin      The JavaPlugin instance.
     * @param commandName The name of the command.
     * @param executor    The CommandExecutor to be associated with the command.
     * @throws NullPointerException if plugin or executor is null.
     */
    public static void command(@NotNull JavaPlugin plugin, @NotNull String commandName, @NotNull CommandExecutor executor) {
        Objects.requireNonNull(plugin, "Plugin cannot be null");
        Objects.requireNonNull(executor, "CommandExecutor cannot be null");

        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(executor);
    }

    /**
     * Retrieves the CommandMap associated with the provided plugin.
     *
     * @param plugin The JavaPlugin instance.
     * @return The CommandMap object, or null if it couldn't be retrieved.
     */
    public static @NotNull CommandMap commandMap(@NotNull JavaPlugin plugin) {
        return plugin.getServer().getCommandMap();
    }

    /**
     * Registers a custom command with the CommandMap associated with the provided plugin.
     *
     * @param plugin      The JavaPlugin instance.
     * @param commandName The name of the command.
     * @param command     The Command object to register.
     */
    public static void commandMap(@NotNull JavaPlugin plugin, @NotNull String commandName, @NotNull Command command) {
        Objects.requireNonNull(commandMap(plugin)).register(plugin.getName(), command);
    }

    /**
     * Registers a listener with the plugin obtained from Utils.
     *
     * @param listener The listener to register.
     */
    public static void events(@NotNull Listener listener) {
        events(Utils.plugin(), listener);
    }

    /**
     * Registers a command executor with the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param executor The CommandExecutor to associate with the command.
     * @throws NullPointerException if executor is null.
     */
    public static void command(@NotNull String commandName, @NotNull CommandExecutor executor) {
        command(Utils.plugin(), commandName, executor);
    }

    /**
     * Retrieves the CommandMap associated with the plugin obtained from Utils.
     *
     * @return The CommandMap object, or null if unable to retrieve.
     */
    public static @NotNull CommandMap commandMap() {
        return commandMap(Utils.plugin());
    }

    /**
     * Registers a custom command with the CommandMap of the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param command The Command object to register.
     */
    public static void commandMap(@NotNull String commandName, @NotNull Command command) {
        commandMap(Utils.plugin(), commandName, command);
    }

    /**
     * Registers a custom command that extends {@link Commander}.
     *
     * @param commander The commander instance to register.
     * @see Commander
     */
    @SuppressWarnings("DataFlowIssue")
    public static void command(@NotNull Commander commander) {
        if (commander.settings().type() == CommandType.COMMAND_MAP) {
            Registrar.command(
                    commander,
                    commander.aliases(),
                    commander.description(),
                    commander.permission(),
                    commander.usage()
            );
            return;
        }

        if (commander.settings().type() == CommandType.PLUGIN_YML && Utils.plugin().getCommand(commander.name()) == null) {
            throw new NullPointerException("You seem to have forgotten to provide the command '" + commander.name() + "' " +
                    "in the plugin.yml file inside of your project.");
        }

        Utils.plugin().getCommand(commander.name()).setExecutor((sender, command, label, args) -> commander.settings()
                .executes().run(new CommandContext(sender, command, label, args)));

        if (commander.settings().completes() != null) {
            Utils.plugin().getCommand(commander.name()).setTabCompleter((sender, command, label, args) -> {
                if (commander.settings().completes().suggest(new CommandContext(sender, command, label, args)) == null) {
                    return List.of();
                } else {
                    return commander.settings().completes().suggest(new CommandContext(sender, command, label, args));
                }
            });
        }
    }

    /**
     * Registers a custom command that extends {@link Commander}.
     *
     * @param commander The commander instance to register.
     * @see Commander
     */
    public static void command(@NotNull Commander commander, @Nullable List<String> aliases, @Nullable String description, @Nullable String permission, @Nullable String usage) {
        CommandBuilder builder = CommandBuilder.command(commander.name(), commander.settings().type());

        if (aliases != null && !aliases.isEmpty()) {
            builder.aliases().aliases(aliases).build();
        }

        builder.executes(commander.settings().executes());
        builder.completes(commander.settings().completes());

        if (description != null) {
            builder.description(description);
        }

        if (permission != null) {
            builder.permission()
                    .type(CommandPermission.CUSTOM)
                    .custom(permission)
                    .build();
        }

        if (usage != null) {
            builder.usage(usage);
        }

        builder.build().register();
    }

    @SuppressWarnings("DataFlowIssue")
    public static void commandMap(CommandBuilderHandler handler) {
        CommandBuilder builder = handler.commandBuilder();

        Command command = new Command(builder.name(), builder.description(), builder.usage(), builder.aliases().aliases()) {

            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                CommandContext ctx = CommandContext.context(sender, this, label, args);
                ctx.args().clear();

                for (Argument<?> arg : builder.args()) ctx.args().add(arg);
                for (Predicate<CommandContext> filter : builder.requirements()) if (filter.test(ctx)) return true;

                return builder.executes().run(ctx);
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
                CommandContext ctx = CommandContext.context(sender, this, builder.name(), args);

                for (Argument<?> arg : builder.args()) {
                    if (arg.suggestions() == null || ctx.argPos(arg.identifier()) == -1 || ctx.args().size() != ctx.argPos(arg.identifier())) continue;
                    if (arg.suggestions().suggest(ctx) == null) return List.of();

                    return arg.suggestions().suggest(ctx);
                }

                if (builder.completes().suggest(ctx) == null) return List.of();
                return builder.completes().suggest(ctx);
            }
        };

        command.setName(builder.command().getName());
        command.setAliases(builder.command().getAliases());
        command.setDescription(builder.command().getDescription());

        if (builder.command().getPermission() != null) {
            command.setPermission(builder.command().getPermission());
        }

        command.setUsage(builder.command().getUsage());

        if (builder.command().permissionMessage() != null) {
            command.permissionMessage(builder.command().permissionMessage());
        }

        Registrar.commandMap().register(builder.command().getName(), Utils.plugin().getName(), command);
    }
}
