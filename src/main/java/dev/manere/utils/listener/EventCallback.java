package dev.manere.utils.listener;

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
    static final HandlerListCache HANDLER_LIST_CACHE = new HandlerListCache();

    final Class<T> eventType;
    final EventPriority eventPriority;
    final boolean ignoredCancelled;
    final Object[] handlerArray;
    final AtomicBoolean isRegistered;
    final JavaPlugin plugin;

    /**
     * Constructs an EventCallback.
     *
     * @param plugin       The JavaPlugin instance associated with this event callback.
     * @param eventHandler The EventHandler containing information about the event.
     */
    EventCallback(JavaPlugin plugin, EventHandler<T> eventHandler) {
        this.plugin = plugin;
        eventType = eventHandler.eventType;
        eventPriority = eventHandler.eventPriority;
        ignoredCancelled = eventHandler.ignoreCancelled;
        handlerArray = new Object[eventHandler.actionList.size()];
        isRegistered = new AtomicBoolean();

        for (int i = 0; i < handlerArray.length; i++) {
            handlerArray[i] = eventHandler.actionList.get(i);
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
    public EventPriority getPriority() {
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
    public Class<T> getEventType() {
        return eventType;
    }

    /**
     * Gets the JavaPlugin associated with this event callback.
     *
     * @return The JavaPlugin instance.
     */
    public JavaPlugin getPlugin() {
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
        register("This doesn't actually matter");
    }

    /**
     * Registers the event callback with a specific method name.
     *
     * @param withoutThisTheMethodNameWouldBeRegister0 A dummy parameter (not used).
     */
    void register(String withoutThisTheMethodNameWouldBeRegister0) {
        Bukkit.getPluginManager().registerEvent(eventType,this,eventPriority,this,plugin,ignoredCancelled);
    }

    /**
     * Executes the event callback.
     *
     * @param listener The event listener.
     * @param event    The event object.
     * @throws EventException If an error occurs during event execution.
     */
    @Override
    public void execute(@NotNull Listener listener, Event event) throws EventException {
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
    static class HandlerListCache implements BiFunction<Class<? extends Event>,EventCallback<?>,HandlerList> {

        final Map<Class<? extends Event>, HandlerList> cacheMap = new HashMap<>();

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
        HandlerList cache(Class<? extends Event> aClass) throws ReflectiveOperationException {
            Method method_getHandlerList = aClass.getMethod("getHandlerList");
            return (HandlerList) method_getHandlerList.invoke(null);
        }
    }
}
