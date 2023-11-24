package dev.manere.utils.event.builder;

import dev.manere.utils.library.Utils;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;

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
    EventBuilder(@NotNull Class<T> eventType) {
        this.actionList = new ArrayList<>();
        this.eventType = eventType;
    }

    /**
     * Adds a new listener to this EventBuilder.
     *
     * @param consumer The event consumer.
     * @return This EventBuilder instance, for chaining methods.
     */
    public @NotNull EventBuilder<T> listener(@NotNull Consumer<T> consumer) {
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
    public static @NotNull <T extends Event> EventBuilder<T> event(@NotNull Class<T> eventType) {
        return new EventBuilder<>(eventType);
    }

    /**
     * Builds the EventBuilder.
     *
     * @return A new EventHandler instance for this
     */
    public @NotNull EventHandler<T> build() {
        return new EventHandler<>(this);
    }

    /**
     * Registers the listeners of this EventBuilder.
     *
     * @param plugin The plugin to use for the listener registration.
     * @return an EventCallback.
     */
    public @NotNull EventCallback<T> register(@NotNull JavaPlugin plugin) {
        return build().register(plugin);
    }

    /**
     * Registers the listeners of this EventBuilder with the default plugin
     * which is retrieved with {@link Utils#plugin()}
     *
     * @return an EventCallback.
     */
    public @NotNull EventCallback<T> register() {
        return build().register(Utils.plugin());
    }
}
