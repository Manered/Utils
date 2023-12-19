package dev.manere.utils.menu.listener;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for listening to inventory close events.
 */
public interface CloseListener {
    /**
     * Called when an inventory is closed.
     *
     * @param event The inventory close event.
     */
    void onClose(@NotNull InventoryCloseEvent event);
}