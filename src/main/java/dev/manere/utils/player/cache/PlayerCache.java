package dev.manere.utils.player.cache;

import dev.manere.utils.library.Utils;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a cache for player-related information using metadata in Bukkit.
 * This class provides methods for caching, retrieving, and checking for specific values.
 *
 * @param target The player for whom the cache is created.
 */
public record PlayerCache(@NotNull Player target) {

    /**
     * Creates a new PlayerCache instance for the specified player.
     *
     * @param player The player to create a cache for.
     * @return A new PlayerCache instance.
     */
    public static @NotNull PlayerCache cacheOf(Player player) {
        return new PlayerCache(player);
    }

    /**
     * Caches a value for the specified player with a given identifier.
     *
     * @param target     The player to cache the value for.
     * @param identifier The identifier for the cached value.
     * @param val        The value to cache.
     * @return True if the value was successfully cached, false otherwise.
     */
    public static boolean cache(@NotNull Player target, @NotNull CacheKey identifier, @Nullable Object val) {
        if (!target.getMetadata(identifier.identifier()).isEmpty()) {
            return false;
        }

        target.getMetadata(identifier.identifier()).add(new FixedMetadataValue(Utils.plugin(), val));
        return true;
    }

    /**
     * Caches a value for the specified player using information from a CacheInfo instance.
     *
     * @param info The CacheInfo instance containing target player, identifier, and value.
     * @return True if the value was successfully cached, false otherwise.
     */
    public static boolean cache(@NotNull CacheInfo info) {
        return cache(info.target(), info.identifier(), info.val());
    }

    /**
     * Retrieves a cached value for the specified player and identifier.
     *
     * @param target     The player to retrieve the cached value for.
     * @param identifier The identifier for the cached value.
     * @return A CacheVal instance representing the cached value.
     */
    public static CacheVal retrieve(@NotNull Player target, @NotNull CacheKey identifier) {
        return CacheVal.cached(target.getMetadata(identifier.identifier()).get(0).value());
    }

    /**
     * Checks if the specified player has a cached value with the given identifier and value.
     *
     * @param target     The player to check for the cached value.
     * @param identifier The identifier for the cached value.
     * @param val        The value to check for.
     * @return True if the player has the specified cached value, false otherwise.
     */
    public static boolean has(@NotNull Player target, @NotNull CacheKey identifier, @Nullable Object val) {
        return target.getMetadata(identifier.identifier()).contains(new FixedMetadataValue(Utils.plugin(), val));
    }

    /**
     * Checks if the specified player has a cached value using information from a CacheInfo instance.
     *
     * @param info The CacheInfo instance containing target player, identifier, and value.
     * @return True if the player has the specified cached value, false otherwise.
     */
    public static boolean has(@NotNull CacheInfo info) {
        return has(info.target(), info.identifier(), info.val());
    }

    /**
     * Caches a value for the current instance's player with a given identifier.
     *
     * @param identifier The identifier for the cached value.
     * @param val        The value to cache.
     * @return True if the value was successfully cached, false otherwise.
     */
    public boolean cache(@NotNull CacheKey identifier, @Nullable Object val) {
        if (!target.getMetadata(identifier.identifier()).isEmpty()) {
            return false;
        }

        target.getMetadata(identifier.identifier()).add(new FixedMetadataValue(Utils.plugin(), val));
        return true;
    }

    /**
     * Retrieves a cached value for the current instance's player and identifier.
     *
     * @param identifier The identifier for the cached value.
     * @return A CacheVal instance representing the cached value.
     */
    public CacheVal retrieve(@NotNull CacheKey identifier) {
        return CacheVal.cached(target.getMetadata(identifier.identifier()).get(0).value());
    }

    /**
     * Checks if the current instance's player has a cached value with the given identifier and value.
     *
     * @param identifier The identifier for the cached value.
     * @param val        The value to check for.
     * @return True if the player has the specified cached value, false otherwise.
     */
    public boolean has(@NotNull CacheKey identifier, @Nullable Object val) {
        return target.getMetadata(identifier.identifier()).contains(new FixedMetadataValue(Utils.plugin(), val));
    }

    /**
     * Deletes a cached value for the specified player and identifier.
     *
     * @param target The player to delete the cached value for.
     * @param identifier The identifier for the cached value to delete.
     */
    public static void delete(@NotNull Player target, @NotNull CacheKey identifier) {
        target.removeMetadata(identifier.identifier(), Utils.plugin());
    }

    /**
     * Deletes a cached value for the current instance's player and identifier.
     *
     * @param identifier The identifier for the cached value to delete.
     */
    public void delete(@NotNull CacheKey identifier) {
        target.removeMetadata(identifier.identifier(), Utils.plugin());
    }
}
