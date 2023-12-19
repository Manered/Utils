package dev.manere.utils.menu.listener;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.menu.paginated.PaginatedMenu;
import dev.manere.utils.menu.paginated.PaginatedSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Map;

/**
 * A listener class for handling click events in menus.
 */
public class MenuListener implements Listener {
    /**
     * Handles click events in menus.
     *
     * @param event The InventoryClickEvent to handle.
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getHolder() instanceof Menu menu) {

            Button button = menu.button(event.getSlot());
            if (button != null) {
                Button.ButtonListener listener = button.listener();
                if (listener != null) {
                    listener.onClick(event);
                }
            }
        }

        if (event.getInventory().getHolder() instanceof PaginatedMenu menu) {
            Button stickyButton = menu.stickyButton(event.getSlot());

            if (menu.currentPageItemEnabled() && event.getSlot() == menu.currentPageSlot()) {
                event.setCancelled(true);
            }

            for (Map.Entry<Integer, ItemBuilder> entry : menu.previousButton().entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    event.setCancelled(true);

                    if (menu.currentPage() > 1) {
                        menu.open(player, menu.currentPage() - 1);
                    }
                }
            }

            for (Map.Entry<Integer, ItemBuilder> entry : menu.nextButton().entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    event.setCancelled(true);

                    if (menu.totalPages() > 1) {
                        menu.open(player, menu.currentPage() + 1);
                    }
                }
            }

            for (Map.Entry<Integer, Button> entry : menu.stickyButtons().entrySet()) {
                if (event.getSlot() == entry.getKey()) {
                    if (stickyButton != null && stickyButton.listener() != null) {
                        stickyButton.listener().onClick(event);
                    }
                }
            }

            for (Map.Entry<PaginatedSlot, Button> entry : menu.buttons().entrySet()) {
                PaginatedSlot slotHolder = entry.getKey();
                if (slotHolder.slot() == event.getSlot() && slotHolder.page() == menu.currentPage()) {
                    Button button = entry.getValue();
                    if (button != null && button.listener() != null) {
                        button.listener().onClick(event);
                    }
                }
            }
        }
    }

    /**
     * Handles close events in menus.
     *
     * @param event The InventoryCloseEvent to handle.
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (event.getInventory().getHolder() instanceof Menu menu) {
            if (menu.onClose() != null) {
                menu.onClose().onClose(event);
            }
        } else if (event.getInventory().getHolder() instanceof PaginatedMenu menu) {
            if (menu.onClose() != null) {
                menu.onClose().onClose(event);
            }
        }
    }

    /**
     * Handles drag events in menus.
     *
     * @param event The InventoryDragEvent to handle.
     */
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getHolder() instanceof Menu menu) {
            if (menu.onDrag() != null) {
                menu.onDrag().onDrag(event);
            }
        } else if (event.getInventory().getHolder() instanceof PaginatedMenu menu) {
            if (menu.onDrag() != null) {
                menu.onDrag().onDrag(event);
            }
        }
    }
}
