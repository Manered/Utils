package dev.manere.utils.player.cache;

import dev.manere.utils.text.Text;

/**
 * Represents a cached value in the player cache.
 *
 * @param val The cached value.
 */
public record CacheVal(Object val) {

    /**
     * Creates a new CacheVal instance with the specified value.
     *
     * @param val The value to be wrapped in a CacheVal instance.
     * @return A new CacheVal instance.
     */
    public static CacheVal cached(Object val) {
        return new CacheVal(val);
    }

    /**
     * Returns the value stored as an Object.
     *
     * @return the value stored as an Object.
     */
    public Object asObject() {
        return val;
    }

    /**
     * Converts the cached value to a Text.
     *
     * @return A Text instance representing the cached value.
     */
    public Text asText() {
        return Text.text((String) val);
    }

    /**
     * Converts the cached value to a boolean.
     *
     * @return A boolean instance representing the cached value.
     */
    public boolean asBoolean() {
        return (boolean) val;
    }

    /**
     * Converts the cached value to a byte.
     *
     * @return A byte instance representing the cached value.
     */
    public byte asByte() {
        return (byte) val;
    }

    /**
     * Converts the cached value to a double.
     *
     * @return A double instance representing the cached value.
     */
    public double asDouble() {
        return (double) val;
    }

    /**
     * Converts the cached value to a float.
     *
     * @return A float instance representing the cached value.
     */
    public float asFloat() {
        return (float) val;
    }

    /**
     * Converts the cached value to an int.
     *
     * @return An int instance representing the cached value.
     */
    public int asInt() {
        return (int) val;
    }

    /**
     * Converts the cached value to a long.
     *
     * @return A long instance representing the cached value.
     */
    public long asLong() {
        return (long) val;
    }

    /**
     * Converts the cached value to a short.
     *
     * @return A short instance representing the cached value.
     */
    public short asShort() {
        return (short) val;
    }
}
