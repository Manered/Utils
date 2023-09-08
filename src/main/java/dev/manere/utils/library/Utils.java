package dev.manere.utils.library;

import dev.manere.utils.command.CommandBuilder;
import dev.manere.utils.listener.PlayerDeathByPlayerWithCrystalEvent;
import dev.manere.utils.listener.SpigotEventListener;
import dev.manere.utils.menu.listeners.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The {@code Utils} class provides utility methods and event registration for the Manere plugin.
 * If you intend to use the {@link PlayerDeathByPlayerWithCrystalEvent} listener or {@link CommandBuilder} menu builder you should instantiate an object of this class.
 * <p></p>
 * To use this utility class, you need to add a {@code public static Utils yetAnotherManeredUtility} variable to your class.
 * In your plugin's {@code onEnable()} method, create an instance of this class using {@code yetAnotherManeredUtility = new Utils(this)}.
 */
public class Utils {

    public static JavaPlugin plugin;

    /**
     * Constructs a new {@code Utils} instance.
     *
     * @param plugin The JavaPlugin instance that will be used for event registration.
     */
    public Utils(JavaPlugin plugin) {
        Utils.plugin = plugin;

        // Register Spigot event listener
        plugin.getServer().getPluginManager().registerEvents(new SpigotEventListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
