package dev.manere.utils.config.section;

import dev.manere.utils.config.ConfigKey;
import dev.manere.utils.config.val.ConfigVal;
import dev.manere.utils.model.Duo;

import java.util.function.Consumer;

/**
 * Utility class for selecting and iterating over keys and values within a specific section of the plugin's configuration.
 */
public class ConfigSelection {

    private final ConfigSection section;

    /**
     * Constructs a ConfigSelection for the specified ConfigSection.
     *
     * @param section The ConfigSection for which keys and values will be selected.
     */
    public ConfigSelection(ConfigSection section) {
        this.section = section;
    }

    /**
     * Creates a new ConfigSelection instance for the specified ConfigSection.
     *
     * @param section The ConfigSection for which keys and values will be selected.
     * @return A new ConfigSelection instance.
     */
    public static ConfigSelection selection(ConfigSection section) {
        return new ConfigSelection(section);
    }

    /**
     * Iterates over each key-value pair in the selected section and applies the specified consumer.
     *
     * @param consumer The consumer to apply to each key-value pair.
     */
    public void forEach(Consumer<Duo<ConfigKey, ConfigVal>> consumer) {
        if (this.section.section != null) {
            for (String key : this.section.section.getKeys(false)) {
                consumer.accept(new Duo<>(ConfigKey.key(key), ConfigKey.key(key).val()));
            }
        }
    }
}
