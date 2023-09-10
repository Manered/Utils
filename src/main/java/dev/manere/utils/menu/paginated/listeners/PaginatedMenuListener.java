package dev.manere.utils.menu.paginated.listeners;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.MenuButtonListener;
import dev.manere.utils.menu.MenuButton;
import dev.manere.utils.menu.paginated.PageSlotHolder;
import dev.manere.utils.menu.paginated.PaginatedMenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

/**
 * A listener class for handling click events in paginated menus.
 */
public class PaginatedMenuListener implements Listener {

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

        final Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getHolder() instanceof PaginatedMenuBuilder menu) {
            MenuButton button = menu.getButton(new PageSlotHolder(event.getSlot(), menu.getCurrentPage()));

            if (button != null) {
                MenuButtonListener listener = button.getListener();
                if (listener != null) {
                    listener.onClick(event);
                }
            }

            for (Map.Entry<Integer, ItemBuilder> entry : menu.previousButton.entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    event.setCancelled(true);

                    menu.previousPage(player);
                }
            }

            for (Map.Entry<Integer, ItemBuilder> entry : menu.nextButton.entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    event.setCancelled(true);

                    menu.nextPage(player);
                }
            }
        }
    }
}