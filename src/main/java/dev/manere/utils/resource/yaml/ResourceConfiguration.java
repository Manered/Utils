package dev.manere.utils.resource.yaml;

import dev.manere.utils.cachable.Cachable;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.model.Tuple;
import dev.manere.utils.resource.ResourceFile;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record ResourceConfiguration(ResourceFile resourceFile) {
    /**
     * Retrieves the value associated with the specified key as an Object.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public Object val(String key) {
        return bukkit().get(key);
    }

    /**
     * Retrieves the value associated with the specified key as a String.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public String valAsString(String key) {
        return bukkit().getString(key);
    }

    /**
     * Retrieves the value associated with the specified key as a boolean.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public boolean valAsBoolean(String key) {
        return bukkit().getBoolean(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of booleans.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Boolean> valAsBooleanList(String key) {
        return bukkit().getBooleanList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of bytes.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Byte> valAsByteList(String key) {
        return bukkit().getByteList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of characters.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Character> valAsCharacterList(String key) {
        return bukkit().getCharacterList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a {@link Color}.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public Color valAsColor(String key) {
        return bukkit().getColor(key);
    }

    /**
     * Retrieves the value associated with the specified key as a double.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public double valAsDouble(String key) {
        return bukkit().getDouble(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of doubles.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Double> valAsDoubleList(String key) {
        return bukkit().getDoubleList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of floats.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Float> valAsFloatList(String key) {
        return bukkit().getFloatList(key);
    }

    /**
     * Retrieves the value associated with the specified key as an int.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public int valAsInt(String key) {
        return bukkit().getInt(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of ints.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Integer> valAsIntList(String key) {
        return bukkit().getIntegerList(key);
    }

    /**
     * Retrieves the value associated with the specified key as an item stack.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public ItemStack valAsItemStack(String key) {
        return bukkit().getItemStack(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of ? (any Object).
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<?> valAsList(String key) {
        return bukkit().getList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a location.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public Location valAsLocation(String key) {
        return bukkit().getLocation(key);
    }

    /**
     * Retrieves the value associated with the specified key as a long.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public long valAsLong(String key) {
        return bukkit().getLong(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of longs.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Long> valAsLongList(String key) {
        return bukkit().getLongList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of maps of ?, ? (Object, Object).
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Map<?, ?>> valAsMapList(String key) {
        return bukkit().getMapList(key);
    }

    /**
     * Retrieves the value associated with the specified key as an Object.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    @SuppressWarnings("unchecked")
    public <T> T val(String key, Class<T> clazz) {
        if (clazz.getCanonicalName().equals(Component.class.getCanonicalName())) {
            return (T) valAsComponent(key);
        }

        if (clazz.getCanonicalName().equals(ItemBuilder.class.getCanonicalName())) {
            return (T) valAsItemBuilder(key);
        }

        return bukkit().getObject(key, clazz);
    }

    /**
     * Retrieves the value associated with the specified key as an {@link OfflinePlayer}.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public OfflinePlayer valAsOfflinePlayer(String key) {
        return bukkit().getOfflinePlayer(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of shorts.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<Short> valAsShortList(String key) {
        return bukkit().getShortList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a list of strings.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public List<String> valAsStringList(String key) {
        return bukkit().getStringList(key);
    }

    /**
     * Retrieves the value associated with the specified key as a vector.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public Vector valAsVector(String key) {
        return bukkit().getVector(key);
    }

    /**
     * Retrieves the value associated with the specified key as an item builder.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public ItemBuilder valAsItemBuilder(String key) {
        return ItemBuilder.item(valAsItemStack(key));
    }

    /**
     * Retrieves the value associated with the specified key as a {@link Component}.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public Component valAsComponent(String key) {
        return TextStyle.style(valAsString(key));
    }

    /**
     * Sets the value associated with the specified key in the configuration.
     *
     * @param key The key to set the value for.
     * @param val The value to set.
     */
    public void set(String key, Object val) {
        bukkit().set(key, val);
        resourceFile.saveYml();
    }

    /**
     * Sets the value associated with the specified key in the configuration.
     *
     * @param tuple The Tuple to obtain the key and value from.
     */
    public void set(Tuple<String, Object> tuple) {
        if (tuple != null && tuple.key() != null) {
            set(Objects.requireNonNull(tuple.key()), tuple.val());
        }
    }

    /**
     * Creates and returns a Cachable instance containing key-value pairs from the configuration.
     *
     * @return Cachable instance containing key-value pairs from the configuration.
     */
    public Cachable<String, Object> keyVal() {
        Cachable<String, Object> cachable = Cachable.of();

        for (String key : keys()) {
            Object val = bukkit().get(key);
            cachable.cache(key, val);
        }

        return cachable;
    }

    /**
     * Retrieves a collection of all keys in the configuration.
     *
     * @return Collection of all keys in the configuration.
     */
    public Collection<String> keys() {
        return bukkit().getKeys(false);
    }

    /**
     * Loads the Bukkit YamlConfiguration associated with this resource file.
     *
     * @return The loaded YamlConfiguration.
     */
    public YamlConfiguration bukkit() {
        return (YamlConfiguration) resourceFile.loadYml();
    }
}
