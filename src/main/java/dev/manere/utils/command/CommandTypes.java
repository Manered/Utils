package dev.manere.utils.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * An enumeration representing the registration system to use.
 */
public class CommandTypes {
    private final int type;

    private CommandTypes(int type) {
        this.type = type;
    }

    /**
     * Self-explanatory. Uses the plugin.yml file
     * and {@link JavaPlugin#getCommand(String)} to retrieve and register the command.
     */
    public static final CommandTypes PLUGIN_YML = CommandTypes.type(0);

    /**
     * Used for registering commands via the {@link CommandMap}
     * Does not require the command to be in the plugin.yml of your project.
     * <P>
     * If you have a command of {@code /hello} and your plugin's name is HelloPlugin
     * then it will register with the default namespace of your plugin: {@code /helloplugin:hello}
     * <P>
     * Access to the command map is provided via the {@link Bukkit#getCommandMap()} method.
     */
    public static final CommandTypes COMMAND_MAP = CommandTypes.type(1);

    public static CommandTypes pluginYml() {
        return PLUGIN_YML;
    }

    public static CommandTypes plugin() {
        return pluginYml();
    }

    public static CommandTypes descriptionFile() {
        return pluginYml();
    }

    public static CommandTypes descFile() {
        return pluginYml();
    }

    public static CommandTypes pluginMeta() {
        return pluginYml();
    }

    public static CommandTypes meta() {
        return pluginYml();
    }

    public static CommandTypes commandMap() {
        return COMMAND_MAP;
    }

    public static CommandTypes map() {
        return commandMap();
    }

    public static CommandTypes paper() {
        return commandMap();
    }

    public int type() {
        return type;
    }

    public static CommandTypes type(int type) {
        return new CommandTypes(type);
    }
}
