package dev.manere.utils.registration;

import dev.manere.utils.command.CommandType;
import dev.manere.utils.command.Commander;
import dev.manere.utils.command.builder.CommandBuilder;
import dev.manere.utils.command.builder.alias.CommandAliasBuilder;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.command.builder.permission.CommandPermission;
import dev.manere.utils.library.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

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
    public static void events(JavaPlugin plugin, Listener listener) {
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
    public static void command(JavaPlugin plugin, String commandName, CommandExecutor executor) {
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
    public static CommandMap commandMap(JavaPlugin plugin) {
        try {
            Field bukkitCommandMap = plugin.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            return (CommandMap) bukkitCommandMap.get(plugin.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Registers a custom command with the CommandMap associated with the provided plugin.
     *
     * @param plugin      The JavaPlugin instance.
     * @param commandName The name of the command.
     * @param command     The Command object to register.
     */
    public static void commandMap(JavaPlugin plugin, String commandName, Command command) {
        Objects.requireNonNull(commandMap(plugin)).register(plugin.getName(), command);
    }

    /**
     * Registers a listener with the plugin obtained from Utils.
     *
     * @param listener The listener to register.
     */
    public static void events(Listener listener) {
        events(Utils.plugin(), listener);
    }

    /**
     * Registers a command executor with the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param executor The CommandExecutor to associate with the command.
     * @throws NullPointerException if executor is null.
     */
    public static void command(String commandName, CommandExecutor executor) {
        command(Utils.plugin(), commandName, executor);
    }

    /**
     * Retrieves the CommandMap associated with the plugin obtained from Utils.
     *
     * @return The CommandMap object, or null if unable to retrieve.
     */
    public static CommandMap commandMap() {
        return commandMap(Utils.plugin());
    }

    /**
     * Registers a custom command with the CommandMap of the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param command The Command object to register.
     */
    public static void commandMap(String commandName, Command command) {
        commandMap(Utils.plugin(), commandName, command);
    }

    /**
     * Registers a custom command that extends {@link Commander}.
     *
     * @apiNote This should only be used for Commanders that use the CommandType of PLUGIN_YML.
     * @param commander The commander instance to register.
     * @see Commander
     * @see CommandType#PLUGIN_YML
     */
    @SuppressWarnings("DataFlowIssue")
    public static void command(Commander commander) {
        if (commander.settings().type() == CommandType.COMMAND_MAP) {
            throw new UnsupportedOperationException("You can only use dev.manere.utils.command.CommandType.PLUGIN_YML" +
                    " in this method." +
                    " Please use dev.manere.utils.registration.Registrar#command(" +
                    "Commander commander, List<String> aliases, String description, String permission, String usage) instead.");
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
     * @see CommandType#COMMAND_MAP
     * @apiNote This should only be used for Commanders that use the CommandType of COMMAND_MAP.
     */
    public static void command(Commander commander, @Nullable List<String> aliases, @Nullable String description, @Nullable String permission, @Nullable String usage) {
        CommandBuilder builder = CommandBuilder.command(commander.name(), commander.settings().type());

        if (aliases != null && !aliases.isEmpty()) {
            CommandAliasBuilder builderAliases = builder.aliases();

            for (String alias : aliases) {
                builderAliases.add(alias);
            }

            builder.aliases(builderAliases);
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
}
