package dev.manere.utils.server;

import dev.manere.utils.library.Utils;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.structure.StructureManager;

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
    public static PluginManager manager() {
        return Utils.getPlugin().getServer().getPluginManager();
    }

    /**
     * Retrieves the StructureManager associated with the server.
     *
     * @return The StructureManager of the server.
     */
    public static StructureManager structures() {
        return Utils.getPlugin().getServer().getStructureManager();
    }

    /**
     * Retrieves the ScoreboardManager associated with the server.
     *
     * @return The ScoreboardManager of the server.
     */
    public static ScoreboardManager scoreboards() {
        return Utils.getPlugin().getServer().getScoreboardManager();
    }

    /**
     * Retrieves the BukkitScheduler associated with the server.
     *
     * @return The BukkitScheduler of the server.
     */
    public static BukkitScheduler scheduler() {
        return Utils.getPlugin().getServer().getScheduler();
    }

    /**
     * Retrieves the ServicesManager associated with the server.
     *
     * @return The ServicesManager of the server.
     */
    public static ServicesManager services() {
        return Utils.getPlugin().getServer().getServicesManager();
    }

    /**
     * Gets a list of all online players.
     *
     * @return List of online Player objects.
     */
    public static List<Player> online() {
        return new ArrayList<>(Utils.getPlugin().getServer().getOnlinePlayers());
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
    public static Plugin plugin(String name) {
        return Utils.getPlugin().getServer().getPluginManager().getPlugin(name);
    }

    /**
     * Gets a plugin's {@link org.bukkit.Server} from a name.
     *
     * @param name Name of any plugin
     * @return The {@link org.bukkit.Server} of the plugin registered under {@code name}
     */
    public static Server server(String name) {
        return plugin(name).getServer();
    }
}