package dev.manere.utils.command;

import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * An enumeration representing the registration system to use.
 */
public enum CommandType {

    /**
     * Self-explanatory. Uses the plugin.yml file
     * and {@link JavaPlugin#getCommand(String)} to retrieve and register the command.
     */
    PLUGIN_YML,

    /**
     * Used for registering commands via the {@link CommandMap}
     * Does not require the command to be in the plugin.yml of your project.
     */
    COMMAND_MAP,
}
