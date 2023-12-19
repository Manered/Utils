package dev.manere.utils.cachable;

import dev.manere.utils.model.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The {@code CachableSnapshot} interface represents a snapshot or view of a cache's current state.
 * It provides methods to access the cached entries as a map, list, or collection of key-value pairs.
 * <P></P>
 * <strong>WARNING: THIS DOES NOT UPDATE! ONCE CREATED YOUR VALUES WILL BE OLD VALUES IF UPDATED AFTER YOU MADE A SNAPSHOT</strong>
 * @param <K> The type of keys in the cache.
 * @param <V> The type of values in the cache.
 */
public interface CachableSnapshot<K, V> {
    /**
     * Returns the cached entries as an unmodifiable map.
     *
     * @return An unmodifiable map view of the cached entries
     */
    Map<K, V> asMap();

    /**
     * Returns the cached entries as an unmodifiable list.
     *
     * @return An unmodifiable list view of the cached entries
     */
    List<Tuple<K, V>> asList();

    /**
     * Returns the cached entries as an unmodifiable collection.
     *
     * @return An unmodifiable collection view of the cached entries
     */
    Collection<Tuple<K, V>> asCollection();

    /**
     * Returns the cached entries as an array of tuples.
     *
     * @return An array view of the cached entries
     */
    Tuple<K, V>[] asTupleArray();
}
