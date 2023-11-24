package dev.manere.utils.config.section;

import dev.manere.utils.config.setter.ConfigSectionSetter;
import dev.manere.utils.library.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a configuration section in a Bukkit configuration file.
 * This class provides methods for creating, modifying, and retrieving values from the specified section.
 */
public class ConfigSection {
    public final ConfigurationSection section;
    public final String sectionPath;

    /**
     * Constructs a new ConfigSection with the specified section name.
     * If the section does not exist in the configuration file, it will be created.
     *
     * @param section The name of the configuration section.
     */
    public ConfigSection(@NotNull String section) {
        if (Utils.plugin().getConfig().getConfigurationSection(section) == null) {
            Utils.plugin().getConfig().createSection(section);
        }

        this.section = Utils.plugin().getConfig().getConfigurationSection(section);
        this.sectionPath = section;
    }

    /**
     * Static factory method to create a new ConfigSection with the specified section name.
     *
     * @param section The name of the configuration section.
     * @return A new instance of ConfigSection for the specified section.
     */
    public static @NotNull ConfigSection section(@NotNull String section) {
        return new ConfigSection(section);
    }

    /**
     * Returns a ConfigSectionSetter associated with this ConfigSection for modifying values.
     *
     * @return A ConfigSectionSetter instance for this ConfigSection.
     */
    public @NotNull ConfigSectionSetter set() {
        return new ConfigSectionSetter(this);
    }

    /**
     * Retrieves the value associated with the specified key within this ConfigSection.
     *
     * @param key The key within the section to retrieve the value for.
     * @return The value associated with the specified key, or null if not found.
     */
    public @Nullable Object get(@NotNull String key) {
        return section.get(sectionPath + "." + key);
    }

    /**
     * Deletes the entire configuration section from the underlying configuration file.
     */
    public void delete() {
        Utils.plugin().getConfig().set(sectionPath, null);
    }
}
