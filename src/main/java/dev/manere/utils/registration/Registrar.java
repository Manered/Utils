package dev.manere.utils.registration;

import dev.manere.utils.command.CommandBuilder;
import dev.manere.utils.library.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
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
    public static void listener(JavaPlugin plugin, Listener listener) {
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
    public static CommandMap getCommandMap(JavaPlugin plugin) {
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
        Objects.requireNonNull(getCommandMap(plugin)).register(commandName, command);
    }

    /**
     * Registers a custom command using a CommandBuilder with the CommandMap associated with the provided plugin.
     *
     * @param plugin  The JavaPlugin instance.
     * @param command The CommandBuilder containing the command details.
     */
    public static void commandMap(JavaPlugin plugin, CommandBuilder command) {
        Objects.requireNonNull(getCommandMap(plugin)).register(command.getName(), command.getCommand());
    }

    /**
     * Registers a listener with the plugin obtained from Utils.
     *
     * @param listener The listener to register.
     */
    public static void listener(Listener listener) {
        listener(Utils.getPlugin(), listener);
    }

    /**
     * Registers a command executor with the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param executor The CommandExecutor to associate with the command.
     * @throws NullPointerException if executor is null.
     */
    public static void command(String commandName, CommandExecutor executor) {
        command(Utils.getPlugin(), commandName, executor);
    }

    /**
     * Retrieves the CommandMap associated with the plugin obtained from Utils.
     *
     * @return The CommandMap object, or null if unable to retrieve.
     */
    public static CommandMap getCommandMap() {
        return getCommandMap(Utils.getPlugin());
    }

    /**
     * Registers a custom command with the CommandMap of the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param command The Command object to register.
     */
    public static void commandMap(String commandName, Command command) {
        commandMap(Utils.getPlugin(), commandName, command);
    }

    /**
     * Registers a custom command built with a CommandBuilder with the CommandMap
     * of the plugin obtained from Utils.
     *
     * @param command The CommandBuilder containing the command details.
     */
    public static void commandMap(CommandBuilder command) {
        commandMap(Utils.getPlugin(), command);
    }
}
