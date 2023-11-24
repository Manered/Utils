package dev.manere.utils.event;

import dev.manere.utils.library.Utils;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * A generic event listener class for handling events in a Bukkit plugin.
 *
 * @param <T> The type of the event to listen for, which should extend Bukkit's Event class.
 */
public abstract class EventListener<T extends Event> implements Listener, EventExecutor {
    private final Class<T> clazz;
    private final EventPriority priority;
    private final boolean ignoreCancelled;
    private T event;

    /**
     * Constructs an EventListener with the default EventPriority of NORMAL.
     *
     * @param clazz The class of the event to listen for, which should extend Bukkit's Event class.
     */
    public EventListener(@NotNull Class<T> clazz) {
        this(clazz, EventPriority.NORMAL);
    }

    /**
     * Constructs an EventListener with the specified EventPriority.
     *
     * @param clazz    The class of the event to listen for, which should extend Bukkit's Event class.
     * @param priority The priority at which to listen for the event.
     */
    public EventListener(@NotNull Class<T> clazz, @NotNull EventPriority priority) {
        this(clazz, priority, true);
    }

    /**
     * Constructs an EventListener with the specified EventPriority and whether to ignore cancelled events.
     *
     * @param clazz          The class of the event to listen for, which should extend Bukkit's Event class.
     * @param priority       The priority at which to listen for the event.
     * @param ignoreCancelled Whether to ignore cancelled events.
     */
    public EventListener(@NotNull Class<T> clazz, @NotNull EventPriority priority, boolean ignoreCancelled) {
        this.clazz = clazz;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
    }

    /**
     * Executes the event listener when the registered event is called.
     *
     * @param listener The listener that is registered for this event.
     * @param event    The event being called.
     * @throws EventException Thrown if the class of the event does not match the expected event type.
     */
    @Override
    public final void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
        if (!clazz.isInstance(event)) {
            throw new EventException("Field `Class<T> clazz` must be an instance of `org.bukkit.event.Event`");
        }

        this.event = clazz.cast(event);
        this.execute(event());
    }

    /**
     * A method that should be implemented to define the actions to be taken when the event occurs.
     *
     * @param event The event object to be processed.
     */
    protected abstract void execute(@NotNull T event);

    /**
     * Register this event listener with the default plugin.
     */
    public final void register() {
        register(Utils.plugin());
    }

    /**
     * Register this event listener with a specified JavaPlugin.
     *
     * @param plugin The JavaPlugin to register this listener with.
     */
    public final void register(@NotNull JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvent(this.clazz, this, this.priority, this, plugin, this.ignoreCancelled);
    }

    /**
     * Get the class of the event being listened for.
     *
     * @return The class of the event.
     */
    public @NotNull Class<T> clazz() {
        return clazz;
    }

    /**
     * Get the priority at which the event listener is registered.
     *
     * @return The event priority.
     */
    public @NotNull EventPriority priority() {
        return priority;
    }

    /**
     * Check if cancelled events are ignored.
     *
     * @return True if cancelled events are ignored, false otherwise.
     */
    public boolean ignoreCancelled() {
        return ignoreCancelled;
    }

    /**
     * Get the event object that triggered the listener.
     *
     * @return The event object.
     */
    public @NotNull T event() {
        return event;
    }
}
