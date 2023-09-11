package dev.manere.utils.menu.paginated.listeners;

import dev.manere.utils.item.ItemBuilder;
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
            MenuButton stickyButton = menu.getStickyButton(event.getSlot());

            if (menu.currentPageItemEnabled && event.getSlot() == menu.currentPageItemSlot) {
                event.setCancelled(true);
            }

            for (Map.Entry<Integer, ItemBuilder> entry : menu.previousButton.entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    event.setCancelled(true);

                    if (menu.getCurrentPage() > 1) {
                        menu.open(player, menu.getCurrentPage() - 1);
                    }
                }
            }

            for (Map.Entry<Integer, ItemBuilder> entry : menu.nextButton.entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    event.setCancelled(true);

                    if (menu.getTotalPages() > 1) {
                        menu.open(player, menu.getCurrentPage() + 1);
                    }
                }
            }

            for (Map.Entry<Integer, MenuButton> entry : menu.getStickyButtons().entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    if (stickyButton != null && stickyButton.getListener() != null) {
                        stickyButton.getListener().onClick(event);
                    }
                }
            }

            for (Map.Entry<PageSlotHolder, MenuButton> entry : menu.getButtons().entrySet()) {
                PageSlotHolder slotHolder = entry.getKey();
                if (slotHolder.getSlot() == event.getSlot() && slotHolder.getPage() == menu.getCurrentPage()) {
                    MenuButton button = entry.getValue();
                    if (button != null && button.getListener() != null) {
                        button.getListener().onClick(event);
                    }
                }
            }
        }
    }
}