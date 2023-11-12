package dev.manere.utils.config.setter;

import dev.manere.utils.config.Config;
import dev.manere.utils.config.section.ConfigSection;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.text.Text;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Utility class for setting values in a specific section of the plugin's configuration.
 */
public class ConfigSectionSetter {

    private final ConfigSection configSection;
    private Object val;
    private String key;
    private boolean defaultVal;

    /**
     * Constructs a ConfigSectionSetter for the specified ConfigSection with default values.
     *
     * @param configSection The ConfigSection for which values will be set.
     */
    public ConfigSectionSetter(ConfigSection configSection) {
        this.configSection = configSection;
        this.defaultVal = true;
    }

    /**
     * Sets the key for the configuration value within the section.
     *
     * @param key The key for the configuration value within the section.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter key(String key) {
        this.key = configSection.sectionPath + "." + key;
        return this;
    }

    /**
     * Sets the value for the configuration key.
     *
     * @param val The value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(Object val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as Text.
     *
     * @param val The Text value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(Text val) {
        this.val = val.toString();
        return this;
    }

    /**
     * Sets the value for the configuration key as Location.
     *
     * @param val The Location value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(Location val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as ItemStack.
     *
     * @param val The ItemStack value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(ItemStack val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as ItemBuilder.
     *
     * @param val The ItemBuilder value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(ItemBuilder val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a List.
     *
     * @param val The List value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(List<?> val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as an integer.
     *
     * @param val The integer value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(int val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a double.
     *
     * @param val The double value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(double val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a long.
     *
     * @param val The long value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(long val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a float.
     *
     * @param val The float value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(float val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a boolean.
     *
     * @param val The boolean value to set for the configuration key.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter val(boolean val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the default value flag for the configuration key.
     *
     * @param defaultVal A boolean flag indicating whether the value is the default value.
     * @return The ConfigSectionSetter instance for method chaining.
     */
    public ConfigSectionSetter defaultVal(boolean defaultVal) {
        this.defaultVal = defaultVal;
        return this;
    }

    /**
     * Builds and applies the configured key-value pair to the plugin's configuration section.
     *
     * @return The Config instance for further configuration or interaction.
     * @throws IllegalArgumentException If key or val is null.
     */
    public Config build() {
        if (key == null || val == null) {
            throw new IllegalArgumentException("key or val must not be null");
        }

        Utils.plugin().getConfig().set(this.key, val);

        if (defaultVal) {
            Utils.plugin().getConfig().addDefault(this.key, val);
        }

        return Config.config();
    }
}
