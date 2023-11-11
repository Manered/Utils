package dev.manere.utils.library;

import dev.manere.utils.command.annotation.AutoRegisterHandler;
import dev.manere.utils.event.crystal.SpigotEventListener;
import dev.manere.utils.menu.MenuListener;
import dev.manere.utils.registration.Registrar;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The {@code Utils} class provides utility methods and event registration for the Utils library.
 */
public class Utils {

    public static JavaPlugin plugin;

    /**
     * Constructs a new {@code Utils} instance.
     *
     * @param plugin The JavaPlugin instance that will be used.
     */
    private Utils(JavaPlugin plugin) {
        Utils.plugin = plugin;

        if (Utils.plugin() != null) {
            Registrar.events(new SpigotEventListener());
            Registrar.events(new MenuListener());
        } else {
            throw new NullPointerException("Did you seriously just make the most important part of a library NULL?");
        }

        AutoRegisterHandler.scanAndRegister();
    }

    /**
     * Constructs a new {@code Utils} instance.
     *
     * @param plugin The JavaPlugin instance that will be used.
     */
    public static Utils init(JavaPlugin plugin) {
        return new Utils(plugin);
    }

    /**
     * Returns the JavaPlugin instance that will be used for event registration and more.
     *
     * @return The JavaPlugin instance that will be used for event registration and more.
     */
    public static JavaPlugin plugin() {
        return plugin;
    }

    /**
     * You don't really need to use this.
     * The reason this method exists is that IntelliJ
     * showed "Instantiation of utility class 'Utils'"
     * and I don't think there's any way to suppress it.
     *
     * @param plugin The JavaPlugin instance that will be used.
     */
    public void plugin(JavaPlugin plugin) {
        Utils.plugin = plugin;
    }
}
