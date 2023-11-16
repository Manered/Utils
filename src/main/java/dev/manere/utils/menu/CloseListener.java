package dev.manere.utils.menu;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Interface for listening to inventory close events.
 */
public interface CloseListener {

    /**
     * Called when an inventory is closed.
     *
     * @param event The inventory close event.
     */
    void onClose(InventoryCloseEvent event);

}