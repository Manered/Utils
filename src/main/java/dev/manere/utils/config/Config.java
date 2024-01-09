package dev.manere.utils.config;

import dev.manere.utils.config.section.ConfigSection;
import dev.manere.utils.config.section.ConfigSelection;
import dev.manere.utils.config.setter.ConfigSetter;
import dev.manere.utils.library.Utils;
import dev.manere.utils.server.Servers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Utility class for managing and interacting with configuration settings in a Bukkit plugin.
 */
public class Config {
    /**
     * Retrieves the singleton instance of the Config class.
     *
     * @return The Config instance.
     */
    public static @NotNull Config config() {
        return new Config();
    }

    /**
     * Initializes the configuration file. If the file does not exist, it will be created and populated with default values.
     */
    public static void init() {
        File file = new File(Servers.dataFolder(), "config.yml");

        if (file.exists()) return;

        Utils.plugin().saveDefaultConfig();
        Utils.plugin().reloadConfig();
    }

    /**
     * Creates a new ConfigKey instance with the specified key.
     *
     * @param key The key used to identify the configuration value.
     * @return A new ConfigKey instance.
     */
    public static @NotNull ConfigKey key(@NotNull String key) {
        return ConfigKey.key(key);
    }

    /**
     * Creates a new ConfigSelection instance for the specified ConfigSection.
     *
     * @param section The ConfigSection for which a selection is created.
     * @return A new ConfigSelection instance.
     */
    public static @NotNull ConfigSelection selection(@NotNull ConfigSection section) {
        return ConfigSelection.selection(section);
    }

    /**
     * Saves the current configuration to the file.
     */
    public static void save() {
        Utils.plugin().saveConfig();
    }

    /**
     * Creates a new ConfigSetter instance for configuring a specific key-value pair.
     *
     * @return A new ConfigSetter instance.
     */
    public static @NotNull ConfigSetter set() {
        return new ConfigSetter();
    }

    /**
     * Sets a key in the configuration with the specified value.
     *
     * @param key        The key to set.
     * @param object     The value to associate with the key.
     */
    public static void set(@NotNull ConfigKey key, @Nullable Object object) {
        set(key, object, false);
    }

    /**
     * Sets a key in the configuration with the specified value and default value flag.
     *
     * @param key         The key to set.
     * @param object      The value to associate with the key.
     * @param defaultVal  A flag indicating whether the specified value is the default value.
     */
    public static void set(@NotNull ConfigKey key, @Nullable Object object, boolean defaultVal) {
        new ConfigSetter()
                .key(key)
                .val(object)
                .defaultVal(defaultVal);
    }

    /**
     * Deletes a key from the configuration.
     *
     * @param key The key to delete.
     */
    public static void delete(@NotNull ConfigKey key) {
        Utils.plugin().getConfig().set(key.path, null);
    }

    /**
     * Creates a new ConfigSection instance with the specified section name.
     *
     * @param section The name of the section.
     * @return A new ConfigSection instance.
     */
    public static ConfigSection section(@NotNull String section) {
        return ConfigSection.section(section);
    }

    /**
     * Deletes a ConfigSection from the configuration.
     *
     * @param section The ConfigSection to delete.
     */
    public static void delete(@NotNull ConfigSection section) {
        section.delete();
    }

    /**
     * Reloads the configuration from the file.
     */
    public static void reload() {
        Utils.plugin().reloadConfig();
    }

    @NotNull Config a() {
        return this;
    }
}
