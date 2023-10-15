package dev.manere.utils.menu.normal.listeners;

import dev.manere.utils.menu.MenuButtonListener;
import dev.manere.utils.menu.normal.NormalMenuBuilder;
import dev.manere.utils.menu.MenuButton;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A listener class for handling click events in menus.
 */
public class NormalMenuListener implements Listener {

    /**
     * Handles click events in menus.
     *
     * @param event The InventoryClickEvent to handle.
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (event.getInventory().getHolder() instanceof NormalMenuBuilder menu) {

            MenuButton button = menu.getButton(event.getSlot());
            if (button != null) {
                MenuButtonListener listener = button.getListener();
                if (listener != null) {
                    listener.onClick(event);
                }
            }
        }
    }
}
