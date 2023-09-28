package dev.manere.utils.server;

import dev.manere.utils.library.Utils;
import org.bukkit.entity.Player;
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
    public static PluginManager getPluginManager() {
        return Utils.getPlugin().getServer().getPluginManager();
    }

    /**
     * Retrieves the StructureManager associated with the server.
     *
     * @return The StructureManager of the server.
     */
    public static StructureManager getStructureManager() {
        return Utils.getPlugin().getServer().getStructureManager();
    }

    /**
     * Retrieves the ScoreboardManager associated with the server.
     *
     * @return The ScoreboardManager of the server.
     */
    public static ScoreboardManager getScoreboardManager() {
        return Utils.getPlugin().getServer().getScoreboardManager();
    }

    /**
     * Retrieves the BukkitScheduler associated with the server.
     *
     * @return The BukkitScheduler of the server.
     */
    public static BukkitScheduler getScheduler() {
        return Utils.getPlugin().getServer().getScheduler();
    }

    /**
     * Retrieves the ServicesManager associated with the server.
     *
     * @return The ServicesManager of the server.
     */
    public static ServicesManager getServicesManager() {
        return Utils.getPlugin().getServer().getServicesManager();
    }

    /**
     * Gets a list of all online players.
     *
     * @return List of online Player objects.
     */
    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(Utils.getPlugin().getServer().getOnlinePlayers());
    }

    /**
     * Gets the number of online players.
     *
     * @return Number of online players.
     */
    public static int getOnlinePlayersCount() {
        return getOnlinePlayers().size();
    }
}