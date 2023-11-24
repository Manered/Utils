package dev.manere.utils.event.builder;

import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * A utility class for managing event callbacks in a Bukkit plugin.
 * This class provides methods for registering and unregistering event listeners.
 *
 * @param <T> The type of event to handle.
 */
public class EventCallback<T extends Event> implements EventExecutor, Listener {
    public static final HandlerListCache HANDLER_LIST_CACHE = new HandlerListCache();

    public final Class<T> eventType;
    public final EventPriority eventPriority;
    public final boolean ignoredCancelled;
    public final Object[] handlerArray;
    public final AtomicBoolean isRegistered;
    public final JavaPlugin plugin;

    /**
     * Constructs an EventCallback.
     *
     * @param plugin       The JavaPlugin instance associated with this event callback.
     * @param eventHandler The EventHandler containing information about the event.
     */
    public EventCallback(@NotNull JavaPlugin plugin, @NotNull EventHandler<T> eventHandler) {
        this.plugin = plugin;
        this.eventType = eventHandler.eventType;
        this.eventPriority = eventHandler.eventPriority;
        this.ignoredCancelled = eventHandler.ignoreCancelled;
        this.handlerArray = new Object[eventHandler.actionList.size()];
        this.isRegistered = new AtomicBoolean();

        for (int i = 0; i < this.handlerArray.length; i++) {
            this.handlerArray[i] = eventHandler.actionList.get(i);
        }

        register();
    }

    /**
     * Checks if the event callback is registered.
     *
     * @return True if registered, false otherwise.
     */
    public boolean isRegistered() {
        return isRegistered.get();
    }

    /**
     * Gets the priority of the event.
     *
     * @return The EventPriority.
     */
    public @NotNull EventPriority priority() {
        return eventPriority;
    }

    /**
     * Checks if cancelled events are ignored.
     *
     * @return True if ignored, false otherwise.
     */
    public boolean ignoreCancelled() {
        return ignoredCancelled;
    }

    /**
     * Gets the class of the event.
     *
     * @return The Class representing the event type.
     */
    public @NotNull Class<T> eventType() {
        return eventType;
    }

    /**
     * Gets the JavaPlugin associated with this event callback.
     *
     * @return The JavaPlugin instance.
     */
    public @NotNull JavaPlugin plugin() {
        return plugin;
    }

    /**
     * Unregisters the event callback.
     *
     * @return True if successfully unregistered, false if not registered.
     */
    public boolean unregister() {
        if (!isRegistered.getAndSet(false)) {
            return false;
        }

        HANDLER_LIST_CACHE.apply(eventType,this).unregister(this);
        return true;
    }

    /**
     * Registers the event callback.
     */
    public void register() {
        if (isRegistered.getAndSet(true)) {
            return;
        }

        attemptRegistration();
    }

    /**
     * Registers the event callback with a specific method name.
     */
    void attemptRegistration() {
        Bukkit.getPluginManager().registerEvent(this.eventType, this,eventPriority, this,plugin, this.ignoredCancelled);
    }

    /**
     * Executes the event callback.
     *
     * @param listener The event listener.
     * @param event    The event object.
     * @throws EventException If an error occurs during event execution.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
        if (eventType != event.getClass()) {
            return;
        }

        if (!isRegistered.get()) {
            event.getHandlers().unregister(listener);
            return;
        }

        T eventCasted = eventType.cast(event);

        try {
            for (Object o : handlerArray) {
                if (o == null) {
                    continue;
                }

                if (o instanceof Consumer) {
                    ((Consumer<T>) o).accept(eventCasted);
                }
            }
        } catch (ClassCastException exc) {
            throw new EventException(exc);
        }
    }

    /**
     * A cache for storing HandlerLists.
     */
    public static class HandlerListCache implements BiFunction<Class<? extends Event>,EventCallback<?>,HandlerList> {

        public final Map<Class<? extends Event>, HandlerList> cacheMap = new HashMap<>();

        /**
         * Retrieves or creates a HandlerList for a specific event class.
         *
         * @param aClass             The event class.
         * @param eventSubscription  The event callback.
         * @return The HandlerList associated with the event class.
         */
        @Override
        public HandlerList apply(Class<? extends Event> aClass, EventCallback<?> eventSubscription) {
            HandlerList handlerList = cacheMap.get(aClass);
            if (handlerList == null) {
                try {
                    handlerList = cache(aClass);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                } finally {
                    cacheMap.put(aClass,handlerList);
                }
            }
            return handlerList;
        }

        /**
         * Caches the HandlerList for a specific event class.
         *
         * @param aClass The event class.
         * @return The cached HandlerList.
         * @throws ReflectiveOperationException If a reflective operation fails.
         */
        public HandlerList cache(Class<? extends Event> aClass) throws ReflectiveOperationException {
            Method method_getHandlerList = aClass.getMethod("getHandlerList");
            return (HandlerList) method_getHandlerList.invoke(null);
        }
    }
}
