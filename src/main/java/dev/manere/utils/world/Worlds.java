package dev.manere.utils.world;

import dev.manere.utils.library.Utils;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A utility class for managing Bukkit worlds and related operations.
 */
public class Worlds {
    /**
     * Retrieves all valid worlds on the server.
     *
     * @return All valid worlds on the server.
     */
    public static @NotNull List<World> worlds() {
        return Utils.plugin().getServer().getWorlds();
    }

    /**
     * Retrieves a Bukkit world by its name.
     *
     * @param name The name of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static @Nullable World world(@NotNull String name) {
        return world(Utils.plugin(), name);
    }

    /**
     * Retrieves a Bukkit world by its UUID.
     *
     * @param uuid   The UUID of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static @Nullable World world(@NotNull UUID uuid) {
        return world(Utils.plugin(), uuid);
    }

    /**
     * Retrieves a Bukkit world by its name.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param name   The name of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static @Nullable World world(@NotNull JavaPlugin plugin, @NotNull String name) {
        return plugin.getServer().getWorld(name);
    }

    /**
     * Retrieves a Bukkit world by its UUID.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param uuid   The UUID of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static @Nullable World world(@NotNull JavaPlugin plugin, @NotNull UUID uuid) {
        return plugin.getServer().getWorld(uuid);
    }

    /**
     * Retrieves the name of a Bukkit world.
     *
     * @param world The Bukkit World instance.
     * @return The name of the world.
     */
    public static @NotNull String name(@NotNull World world) {
        return world.getName();
    }

    /**
     * Retrieves the name of a Bukkit world by its UUID.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param uuid   The UUID of the world.
     * @return The name of the world.
     */
    public static @NotNull String name(@NotNull JavaPlugin plugin, @NotNull UUID uuid) {
        return Objects.requireNonNull(plugin.getServer().getWorld(uuid)).getName();
    }

    /**
     * Checks whether a Bukkit world with the given name exists.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param name   The name of the world to check.
     * @return True if the world exists, false otherwise.
     */
    public static boolean worldExists(@NotNull JavaPlugin plugin, @NotNull String name) {
        return plugin.getServer().getWorld(name) != null;
    }

    /**
     * Creates a new Bukkit world using the provided WorldCreator properties.
     *
     * @param plugin     The JavaPlugin instance representing the plugin.
     * @param properties The WorldCreator instance with desired properties for the new world.
     * @return The newly created Bukkit World instance.
     */
    public static @Nullable World create(@NotNull JavaPlugin plugin, @NotNull WorldCreator properties) {
        return plugin.getServer().createWorld(properties);
    }

    /**
     * Creates a new Bukkit world using the provided WorldCreator properties.
     *
     * @param properties The WorldCreator instance with desired properties for the new world.
     * @return The newly created Bukkit World instance.
     */
    public static World create(WorldCreator properties) {
        return Utils.plugin().getServer().createWorld(properties);
    }

    /**
     * Checks whether two players are in the same Bukkit world.
     *
     * @param playerOne The first Player instance.
     * @param playerTwo The second Player instance.
     * @return True if both players are in the same world, false otherwise.
     */
    public static boolean isSameWorld(@NotNull Player playerOne, @NotNull Player playerTwo) {
        return isSameWorld(playerOne.getWorld(), playerTwo.getWorld());
    }

    /**
     * Checks whether two Bukkit worlds are the same.
     *
     * @param worldOne The first Bukkit World instance.
     * @param worldTwo The second Bukkit World instance.
     * @return True if both worlds have the same name, false otherwise.
     */
    public static boolean isSameWorld(@NotNull World worldOne, @NotNull World worldTwo) {
        return worldOne.getName().equalsIgnoreCase(worldTwo.getName());
    }

    /**
     * Checks whether two world names are the same, ignoring case.
     *
     * @param worldOne The name of the first world.
     * @param worldTwo The name of the second world.
     * @return True if both world names are the same (case-insensitive), false otherwise.
     */
    public static boolean isSameWorld(@NotNull String worldOne, @NotNull String worldTwo) {
        return worldOne.equalsIgnoreCase(worldTwo);
    }
}
