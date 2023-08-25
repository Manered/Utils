package dev.manere.utils.registration;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A utility class for registering event listeners with a Bukkit plugin.
 */
public class ListenerUtils {
    /**
     * Registers a Bukkit event listener with the specified plugin.
     *
     * @param plugin The JavaPlugin instance representing the plugin.
     * @param listener The event listener to register.
     */
    public static void registerListener(JavaPlugin plugin, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
