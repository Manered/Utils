package dev.manere.utils.menu;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * An interface for handling click events on menu buttons.
 */
public interface MenuButtonListener {
    /**
     * Called when a menu button is clicked.
     *
     * @param event The InventoryClickEvent associated with the button click.
     */
    void onClick(InventoryClickEvent event);
}
