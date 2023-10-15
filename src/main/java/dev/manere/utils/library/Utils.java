package dev.manere.utils.library;

import dev.manere.utils.listener.PlayerDeathByPlayerWithCrystalEvent;
import dev.manere.utils.listener.SpigotEventListener;
import dev.manere.utils.menu.normal.listeners.NormalMenuListener;
import dev.manere.utils.menu.paginated.listeners.PaginatedMenuListener;
import dev.manere.utils.registration.Registrar;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The {@code Utils} class provides utility methods and event registration for the Utils library.
 * If you intend to use the {@link PlayerDeathByPlayerWithCrystalEvent} listener or menu builderS you should instantiate an object of this class.
 * <p></p>
 * To use this utility class, you need to add a {@code public static Utils Utils} variable to your class.
 * In your plugin's {@code onEnable()} method, create an instance of this class using {@code Utils = new Utils(this)}.
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

        Registrar.listener(new SpigotEventListener());
        Registrar.listener(new NormalMenuListener());
        Registrar.listener(new PaginatedMenuListener());
    }

    /**
     * Constructs a new {@code Utils} instance.
     *
     * @param plugin The JavaPlugin instance that will be used.
     */
    public static Utils of(JavaPlugin plugin) {
        return new Utils(plugin);
    }

    /**
     * Returns the JavaPlugin instance that will be used for event registration and more.
     *
     * @return The JavaPlugin instance that will be used for event registration and more.
     */
    public static JavaPlugin getPlugin() {
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
    public void setPlugin(JavaPlugin plugin) {
        Utils.plugin = plugin;
    }
}
