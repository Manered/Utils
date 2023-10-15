package dev.manere.utils.logger;

import dev.manere.utils.library.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * The LogBuilder class provides a fluent interface for creating and logging messages
 * with optional automatic prefixing in Bukkit plugins.
 * <P></P>
 * This class allows you to configure the log message's text, log level, associated plugin,
 * and whether automatic prefixing should be applied.
 */
public class LogBuilder {
    private Level level;
    private String text;
    private JavaPlugin plugin;
    private boolean withAutomaticPrefix;

    /**
     * Constructs a new LogBuilder instance with default settings.
     * The default plugin is retrieved from Utils.getPlugin(), and automatic prefixing is disabled.
     */
    public LogBuilder() {
        this.plugin = Utils.getPlugin();
        this.withAutomaticPrefix = false;
    }

    /**
     * Sets the log message text.
     *
     * @param text The text of the log message.
     * @return This LogBuilder instance for method chaining.
     */
    public LogBuilder text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets the log level for the message.
     *
     * @param level The log level for the message.
     * @return This LogBuilder instance for method chaining.
     */
    public LogBuilder level(Level level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the associated plugin for the log message.
     *
     * @param plugin The JavaPlugin instance associated with the log message.
     * @return This LogBuilder instance for method chaining.
     */
    public LogBuilder plugin(JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    /**
     * Sets whether automatic prefixing should be applied to the log message.
     *
     * @param withAutomaticPrefix True if automatic prefixing should be applied, false otherwise.
     * @return This LogBuilder instance for method chaining.
     */
    public LogBuilder prefix(boolean withAutomaticPrefix) {
        this.withAutomaticPrefix = withAutomaticPrefix;
        return this;
    }

    /**
     * Creates a new LogBuilder instance with default settings.
     *
     * @return A new LogBuilder instance.
     */
    public static LogBuilder of() {
        return new LogBuilder();
    }

    /**
     * Logs the configured message based on the set log level and settings.
     * The message will be logged to the appropriate logger (plugin or server) based on the settings.
     */
    public void build() {
        if (plugin != null && withAutomaticPrefix && text != null && level != null)
            plugin.getLogger().log(level, text);

        if (plugin != null && !withAutomaticPrefix && text != null && level != null)
            plugin.getServer().getLogger().log(level, text);

        if (plugin == null && withAutomaticPrefix && text != null && level != null)
            Utils.getPlugin().getLogger().log(level, text);

        if (plugin == null && !withAutomaticPrefix && text != null && level != null)
            Utils.getPlugin().getServer().getLogger().log(level, text);
    }
}
