package dev.manere.utils.logger;

import dev.manere.utils.library.Utils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

/**
 * The Loggers class provides utility methods for obtaining different types of loggers
 * in a Bukkit plugin, including Adventure loggers and prefixed loggers.
 */
public class Loggers {
    /**
     * Returns an AdventureLogger instance for logging messages with Adventure components.
     *
     * @return An AdventureLogger instance.
     */
    public static AdventureLogger adventure() {
        return new AdventureLogger();
    }

    /**
     * Returns an AdventureLogger instance for logging messages with Adventure components.
     *
     * @return An AdventureLogger instance.
     */
    public static AdventureLogger component() {
        return new AdventureLogger();
    }

    /**
     * Returns the prefixed logger associated with the current plugin.
     *
     * @return The logger instance.
     */
    public static Logger prefixed() {
        return Utils.plugin().getLogger();
    }

    /**
     * Returns the prefixed logger associated with the provided JavaPlugin.
     *
     * @param plugin The JavaPlugin instance.
     * @return The logger instance.
     */
    public static Logger prefixed(JavaPlugin plugin) {
        return plugin.getLogger();
    }

    /**
     * Returns the prefixed logger associated with the provided Plugin.
     *
     * @param plugin The Plugin instance.
     * @return The logger instance.
     */
    public static Logger prefixed(Plugin plugin) {
        return plugin.getLogger();
    }

    /**
     * Returns the server logger associated with the current plugin.
     *
     * @return The server logger instance.
     */
    public static Logger server() {
        return Utils.plugin().getServer().getLogger();
    }

    /**
     * Returns the server logger associated with the provided JavaPlugin.
     *
     * @param plugin The JavaPlugin instance.
     * @return The server logger instance.
     */
    public static Logger server(JavaPlugin plugin) {
        return plugin.getServer().getLogger();
    }

    /**
     * Returns the server logger associated with the provided Plugin.
     *
     * @param plugin The Plugin instance.
     * @return The server logger instance.
     */
    public static Logger server(Plugin plugin) {
        return plugin.getServer().getLogger();
    }

    /**
     * Returns the class logger associated with the provided class.
     *
     * @param clazz The class.
     * @return The class logger.
     */
    public static <L> org.slf4j.Logger logger(@NotNull Class<L> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
