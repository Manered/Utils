package dev.manere.utils.library.event;

import dev.manere.utils.event.builder.EventBuilder;
import dev.manere.utils.event.builder.EventCallback;
import dev.manere.utils.event.builder.EventHandler;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Utility class for simplified event handling in Bukkit plugins.
 */
public class PluginEvents {
    /**
     * Static method to create a new PluginEvents object.
     * @return The newly created PluginEvents object.
     */
    public static PluginEvents handler() {
        return new PluginEvents();
    }

    /**
     * Registers an event with a callback to be executed on event occurrence with normal priority.
     *
     * @param <E>         The type of the event.
     * @param eventClazz  The class object representing the event.
     * @param consumer    The callback to be executed when the event occurs.
     */
    public <E extends Event> void register(@NotNull Class<E> eventClazz, @NotNull Consumer<E> consumer) {
        register(eventClazz, consumer, EventPriority.NORMAL);
    }

    /**
     * Registers an event with a callback to be executed on event occurrence with the specified priority.
     *
     * @param <E>         The type of the event.
     * @param eventClazz  The class object representing the event.
     * @param consumer    The callback to be executed when the event occurs.
     * @param priority    The priority of the event registration.
     */
    public <E extends Event> void register(
            @NotNull Class<E> eventClazz, @NotNull Consumer<E> consumer, @NotNull EventPriority priority
    ) {
        register(eventClazz, consumer, priority, false);
    }

    /**
     * Registers an event with a callback and additional settings.
     *
     * @param <E>             The type of the event.
     * @param eventClazz      The class object representing the event.
     * @param consumer        The callback to be executed when the event occurs.
     * @param priority        The priority of the event registration.
     * @param ignoreCancelled Whether to ignore cancelled events.
     */
    public <E extends Event> void register(
            @NotNull Class<E> eventClazz, @NotNull Consumer<E> consumer, @NotNull EventPriority priority, boolean ignoreCancelled
    ) {
        EventBuilder.event(eventClazz)
                .execute(consumer)
                .build()
                .ignoreCancelled(ignoreCancelled)
                .priority(priority)
                .register();
    }

    /**
     * Unregisters all event listeners associated with the provided listener.
     *
     * @param listener The listener to unregister.
     */
    public void unregister(@NotNull Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    /**
     * Unregisters all event listeners associated with the provided EventBuilder.
     *
     * @param eventBuilder The EventBuilder whose listeners should be unregistered.
     */
    public void unregister(@NotNull EventBuilder<?> eventBuilder) {
        HandlerList.unregisterAll(eventBuilder.build().register());
    }

    /**
     * Unregisters all event listeners associated with the provided EventHandler.
     *
     * @param eventHandler The EventHandler whose listeners should be unregistered.
     */
    public void unregister(@NotNull EventHandler<?> eventHandler) {
        HandlerList.unregisterAll(eventHandler.register());
    }

    /**
     * Unregisters all event listeners associated with the provided EventCallback.
     *
     * @param eventCallback The EventCallback whose listeners should be unregistered.
     */
    public void unregister(@NotNull EventCallback<?> eventCallback) {
        HandlerList.unregisterAll(eventCallback);
    }
}
