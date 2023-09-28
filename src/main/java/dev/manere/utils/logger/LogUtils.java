/**
 * The LogUtils class provides utility methods for logging messages with various log levels
 * in Bukkit plugins. It also offers methods for logging without automatic prefixing.
 */
package dev.manere.utils.logger;

import dev.manere.utils.library.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class LogUtils {

    /**
     * Creates a new LogBuilder instance with default settings.
     *
     * @return A new LogBuilder instance.
     */
    public static LogBuilder builder() {
        return LogBuilder.of();
    }

    /**
     * Logs an error message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the error message.
     */
    public static void error(JavaPlugin plugin, String text) {
        plugin.getLogger().severe(text);
    }

    /**
     * Logs a severe message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the severe message.
     */
    public static void severe(JavaPlugin plugin, String text) {
        plugin.getLogger().severe(text);
    }

    /**
     * Logs a warning message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the warning message.
     */
    public static void warn(JavaPlugin plugin, String text) {
        plugin.getLogger().warning(text);
    }

    /**
     * Logs a warning message with the specified JavaPlugin instance.
     * This method is an alias for the "warn" method.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the warning message.
     */
    public static void warning(JavaPlugin plugin, String text) {
        warn(plugin, text);
    }

    /**
     * Logs a fine message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the fine message.
     */
    public static void fine(JavaPlugin plugin, String text) {
        plugin.getLogger().fine(text);
    }

    /**
     * Logs a finer message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the finer message.
     */
    public static void finer(JavaPlugin plugin, String text) {
        plugin.getLogger().finer(text);
    }

    /**
     * Logs the finest message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the finest message.
     */
    public static void finest(JavaPlugin plugin, String text) {
        plugin.getLogger().finest(text);
    }

    /**
     * Logs a config message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the config message.
     */
    public static void config(JavaPlugin plugin, String text) {
        plugin.getLogger().config(text);
    }

    /**
     * Logs a configuration message with the specified JavaPlugin instance.
     * This method is an alias for the "config" method.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the configuration message.
     */
    public static void configuration(JavaPlugin plugin, String text) {
        config(plugin, text);
    }

    /**
     * Logs an info message with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the info message.
     */
    public static void info(JavaPlugin plugin, String text) {
        plugin.getLogger().info(text);
    }

    /**
     * Logs an information message with the specified JavaPlugin instance.
     * This method is an alias for the "info" method.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param text   The text of the information message.
     */
    public static void information(JavaPlugin plugin, String text) {
        info(plugin, text);
    }

    /**
     * Logs a message with the specified log level and JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to use for logging.
     * @param level  The log level for the message.
     * @param text   The text of the log message.
     */
    public static void log(JavaPlugin plugin, Level level, String text) {
        plugin.getLogger().log(level, text);
    }

    /**
     * Logs a message with the specified log level, JavaPlugin instance, and throwable exception.
     *
     * @param plugin    The JavaPlugin instance to use for logging.
     * @param level     The log level for the message.
     * @param text      The text of the log message.
     * @param throwable The throwable exception to log.
     */
    public static void log(JavaPlugin plugin, Level level, String text, Throwable throwable) {
        plugin.getLogger().log(level, text, throwable);
    }

    /**
     * Logs an error message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the error message.
     */
    public static void error(String text) {
        Utils.getPlugin().getLogger().severe(text);
    }

    /**
     * Logs a severe message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the severe message.
     */
    public static void severe(String text) {
        Utils.getPlugin().getLogger().severe(text);
    }

    /**
     * Logs a warning message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the warning message.
     */
    public static void warn(String text) {
        Utils.getPlugin().getLogger().warning(text);
    }

    /**
     * Logs a warning message with the default plugin instance (retrieved from Utils.getPlugin()).
     * This method is an alias for the "warn" method.
     *
     * @param text The text of the warning message.
     */
    public static void warning(String text) {
        warn(text);
    }

    /**
     * Logs a fine message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the fine message.
     */
    public static void fine(String text) {
        Utils.getPlugin().getLogger().fine(text);
    }

    /**
     * Logs a finer message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the finer message.
     */
    public static void finer(String text) {
        Utils.getPlugin().getLogger().finer(text);
    }

    /**
     * Logs a finest message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the finest message.
     */
    public static void finest(String text) {
        Utils.getPlugin().getLogger().finest(text);
    }

    /**
     * Logs a config message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the config message.
     */
    public static void config(String text) {
        Utils.getPlugin().getLogger().config(text);
    }

    /**
     * Logs a configuration message with the default plugin instance (retrieved from Utils.getPlugin()).
     * This method is an alias for the "config" method.
     *
     * @param text The text of the configuration message.
     */
    public static void configuration(String text) {
        config(text);
    }

    /**
     * Logs an info message with the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the info message.
     */
    public static void info(String text) {
        Utils.getPlugin().getLogger().info(text);
    }

    /**
     * Logs an information message with the default plugin instance (retrieved from Utils.getPlugin()).
     * This method is an alias for the "info" method.
     *
     * @param text The text of the information message.
     */
    public static void information(String text) {
        info(text);
    }

    /**
     * Logs a message with the specified log level using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param level The log level for the message.
     * @param text  The text of the log message.
     */
    public static void log(Level level, String text) {
        Utils.getPlugin().getLogger().log(level, text);
    }

    /**
     * Logs a message with the specified log level, text, and throwable exception
     * using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param level     The log level for the message.
     * @param text      The text of the log message.
     * @param throwable The throwable exception to log.
     */
    public static void log(Level level, String text, Throwable throwable) {
        Utils.getPlugin().getLogger().log(level, text, throwable);
    }

    /**
     * Logs an error message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the error message.
     */
    public static void errorNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().severe(text);
    }

    /**
     * Logs a severe message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the severe message.
     */
    public static void severeNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().severe(text);
    }

    /**
     * Logs a warning message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the warning message.
     */
    public static void warnNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().warning(text);
    }

    /**
     * Logs a warning message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     * This method is an alias for the "warnNoPrefix" method.
     *
     * @param text The text of the warning message.
     */
    public static void warningNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().warning(text);
    }

    /**
     * Logs a fine message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the fine message.
     */
    public static void fineNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().fine(text);
    }

    /**
     * Logs a finer message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the finer message.
     */
    public static void finerNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().finer(text);
    }

    /**
     * Logs a finest message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the finest message.
     */
    public static void finestNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().finest(text);
    }

    /**
     * Logs a config message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the config message.
     */
    public static void configNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().config(text);
    }

    /**
     * Logs a configuration message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     * This method is an alias for the "configNoPrefix" method.
     *
     * @param text The text of the configuration message.
     */
    public static void configurationNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().config(text);
    }

    /**
     * Logs an info message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param text The text of the info message.
     */
    public static void infoNoPrefix(String text) {
        Utils.getPlugin().getServer().getLogger().info(text);
    }

    /**
     * Logs an information message without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     * This method is an alias for the "infoNoPrefix" method.
     *
     * @param text The text of the information message.
     */
    public static void informationNoPrefix(String text) {
        infoNoPrefix(text);
    }

    /**
     * Logs a message with the specified log level without automatic prefixing
     * using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param level The log level for the message.
     * @param text  The text of the log message.
     */
    public static void logNoPrefix(Level level, String text) {
        Utils.getPlugin().getServer().getLogger().log(level, text);
    }

    /**
     * Logs a message with the specified log level, text, and throwable exception
     * without automatic prefixing using the default plugin instance (retrieved from Utils.getPlugin()).
     *
     * @param level     The log level for the message.
     * @param text      The text of the log message.
     * @param throwable The throwable exception to log.
     */
    public static void logNoPrefix(Level level, String text, Throwable throwable) {
        Utils.getPlugin().getServer().getLogger().log(level, text, throwable);
    }
}
