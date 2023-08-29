package dev.manere.utils.update;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * A utility class for checking and fetching the latest version of a plugin from SpigotMCs API.
 */
public class UpdateCheckerUtils {

    /**
     * Fetches the latest version of the plugin from the SpigotMC API.
     *
     * @param plugin     The JavaPlugin instance.
     * @param resourceId The resource ID of the plugin on SpigotMC.
     * @return The latest version of the plugin, or null if an error occurred.
     */
    public static String getLatestVersion(JavaPlugin plugin, int resourceId) {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
             Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                return scanner.next();
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to fetch latest version: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging.
        }
        return null;
    }

    /**
     * Checks if the current plugin version is outdated.
     *
     * @param plugin     The JavaPlugin instance.
     * @param resourceId The resource ID of the plugin on SpigotMC.
     * @return True if the plugin is outdated, false otherwise.
     */
    public static boolean isOutdated(JavaPlugin plugin, int resourceId) {
        String latestVersion = getLatestVersion(plugin, resourceId);
        if (latestVersion != null) {
            String currentVersion = plugin.getDescription().getVersion();
            return !currentVersion.equalsIgnoreCase(latestVersion);
        }
        return false;
    }

    /**
     * Checks for updates regularly on an interval.
     *
     * @param plugin               the JavaPlugin instance
     * @param resourceId           the resource ID of the plugin on SpigotMC
     * @param interval             the interval in minutes to check for updates
     * @param outdatedMessageLevel the log level to use for the outdated message
     * @param outdatedMessage      the message to log when the plugin is outdated
     */
    public static void checkForUpdatesRegularly(JavaPlugin plugin, int resourceId, int interval, Level outdatedMessageLevel, String outdatedMessage) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (isOutdated(plugin, resourceId)) {
                plugin.getLogger().log(outdatedMessageLevel, outdatedMessage);
            }
        }, 0L, interval * 20L);
    }
}
