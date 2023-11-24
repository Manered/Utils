package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.paginated.PaginatedSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for working with menus.
 */
public class MenuUtils {

    /**
     * Gets the center slot index for a menu of the given size.
     *
     * @param size the size of the menu
     * @return the center slot index
     */
    public static int getCenter(int size) {
        return size / 2 + (size / 9 % 2 > 0 ? 1 : 5);
    }

    /**
     * Gets the center slot index for a menu of the given size.
     *
     * @param size the size of the menu 
     * @return the center slot index
     */
    public static int getCenterSlot(int size) {
        return getCenter(size) - 1;
    }

    /**
     * Converts a {@link PaginatedSlot} to a regular slot index.
     *
     * @param slot the paginated slot
     * @return the slot index
     */
    public static int paginatedSlotToSlot(@NotNull PaginatedSlot slot) {
        return slot.slot();
    }

    /**
     * Converts a regular slot index to a {@link PaginatedSlot}.
     *
     * @param slot the slot index
     * @return the paginated slot
     */
    public static @NotNull PaginatedSlot slotToPaginatedSlot(int slot) {
        return PaginatedSlot.paginatedSlot(slot, 1);
    }

    /**
     * Converts a {@link PaginatedSlot} to an array containing the slot index and page.
     *
     * @param slot the paginated slot
     * @return an array with the slot index and page
     */
    public static int[] paginatedSlotToArray(@NotNull PaginatedSlot slot) {
        return new int[]{slot.slot(), slot.page()};
    }

    /**
     * Gets the menu contents as a list of {@link ItemBuilder}s.
     *
     * @param menu the menu
     * @return the menu contents
     */
    public static @NotNull List<ItemBuilder> contents(@NotNull MenuBase<?> menu) {
        List<ItemBuilder> result = new ArrayList<>();
        for (ItemStack stack : menu.inventory().getContents()) {
            ItemBuilder item = ItemBuilder.item(stack);
            result.add(item);
        }

        return result;
    }

    /**
     * Clears a menu's entire inventory. This will clear the items/contents, this will not reset
     * any variables like the buttons, etc.
     *
     * @param menu the menu
     */
    public static void clear(@NotNull MenuBase<?> menu) {
        menu.inventory().clear();
    }
}