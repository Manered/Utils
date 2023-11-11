package dev.manere.utils.model;

/**
 * A generic tuple class to represent a key-value pair.
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public class Duo<K, V> {
    private K key;
    private V val;

    /**
     * Creates a new tuple with the given key and value.
     *
     * @param key The key
     * @param val The value
     */
    public Duo(K key, V val) {
        this.key = key;
        this.val = val;
    }

    /**
     * Creates a new tuple with the given key and value.
     *
     * @param <K> The key type
     * @param <V> The value type
     * @param key The key
     * @param val The value
     * @return A new tuple with the specified key and value
     */
    public static <K, V> Duo<K, V> of(K key, V val) {
        return new Duo<>(key, val);
    }

    /**
     * Returns a string representation of this tuple.
     *
     * @return The string representation
     */
    @Override
    public String toString() {
        return "([<key>]:[<val>])"
                .replaceAll("<key>", this.key.toString())
                .replaceAll("<val>", this.val.toString());
    }

    /**
     * Sets the key for this tuple.
     *
     * @param key The new key
     * @return This tuple instance
     */
    public Duo<K, V> key(K key) {
        this.key = key;
        return this;
    }

    /**
     * Sets the value for this tuple.
     *
     * @param val The new value
     * @return This tuple instance
     */
    public Duo<K, V> val(V val) {
        this.val = val;
        return this;
    }

    /**
     * Gets the key.
     *
     * @return The key
     */
    public K key() {
        return key;
    }

    /**
     * Gets the value.
     *
     * @return The value
     */
    public V val() {
        return val;
    }
}
