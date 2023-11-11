package dev.manere.utils.player.cache;

import org.bukkit.entity.Player;

/**
 * Represents information used for caching values in the player cache.
 *
 * @param target     The player for whom the cache is created.
 * @param identifier The identifier for the cached value.
 * @param val        The value to be cached.
 */
public record CacheInfo(Player target, CacheKey identifier, Object val) {

    /**
     * Creates a new CacheInfo instance with the specified player, identifier, and value.
     *
     * @param target     The player for whom the cache is created.
     * @param identifier The identifier for the cached value.
     * @param val        The value to be cached.
     * @return A new CacheInfo instance.
     */
    public static CacheInfo of(Player target, CacheKey identifier, Object val) {
        return new CacheInfo(target, identifier, val);
    }
}
