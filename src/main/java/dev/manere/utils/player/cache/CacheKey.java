package dev.manere.utils.player.cache;

import dev.manere.utils.library.Utils;

/**
 * Represents a key used for identifying cached values in the player cache.
 */
public class CacheKey {
    private final String name;

    /**
     * Creates a new CacheKey instance with the specified name.
     *
     * @param name The name used to create the identifier for the cache key.
     */
    public CacheKey(String name) {
        this.name = Utils.plugin().getName() + name.replaceAll(" ", "_").toUpperCase();
    }

    /**
     * Creates a new CacheKey instance with the specified name.
     *
     * @param name The name used to create the identifier for the cache key.
     * @return A new CacheKey instance.
     */
    public static CacheKey key(String name) {
        return new CacheKey(name);
    }

    /**
     * Gets the identifier generated based on the name.
     *
     * @return The identifier for the cache key.
     */
    public String identifier() {
        return this.name;
    }
}
