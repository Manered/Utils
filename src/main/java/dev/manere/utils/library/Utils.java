package dev.manere.utils.library;

import dev.manere.utils.annotation.AutoRegisterHandler;
import dev.manere.utils.event.crystal.impl.SpigotAnchorEventListener;
import dev.manere.utils.event.crystal.impl.SpigotCrystalEventListener;
import dev.manere.utils.menu.listener.MenuListener;
import dev.manere.utils.misc.Versions;
import dev.manere.utils.registration.Registrar;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code Utils} class provides utility methods and event registration for the Utils library.
 */
public class Utils {
    private static JavaPlugin plugin;
    private static Commodore commodore;

    /**
     * Constructs a new {@code Utils} instance.
     *
     * @param plugin The JavaPlugin instance that will be used.
     */
    private Utils(@NotNull JavaPlugin plugin) {
        Utils.plugin = plugin;

        if (Versions.isHigherThanOrEqualTo("1.16")) {
            try {
                Registrar.events(new SpigotAnchorEventListener());
            } catch (Exception ignored) {}
        }

        if (CommodoreProvider.isSupported()) {
            commodore = CommodoreProvider.getCommodore(plugin);
        }

        Registrar.events(new SpigotCrystalEventListener());
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
     * Returns the JavaPlugin instance that will be used for event registration and more.
     *
     * @return The JavaPlugin instance that will be used for event registration and more.
     */
    public static @Nullable Commodore commodore() {
        return commodore;
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
