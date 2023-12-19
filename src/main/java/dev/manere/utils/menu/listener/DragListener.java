package dev.manere.utils.menu.listener;

import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for listening to inventory drag events.
 */
public interface DragListener {
    /**
     * Called when an inventory drag event occurs.
     *
     * @param event The inventory drag event.
     */
    void onDrag(@NotNull InventoryDragEvent event);
}