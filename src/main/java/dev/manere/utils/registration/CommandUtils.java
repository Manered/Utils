package dev.manere.utils.registration;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

/**
 * A utility class for registering commands with a Bukkit plugin.
 */
public class CommandUtils {
    /**
     * Registers a command executor for the specified command with the given plugin.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param commandName The name of the command to register.
     * @param executor The command executor responsible for handling the command.
     * @throws NullPointerException If the plugin or command executor is null.
     */
    public static void registerCommand(JavaPlugin plugin, String commandName, CommandExecutor executor) {
        Objects.requireNonNull(plugin, "Plugin cannot be null");
        Objects.requireNonNull(executor, "CommandExecutor cannot be null");

        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(executor);
    }
}
