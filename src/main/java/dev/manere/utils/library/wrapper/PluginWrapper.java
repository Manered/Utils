package dev.manere.utils.library.wrapper;

import dev.manere.utils.command.CommandTypes;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.library.Utils;
import dev.manere.utils.library.event.PluginEvents;
import dev.manere.utils.resource.ResourceFile;
import dev.manere.utils.resource.ResourceOptions;
import dev.manere.utils.resource.format.ResourceFormat;
import dev.manere.utils.resource.format.ResourceFormats;
import dev.manere.utils.resource.path.ResourcePath;
import dev.manere.utils.resource.path.ResourcePaths;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * An abstract base class for Paper/Bukkit/Spigot plugins providing common functionality and structure.
 * Extends {@link JavaPlugin} and implements {@link Listener}.
 * <P></P>
 * This class is intended to be used as a replacement for having to run {@link Utils#init(JavaPlugin)} in your onEnable() method of JavaPlugin.
 * It does that for you.
 * <P></P>
 * Here's the order of the abstract method calling (load(), start(), stop()):
 * <P>
 * 1. load() -> 2. start() -> 3. stop()
 * <P></P>
 * 1. Called when initializing the plugin (before enabling the plugin), before start().
 *    You should avoid calling unsafe methods.
 * <P></P>
 * 2. Called when your plugin gets enabled.
 *    Everything should be safe to execute.
 * <P></P>
 * 3. Called when your plugin is disabling.
 *    Do not run any asynchronous methods.
 *
 * @see Utils
 * @see JavaPlugin
 */
public abstract class PluginWrapper extends JavaPlugin implements Listener {
    private boolean isReloading = false;

    /**
     * Called when initializing the plugin (before enabling the plugin), before start().
     * You should avoid calling unsafe methods.
     */
    protected void load() {}

    /**
     * Called when your plugin gets enabled.
     * Everything should be safe to execute.
     */
    protected abstract void start();

    /**
     * Called when your plugin is disabling.
     * Do not run any asynchronous methods.
     */
    protected void stop() {}

    /**
     * Event handler for server load events. Sets {@code isReloading} to true if the load type is RELOAD.
     *
     * @param event The ServerLoadEvent
     */
    @EventHandler
    public final void onServerLoadEvent(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
            isReloading = true;
        }
    }

    @Override
    public final void onLoad() {
        getServer().getPluginManager().registerEvents(this, this);

        this.load();
    }

    @Override
    public final void onDisable() {
        this.stop();
    }

    @Override
    public final void onEnable() {
        Utils.init(this);

        this.start();
        this.isReloading = false;
    }

    /**
     * Logs a message with the specified priority level.
     *
     * @param priority The priority level
     * @param text     The log message
     * @throws IllegalArgumentException if an invalid priority integer is provided
     */
    public final void log(int priority, @NotNull String text) {
        if (priority >= 0 && priority < 300) {
            getLogger().log(Level.FINEST, text);
        } else if (priority >= 300 && priority < 400) {
            getLogger().log(Level.FINER, text);
        } else if (priority >= 400 && priority < 500) {
            getLogger().log(Level.FINE, text);
        } else if (priority >= 500 && priority < 700) {
            getLogger().log(Level.CONFIG, text);
        } else if (priority >= 700 && priority < 800) {
            getLogger().log(Level.INFO, text);
        } else if (priority >= 800 && priority < 900) {
            getLogger().log(Level.WARNING, text);
        } else if (priority >= 900 && priority < 1000) {
            getLogger().log(Level.SEVERE, text);
        } else {
            throw new IllegalArgumentException(
                    "Invalid priority integer: " + priority + ". Refer to the integer values of java.util.logging.Level"
            );
        }
    }

    /**
     * Logs a message with the specified log level.
     *
     * @param level The log level
     * @param text  The log message
     */
    public final void log(@NotNull Level level, @NotNull String text) {
        getLogger().log(level, text);
    }

    /**
     * Creates a {@link ResourceFile} with the specified name, specified format, specified path, and specified options.
     *
     * @param name    The name of the resource file
     * @param format  The format of the resource file
     * @param path    The path of the resource file
     * @param options The resource file options
     * @return The created ResourceFile
     */
    public final @NotNull ResourceFile resource(String name, ResourceFormat format, ResourcePath path, Consumer<ResourceOptions> options) {
        return ResourceFile.builder()
                .name(name)
                .format(format)
                .path(path)
                .options(options)
                .build();
    }

    /**
     * Creates a {@link ResourceFile} with the specified name, default format, specified path, and specified options.
     *
     * @param name    The name of the resource file
     * @param path    The path of the resource file
     * @param options The resource file options
     * @return The created ResourceFile
     */
    public final @NotNull ResourceFile resource(String name, ResourcePath path, Consumer<ResourceOptions> options) {
        return resource(name, ResourceFormats.yaml(), path, options);
    }

    /**
     * Creates a {@link ResourceFile} with the specified name, default format, specified path, and default options.
     *
     * @param name    The name of the resource file
     * @param path    The path of the resource file
     * @return The created ResourceFile
     */
    public final @NotNull ResourceFile resource(String name, ResourcePath path) {
        return resource(name, ResourceFormats.yaml(), path, options -> {});
    }

    /**
     * Creates a {@link ResourceFile} with the specified name, default format, default path, and default options.
     *
     * @param name    The name of the resource file
     * @return The created ResourceFile
     */
    public final @NotNull ResourceFile resource(String name) {
        return resource(name, ResourceFormats.yaml(), ResourcePaths.plugin(), options -> {});
    }

    /**
     * Create a new Commands with the specified name and default (PLUGIN_YML) type.
     *
     * @param name The name of the command.
     * @return A new Commands instance.
     */
    public final @NotNull Commands command(String name) {
        return Commands.command(name);
    }

    /**
     * Create a new Commands with the specified name and type.
     *
     * @param name The name of the command.
     * @param type The type of the command.
     * @return A new Commands instance.
     */
    public final @NotNull Commands command(String name, CommandTypes type) {
        return Commands.command(name, type);
    }

    /**
     * Returns the Server instance currently running this plugin
     * @return Server running this plugin.
     */
    public final @NotNull Server server() {
        return this.getServer();
    }

    /**
     * Executes a runnable if a player is reloading via the command {@code /bukkit:reload}
     * @param toRunIfTrue The runnable to execute
     */
    public final void ifReloadingRun(@NotNull Runnable toRunIfTrue) {
        if (this.isReloading) toRunIfTrue.run();
    }

    /**
     * Disables this plugin.
     */
    public final void disable() {
        plugins().disablePlugin(this);
    }

    /**
     * Gets the plugin manager for interfacing with plugins.
     * @return a plugin manager for this Server instance
     */
    public final @NotNull PluginManager plugins() {
        return server().getPluginManager();
    }

    /**
     * Gets the event handler of this plugin.
     * @return the event handler of this plugin.
     */
    public final @NotNull PluginEvents events() {
        return new PluginEvents();
    }
}

