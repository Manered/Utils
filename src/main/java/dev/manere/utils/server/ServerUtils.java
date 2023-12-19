package dev.manere.utils.server;

import dev.manere.utils.library.Utils;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.structure.StructureManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The ServerUtils class provides utility methods for accessing server-related components.
 */
public class ServerUtils {
    /**
     * Retrieves the PluginManager associated with the server.
     *
     * @return The PluginManager of the server.
     */
    public static @NotNull PluginManager manager() {
        return Utils.plugin().getServer().getPluginManager();
    }

    /**
     * Retrieves the StructureManager associated with the server.
     *
     * @return The StructureManager of the server.
     */
    public static @NotNull StructureManager structures() {
        return Utils.plugin().getServer().getStructureManager();
    }

    /**
     * Retrieves the ScoreboardManager associated with the server.
     *
     * @return The ScoreboardManager of the server.
     */
    public static @NotNull ScoreboardManager scoreboards() {
        return Utils.plugin().getServer().getScoreboardManager();
    }

    /**
     * Retrieves the BukkitScheduler associated with the server.
     *
     * @return The BukkitScheduler of the server.
     */
    public static @NotNull BukkitScheduler scheduler() {
        return Utils.plugin().getServer().getScheduler();
    }

    /**
     * Retrieves the ServicesManager associated with the server.
     *
     * @return The ServicesManager of the server.
     */
    public static @NotNull ServicesManager services() {
        return Utils.plugin().getServer().getServicesManager();
    }

    /**
     * Gets a list of all online players.
     *
     * @return List of online Player objects.
     */
    public static @NotNull List<Player> online() {
        return new ArrayList<>(Utils.plugin().getServer().getOnlinePlayers());
    }

    /**
     * Gets the number of online players.
     *
     * @return Number of online players.
     */
    public static int playerCount() {
        return online().size();
    }

    /**
     * Gets a plugin from a name.
     *
     * @param name Name of any plugin
     * @return The plugin that is registered under {@code name}
     */
    public static Plugin plugin(@NotNull String name) {
        return Utils.plugin().getServer().getPluginManager().getPlugin(name);
    }

    /**
     * Gets a plugin's {@link org.bukkit.Server} from a name.
     *
     * @param name Name of any plugin
     * @return The {@link org.bukkit.Server} of the plugin registered under {@code name}
     */
    public static Server server(@NotNull String name) {
        return plugin(name).getServer();
    }

    /**
     * Gets a Server instance of the default plugin.
     * @return The {@link org.bukkit.Server} instance of the default plugin.
     */
    public static @NotNull Server server() {
        return Utils.plugin().getServer();
    }

    /**
     * Gets the data folder associated with the default plugin.
     *
     * @return The data folder associated with the default plugin.
     */
    public static @NotNull File dataFolder() {
        return Utils.plugin().getDataFolder();
    }

    /**
     * Gets the data folder associated with a specific plugin.
     *
     * @param plugin The plugin to retrieve the data folder from.
     * @return The data folder associated with a specific plugin.
     */
    public static @NotNull File dataFolder(@NotNull JavaPlugin plugin) {
        return plugin.getDataFolder();
    }

    /**
     * Gets the data folder associated with a specific plugin.
     *
     * @param plugin The plugin to retrieve the data folder from.
     * @return The data folder associated with a specific plugin.
     */
    public static @NotNull File dataFolder(@NotNull Plugin plugin) {
        return plugin.getDataFolder();
    }

    /**
     * Gets the configuration file associated with the default plugin.
     *
     * @return The configuration file associated with the default plugin
     */
    public static @NotNull FileConfiguration config() {
        return Utils.plugin().getConfig();
    }
}