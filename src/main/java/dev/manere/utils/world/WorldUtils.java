package dev.manere.utils.world;

import dev.manere.utils.library.Utils;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

/**
 * A utility class for managing Bukkit worlds and related operations.
 */
public class WorldUtils {

    /**
     * Retrieves a Bukkit world by its name.
     *
     * @param name The name of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static World world(String name) {
        return world(Utils.getPlugin(), name);
    }

    /**
     * Retrieves a Bukkit world by its UUID.
     *
     * @param uuid   The UUID of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static World world(UUID uuid) {
        return world(Utils.getPlugin(), uuid);
    }

    /**
     * Retrieves a Bukkit world by its name.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param name   The name of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static World world(JavaPlugin plugin, String name) {
        return plugin.getServer().getWorld(name);
    }

    /**
     * Retrieves a Bukkit world by its UUID.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param uuid   The UUID of the world to retrieve.
     * @return The Bukkit World instance, or null if not found.
     */
    public static World world(JavaPlugin plugin, UUID uuid) {
        return plugin.getServer().getWorld(uuid);
    }

    /**
     * Retrieves the name of a Bukkit world.
     *
     * @param world The Bukkit World instance.
     * @return The name of the world.
     */
    public static String worldName(World world) {
        return world.getName();
    }

    /**
     * Retrieves the name of a Bukkit world by its UUID.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param uuid   The UUID of the world.
     * @return The name of the world.
     */
    public static String worldName(JavaPlugin plugin, UUID uuid) {
        return Objects.requireNonNull(plugin.getServer().getWorld(uuid)).getName();
    }

    /**
     * Checks whether a Bukkit world with the given name exists.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param name   The name of the world to check.
     * @return True if the world exists, false otherwise.
     */
    public static boolean worldExists(JavaPlugin plugin, String name) {
        return plugin.getServer().getWorld(name) != null;
    }

    /**
     * Creates a new Bukkit world using the provided WorldCreator properties.
     *
     * @param plugin     The JavaPlugin instance representing the plugin.
     * @param properties The WorldCreator instance with desired properties for the new world.
     * @return The newly created Bukkit World instance.
     */
    public static World create(JavaPlugin plugin, WorldCreator properties) {
        return plugin.getServer().createWorld(properties);
    }
}
