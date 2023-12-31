package dev.manere.utils.config.val;

import dev.manere.utils.config.ConfigKey;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.location.LocationUtils;
import dev.manere.utils.text.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a value associated with a configuration key.
 */
public class ConfigVal {
    private final ConfigKey key;
    private final Object object;
    final String path;

    /**
     * Constructs a ConfigVal with the specified ConfigKey.
     *
     * @param key The ConfigKey associated with this ConfigVal.
     */
    public ConfigVal(ConfigKey key) {
        this.key = key;
        this.object = key.val;
        this.path = key.path;
    }

    /**
     * Creates a new ConfigVal instance with the specified ConfigKey.
     *
     * @param key The ConfigKey associated with this ConfigVal.
     * @return A new ConfigVal instance.
     */
    public static @NotNull ConfigVal val(@NotNull ConfigKey key) {
        return new ConfigVal(key);
    }

    /**
     * Converts the value to a Bukkit Location.
     *
     * @return A Bukkit Location representing the value.
     */
    public @Nullable Location asLocation() {
        return LocationUtils.deserialize((String) object);
    }

    /**
     * Converts the value to a Bukkit ItemStack.
     *
     * @return A Bukkit ItemStack representing the value.
     */
    public @Nullable ItemStack asItemStack() {
        return ItemStack.deserializeBytes(((ItemStack) object).serializeAsBytes());
    }

    /**
     * Converts the value to an ItemBuilder.
     *
     * @return An ItemBuilder representing the value.
     */
    public @Nullable ItemBuilder asItemBuilder() {
        return ItemBuilder.item(asItemStack());
    }

    /**
     * Converts the value to a Text object.
     *
     * @return A Text object representing the value.
     */
    public @Nullable Text asText() {
        return Text.text((String) object);
    }

    /**
     * Converts the value to a UUID.
     *
     * @return A UUID representing the value.
     */
    public @Nullable UUID asUUID() {
        return UUID.fromString((String) object);
    }

    /**
     * Converts the value to an integer.
     *
     * @return An integer representing the value.
     */
    public int asInt() {
        return Integer.parseInt((String) object);
    }

    /**
     * Converts the value to a double.
     *
     * @return A double representing the value.
     */
    public double asDouble() {
        return Double.parseDouble((String) object);
    }

    /**
     * Converts the value to a float.
     *
     * @return A float representing the value.
     */
    public float asFloat() {
        return Float.parseFloat((String) object);
    }

    /**
     * Converts the value to a boolean.
     *
     * @return A boolean representing the value.
     */
    public boolean asBoolean() {
        return Boolean.parseBoolean((String) object);
    }

    /**
     * Converts the value to a Material.
     *
     * @return A Material representing the value.
     */
    public @Nullable Material asMaterial() {
        return Material.matchMaterial((String) object);
    }

    /**
     * Retrieves a ConfigList instance associated with this ConfigVal.
     *
     * @return A ConfigList instance representing the value as a list.
     */
    public @Nullable ConfigList asListOf() {
        return ConfigList.list(this);
    }

    /**
     * Returns the Object value representation.
     * @return the Object value representation.
     */
    public @Nullable Object raw() {
        return object;
    }

    /**
     * Returns a Object cast value representation
     * @param type The class type of the return value.
     * @return T
     * @param <T> The type (converted) value.
     */
    @SuppressWarnings("unchecked")
    public <T> @Nullable T as(@NotNull Class<T> type) {
        if (type == Component.class) {
            return (T) asText().component();
        }

        return (T) object;
    }

    public ConfigKey key() {
        return key;
    }
}
