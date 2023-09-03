package dev.manere.utils.menu.listeners;

import dev.manere.utils.menu.MenuBuilder;
import dev.manere.utils.menu.MenuButton;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (event.getInventory().getHolder() instanceof MenuBuilder menu) {

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
