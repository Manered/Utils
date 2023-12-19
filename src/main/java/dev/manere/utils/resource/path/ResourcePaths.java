package dev.manere.utils.resource.path;

import dev.manere.utils.library.Utils;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Utility class for obtaining common ResourcePath instances.
 */
public class ResourcePaths {
    /**
     * Retrieves a ResourcePath instance representing the plugin's data folder path.
     *
     * @return A ResourcePath instance for the plugin's data folder.
     */
    public static ResourcePath plugin() {
        return ResourcePath.path(Utils.plugin().getDataFolder().getPath());
    }

    /**
     * Retrieves a ResourcePath instance representing a plugin's data folder path.
     *
     * @param plugin The JavaPlugin instance whose data folder path is needed.
     * @return A ResourcePath instance for the specified plugin's data folder.
     */
    public static ResourcePath plugin(JavaPlugin plugin) {
        return ResourcePath.path(plugin.getDataFolder().getPath());
    }

    /**
     * Retrieves a ResourcePath instance representing the root path of the server.
     *
     * @return A ResourcePath instance for the root path.
     */
    public static ResourcePath root() {
        return ResourcePath.path(Utils.plugin()
                .getDataFolder()
                .getPath()
                .replaceAll("plugins/" + Utils.plugin().getName() + "/", ""));
    }
}
