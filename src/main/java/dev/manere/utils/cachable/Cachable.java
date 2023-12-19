package dev.manere.utils.cachable;

import dev.manere.utils.cachable.impl.CachableImpl;
import dev.manere.utils.consumers.PairConsumer;
import dev.manere.utils.model.Tuple;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * The {@code Cachable} interface defines methods for a caching mechanism that associates keys with values.
 *
 * @param <K> The type of keys in the cache.
 * @param <V> The type of values in the cache.
 */
public interface Cachable<K, V> {
    /**
     * Creates and returns a Cachable instance.
     *
     * @param <K> The key type
     * @param <V> The value type
     * @return A new Cachable instance
     */
    static <K, V> CachableImpl<K, V> of() {
        return new CachableImpl<>();
    }

    /**
     * Creates and returns a Cachable instance initialized with the given entries.
     *
     * @param <K> The key type
     * @param <V> The value type
     * @param entries The initial entries to cache
     * @return A Cachable instance with the given initial entries
     */
    static <K, V> CachableImpl<K, V> of(Collection<Tuple<K, V>> entries) {
        CachableImpl<K, V> cachable = new CachableImpl<>();
        cachable.cacheAll(entries);

        return cachable;
    }

    /**
     * Creates and returns a Cachable instance initialized with the given entries.
     *
     * @param <K> The key type
     * @param <V> The value type
     * @param entries The initial entries to cache
     * @return A Cachable instance with the given initial entries
     */
    static <K, V> CachableImpl<K, V> of(Map<K, V> entries) {
        CachableImpl<K, V> cachable = new CachableImpl<>();
        cachable.cacheAll(entries);

        return cachable;
    }

    /**
     * Creates and returns a Cachable instance initialized with the given entries.
     *
     * @param <K> The key type
     * @param <V> The value type
     * @param entries The initial entries to cache
     * @return A Cachable instance with the given initial entries
     */
    @SafeVarargs
    static <K, V> CachableImpl<K, V> of(Tuple<K, V>... entries) {
        return Cachable.of(Arrays.asList(entries));
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the given key, or null if the key is not present in the cache.
     */
    V val(K key);

    /**
     * Retrieves the key associated with the given value.
     *
     * @param val The value whose associated key is to be returned.
     * @return The key associated with the given value, or null if the value is not present in the cache.
     */
    K key(V val);

    /**
     * Returns true if the cachable contains a key
     * @param key The key to check for
     * @return true if exists, false otherwise
     */
    boolean hasKey(K key);

    /**
     * Returns true if the cachable contains a value
     * @param val The val to check for
     * @return true if exists, false otherwise
     */
    boolean hasVal(V val);

    /**
     * Caches the specified key-value pair.
     *
     * @param key The key to be cached.
     * @param val The value to be cached.
     */
    void cache(K key, V val);

    /**
     * Removes the specified key-value pair from the cache.
     *
     * @param key The key to be removed.
     * @param val The value to be removed.
     */
    void del(K key, V val);

    /**
     * Removes the entry associated with the specified key from the cache.
     *
     * @param key The key to be removed.
     */
    void del(K key);

    /**
     * Caches multiple key-value pairs.
     *
     * @param entries A collection of key-value pairs to be cached.
     */
    void cacheAll(Collection<Tuple<K, V>> entries);

    /**
     * Caches a key-value map.
     *
     * @param entries A key-value map to be cached.
     */
    void cacheAll(Map<K, V> entries);

    /**
     * Removes multiple entries from the cache based on the specified keys.
     *
     * @param keys A collection of keys to be removed.
     */
    void delAll(Collection<K> keys);

    /**
     * Performs the given action for each entry in the cache.
     *
     * @param forEach The action to be performed for each entry.
     */
    void forEach(PairConsumer<K, V> forEach);

    /**
     * Returns the number of entries currently cached.
     *
     * @return The number of entries in the cache.
     */
    int cached();

    /**
     * Clears all entries from the cache.
     */
    void clear();

    /**
     * Creates and returns a snapshot of the current state of the cache.
     *
     * @return A snapshot of the cache.
     */
    CachableSnapshot<K, V> snapshot();
}
