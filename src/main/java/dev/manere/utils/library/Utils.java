package dev.manere.utils.library;

import dev.manere.utils.command.annotation.AutoRegisterHandler;
import dev.manere.utils.event.crystal.SpigotEventListener;
import dev.manere.utils.menu.MenuListener;
import dev.manere.utils.registration.Registrar;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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
    private Utils(@NotNull JavaPlugin plugin) {
        Utils.plugin = plugin;

        Registrar.events(new SpigotEventListener());
        Registrar.events(new MenuListener());

        AutoRegisterHandler.scanAndRegister();
    }

    /**
     * Constructs a new {@code Utils} instance.
     *
     * @param plugin The JavaPlugin instance that will be used.
     */
    public static @NotNull Utils init(@NotNull JavaPlugin plugin) {
        return new Utils(plugin);
    }

    /**
     * Returns the JavaPlugin instance that will be used for event registration and more.
     *
     * @return The JavaPlugin instance that will be used for event registration and more.
     */
    public static @NotNull JavaPlugin plugin() {
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
    public void plugin(@NotNull JavaPlugin plugin) {
        Utils.plugin = plugin;
    }
}
