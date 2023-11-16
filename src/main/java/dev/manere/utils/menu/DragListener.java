package dev.manere.utils.menu;

import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Interface for listening to inventory drag events.
 */
public interface DragListener {

    /**
     * Called when an inventory drag event occurs.
     *
     * @param event The inventory drag event.
     */
    void onDrag(InventoryDragEvent event);

}