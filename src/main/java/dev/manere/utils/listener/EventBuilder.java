package dev.manere.utils.listener;

import dev.manere.utils.library.Utils;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * The EventBuilder class is used to easily listen to events without
 * making a new class that extends {@link org.bukkit.event.Listener}
 *
 * @param <T> Any subclass of {@link Event}
 */
public class EventBuilder<T extends Event> {

    public final List<Object> actionList;
    public final Class<T> eventType;

    /**
     * Constructs a new EventBuilder with the specified event type.
     *
     * @param eventType The event type to listen to
     */
    EventBuilder(Class<T> eventType) {
        this.actionList = new ArrayList<>();
        this.eventType = eventType;
    }

    /**
     * Adds a new listener to this EventBuilder.
     *
     * @param consumer The event consumer.
     * @return This EventBuilder instance, for chaining methods.
     */
    public EventBuilder<T> listens(Consumer<T> consumer) {
        actionList.add(consumer);
        return this;
    }

    /**
     * Creates a new EventBuilder instance with the specified event type.
     *
     * @param eventType The event type to listen to.
     * @param <T> Any subclass of {@link Event}
     * @return A new EventBuilder instance with the specified event type.
     */
    public static <T extends Event> EventBuilder<T> of(Class<T> eventType) {
        return new EventBuilder<>(eventType);
    }

    /**
     * Builds the EventBuilder.
     *
     * @return A new EventHandler instance for this
     */
    public EventHandler<T> build() {
        return new EventHandler<>(this);
    }

    /**
     * Registers the listeners of this EventBuilder.
     *
     * @param plugin The plugin to use for the listener registration.
     * @return an EventCallback.
     */
    public EventCallback<T> register(JavaPlugin plugin) {
        return build().register(plugin);
    }

    /**
     * Registers the listeners of this EventBuilder with the default plugin
     * which is retrieved with {@link Utils#getPlugin()}
     *
     * @return an EventCallback.
     */
    public EventCallback<T> register() {
        return build().register(Utils.getPlugin());
    }
}
