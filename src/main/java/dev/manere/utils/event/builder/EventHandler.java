package dev.manere.utils.event.builder;

import com.google.common.collect.ImmutableList;
import dev.manere.utils.library.Utils;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A class for handling events in a Bukkit plugin.
 *
 * @param <T> The type of event to handle.
 */
public class EventHandler<T extends Event> {
    final List<Object> actionList;
    final Class<T> eventType;
    EventPriority eventPriority;
    boolean ignoreCancelled;

    /**
     * Constructs an EventHandler with the specified EventBuilder.
     *
     * @param builder The EventBuilder containing information about the event.
     */
    EventHandler(@NotNull EventBuilder<T> builder) {
        if (builder.actionList.isEmpty()) {
            throw new UnsupportedOperationException("No actions defined");
        }

        actionList = ImmutableList.copyOf(builder.actionList);
        eventType = builder.eventType;
        eventPriority = EventPriority.NORMAL;
    }

    /**
     * Registers the event handler with the specified JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance to register with.
     * @return The registered EventCallback.
     */
    public @NotNull EventCallback<T> register(@NotNull JavaPlugin plugin) {
        return new EventCallback<>(plugin, this);
    }

    /**
     * Registers the event handler with the default JavaPlugin instance.
     *
     * @return The registered EventCallback.
     */
    public @NotNull EventCallback<T> register() {
        return new EventCallback<>(Utils.plugin(), this);
    }

    /**
     * Sets whether cancelled events should be ignored.
     *
     * @param ignoreCancelled True to ignore cancelled events, false otherwise.
     * @return The modified EventHandler instance.
     */
    public @NotNull EventHandler<T> ignoreCancelled(boolean ignoreCancelled) {
        this.ignoreCancelled = ignoreCancelled;
        return this;
    }

    /**
     * Sets the priority of the event.
     *
     * @param eventPriority The EventPriority to set.
     * @return The modified EventHandler instance.
     */
    public @NotNull EventHandler<T> priority(@NotNull EventPriority eventPriority) {
        this.eventPriority = eventPriority;
        return this;
    }

    /**
     * Sets the priority of the event.
     *
     * @param eventPriority The EventPriority identified via an integer to set.
     * @return The modified EventHandler instance.
     */
    public @NotNull EventHandler<T> priority(int eventPriority) {
        return switch (eventPriority) {
            case 1000, 100, 10, 1 -> priority(EventPriority.MONITOR);
            case 2000, 200, 20, 2 -> priority(EventPriority.HIGHEST);
            case 3000, 300, 30, 3 -> priority(EventPriority.HIGH);
            case 4000, 400, 40, 4 -> priority(EventPriority.NORMAL);
            case 5000, 500, 50, 5 -> priority(EventPriority.LOW);
            case 6000, 600, 60, 6 -> priority(EventPriority.LOWEST);

            default -> this;
        };
    }
}
