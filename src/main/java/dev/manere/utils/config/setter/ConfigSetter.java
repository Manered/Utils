package dev.manere.utils.config.setter;

import dev.manere.utils.config.Config;
import dev.manere.utils.config.ConfigKey;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.text.Text;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Utility class for setting values in the plugin's configuration.
 */
public class ConfigSetter {

    private ConfigKey key;
    private Object val;
    private boolean defaultVal;

    /**
     * Constructs a ConfigSetter with default values.
     */
    public ConfigSetter() {
        this.defaultVal = false;
    }

    /**
     * Sets the ConfigKey for the configuration value.
     *
     * @param key The ConfigKey for the configuration value.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter key(@NotNull ConfigKey key) {
        this.key = key;
        return this;
    }

    /**
     * Sets the value for the configuration key.
     *
     * @param val The value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(@Nullable Object val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as Text.
     *
     * @param val The Text value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(@Nullable Text val) {
        if (val != null) {
            this.val = val.toString();
        }

        return this;
    }

    /**
     * Sets the value for the configuration key as Location.
     *
     * @param val The Location value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(@Nullable Location val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as ItemStack.
     *
     * @param val The ItemStack value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(@Nullable ItemStack val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as ItemBuilder.
     *
     * @param val The ItemBuilder value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(@Nullable ItemBuilder val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a List.
     *
     * @param val The List value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(@Nullable List<?> val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as an integer.
     *
     * @param val The integer value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(int val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a double.
     *
     * @param val The double value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(double val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a long.
     *
     * @param val The long value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(long val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a float.
     *
     * @param val The float value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(float val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the value for the configuration key as a boolean.
     *
     * @param val The boolean value to set for the configuration key.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter val(boolean val) {
        this.val = val;
        return this;
    }

    /**
     * Sets the default value flag for the configuration key.
     *
     * @param defaultVal A boolean flag indicating whether the value is the default value.
     * @return The ConfigSetter instance for method chaining.
     */
    public @NotNull ConfigSetter defaultVal(boolean defaultVal) {
        this.defaultVal = defaultVal;
        return this;
    }

    /**
     * Builds and applies the configured key-value pair to the plugin's configuration.
     *
     * @return The Config instance for further configuration or interaction.
     * @throws IllegalArgumentException If key, key.val, or val is null.
     */
    public @NotNull Config build() {
        if (key == null || key.val == null || val == null) {
            throw new IllegalArgumentException("key or val must not be null");
        }

        Utils.plugin().getConfig().set(key.path, val);

        if (defaultVal) {
            Utils.plugin().getConfig().addDefault(key.path, val);
        }

        return Config.config();
    }
}
