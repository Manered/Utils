package dev.manere.utils.config;

import dev.manere.utils.config.val.ConfigVal;
import dev.manere.utils.library.Utils;

/**
 * Represents a configuration key with its corresponding value and path.
 */
public class ConfigKey {
    public final Object val;
    public final String path;

    /**
     * Constructs a ConfigKey with the specified key. Retrieves the corresponding value from the plugin's configuration.
     *
     * @param key The key used to identify the configuration value.
     */
    public ConfigKey(String key) {
        this.val = Utils.plugin().getConfig().get(key);
        this.path = key;
    }

    /**
     * Creates a new ConfigKey instance with the specified key.
     *
     * @param key The key used to identify the configuration value.
     * @return A new ConfigKey instance.
     */
    public static ConfigKey key(String key) {
        return new ConfigKey(key);
    }

    /**
     * Retrieves a ConfigVal instance associated with this ConfigKey.
     *
     * @return A ConfigVal instance representing the value of this ConfigKey.
     */
    public ConfigVal val() {
        return ConfigVal.val(this);
    }
}
