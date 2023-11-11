package dev.manere.utils.event.builder;

import com.google.common.collect.ImmutableList;
import dev.manere.utils.library.Utils;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * A class for handling events in a Bukkit plugin.
 *
 * @param <T> The type of event to handle.
 */
public class EventHandler<T extends Event> {
    public final List<Object> actionList;
    public final Class<T> eventType;
    public EventPriority eventPriority;
    public boolean ignoreCancelled;

    /**
     * Constructs an EventHandler with the specified EventBuilder.
     *
     * @param builder The EventBuilder containing information about the event.
     */
    EventHandler(EventBuilder<T> builder) {
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
    public EventCallback<T> register(JavaPlugin plugin) {
        return new EventCallback<>(plugin, this);
    }

    /**
     * Registers the event handler with the default JavaPlugin instance.
     *
     * @return The registered EventCallback.
     */
    public EventCallback<T> register() {
        return new EventCallback<>(Utils.plugin(), this);
    }

    /**
     * Sets whether cancelled events should be ignored.
     *
     * @param ignoreCancelled True to ignore cancelled events, false otherwise.
     * @return The modified EventHandler instance.
     */
    public EventHandler<T> ignoreCancelled(boolean ignoreCancelled) {
        this.ignoreCancelled = ignoreCancelled;
        return this;
    }

    /**
     * Sets the priority of the event.
     *
     * @param eventPriority The EventPriority to set.
     * @return The modified EventHandler instance.
     */
    public EventHandler<T> eventPriority(EventPriority eventPriority) {
        this.eventPriority = eventPriority;
        return this;
    }
}
