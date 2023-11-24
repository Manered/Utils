package dev.manere.utils.player.cache;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents information used for caching values in the player cache.
 *
 * @param target     The player for whom the cache is created.
 * @param identifier The identifier for the cached value.
 * @param val        The value to be cached.
 */
public record CacheInfo(@NotNull Player target, @NotNull CacheKey identifier, @Nullable Object val) {

    /**
     * Creates a new CacheInfo instance with the specified player, identifier, and value.
     *
     * @param target     The player for whom the cache is created.
     * @param identifier The identifier for the cached value.
     * @param val        The value to be cached.
     * @return A new CacheInfo instance.
     */
    public static @NotNull CacheInfo of(@NotNull Player target, @NotNull CacheKey identifier, @Nullable Object val) {
        return new CacheInfo(target, identifier, val);
    }
}
