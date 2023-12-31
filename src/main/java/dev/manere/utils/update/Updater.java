package dev.manere.utils.update;

import dev.manere.utils.library.Utils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * A utility class for checking and fetching the latest version of a plugin from SpigotMCs API.
 */
@SuppressWarnings("UnstableApiUsage") /* I sure do love the Paper API */
public class Updater {
    /**
     * Fetches the latest version of the plugin from the SpigotMC API.
     *
     * @param plugin     The JavaPlugin instance.
     * @param resourceId The resource ID of the plugin on SpigotMC.
     * @return The latest version of the plugin, or null if an error occurred.
     */
    public static String latestVersion(@NotNull JavaPlugin plugin, int resourceId) {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
             Scanner scanner = new Scanner(inputStream))
        {
            if (scanner.hasNext()) return scanner.next();
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
    public static boolean isOutdated(@NotNull JavaPlugin plugin, int resourceId) {
        String latestVersion = latestVersion(plugin, resourceId);

        if (latestVersion != null) {
            String currentVersion = plugin.getPluginMeta().getVersion();

            return !currentVersion.equalsIgnoreCase(latestVersion);
        }

        return false;
    }

    /**
     * Checks for updates regularly on an interval.
     *
     * @param plugin               the JavaPlugin instance
     * @param resourceId           the resource ID of the plugin on SpigotMC
     * @param every                the interval in ticks to check for updates
     * @param callback             the callback function to execute when an update is found
     */
    public static void scheduledUpdateChecker(@NotNull JavaPlugin plugin, int resourceId, long every, @NotNull UpdateCallback callback) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (isOutdated(plugin, resourceId)) {
                callback.onUpdate();
            }
        }, 0L, every);
    }

    /**
     * Fetches the latest version of the plugin from the SpigotMC API.
     *
     * @param resourceId The resource ID of the plugin on SpigotMC.
     * @return The latest version of the plugin, or null if an error occurred.
     */
    public static String latestVersion(int resourceId) {
        @NotNull JavaPlugin plugin = Utils.plugin();

        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
             Scanner scanner = new Scanner(inputStream))
        {
            if (scanner.hasNext()) return scanner.next();
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to fetch latest version: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging.
        }

        return null;
    }

    /**
     * Checks if the current plugin version is outdated.
     *
     * @param resourceId The resource ID of the plugin on SpigotMC.
     * @return True if the plugin is outdated, false otherwise.
     */
    public static boolean isOutdated(int resourceId) {
        @NotNull JavaPlugin plugin = Utils.plugin();
        String latestVersion = latestVersion(resourceId);

        if (latestVersion != null) {
            String currentVersion = plugin.getPluginMeta().getVersion();

            return !currentVersion.equalsIgnoreCase(latestVersion);
        }

        return false;
    }

    /**
     * Checks for updates regularly on an interval.
     *
     * @param resourceId the resource ID of the plugin on SpigotMC
     * @param every      the interval in ticks to check for updates
     * @param callback   the callback function to execute when an update is found
     */
    public static void scheduledUpdateChecker(int resourceId, long every, @NotNull UpdateCallback callback) {
        @NotNull JavaPlugin plugin = Utils.plugin();

        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (isOutdated(resourceId)) {
                callback.onUpdate();
            }
        }, 0L, every);
    }

    /**
     * The callback interface for update notifications.
     */
    public interface UpdateCallback {
        void onUpdate();
    }
}
