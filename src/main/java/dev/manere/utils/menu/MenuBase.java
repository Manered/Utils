package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.paginated.PaginatedSlot;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface MenuBase<T> {

    /**
     * Returns T, used for default values and for
     * miscalculation purposes.
     * Should only return PaginatedMenu, Menu or any custom
     * MenuBase implementations.
     *
     * @return The class that implements this.
     */
    T type();

    /**
     * Sets an action to run whenever the menu inventory gets closed.
     *
     * @param onClose Determines what to do when a menu InventoryCloseEvent is fired.
     */
    T onClose(CloseListener onClose);

    /**
     * Sets an action to run whenever a player drags an item in an inventory.
     *
     * @param onDrag Determines what to do when a menu InventoryDragEvent is fired.
     */
    T onDrag(DragListener onDrag);

    /**
     * Sets a button at the specified slot in the menu.
     *
     * @param slot   The slot to set the button.
     * @param button The Button to set at the slot.
     * @return The class that implements this.
     */
    default T button(int slot, Button button) {
        return type();
    }

    /**
     * Sets an item at the specified slot in the menu.
     *
     * @param slot The slot to set the item.
     * @param item The ItemBuilder to set at the slot.
     * @return The class that implements this.
     */
    default T item(int slot, ItemBuilder item) {
        return type();
    }

    /**
     * Gets the size (number of slots) of the menu.
     *
     * @return The size of the menu.
     */
    int size();

    /**
     * Gets the title of the menu.
     *
     * @return The title of the menu.
     */
    Component title();

    /**
     * Opens the menu for a specific player.
     *
     * @param player The player to open the menu for.
     */
    void open(Player player);

    /**
     * Sets a border around the menu using a Button and a pattern.
     *
     * @param borderItem    The Button to use for the border.
     * @param borderPatterns The patterns for the border.
     * @return The class that implements this.
     */
    T border(Button borderItem, String... borderPatterns);

    /**
     * Fills the menu slots with a specified filler object based on a pattern.
     *
     * @param filler  The filler object (Button or ItemBuilder) to fill the slots with.
     * @param pattern The pattern to use for filling.
     * @return The class that implements this.
     */
    T fill(Object filler, String... pattern);

    /**
     * Get the inventory associated with a menu.
     *
     * @return The associated Inventory object.
     */
    Inventory inventory();

    /**
     * Set a button in the menu at a specific location.
     *
     * @param where  The position to place the button.
     * @param button The button to set.
     * @return The class that implements this.
     */
    default T button(PaginatedSlot where, Button button) {
        return type();
    }

    /**
     * Set an item in the menu at a specific location.
     *
     * @param where The position to place the item.
     * @param item  The item to set.
     * @return The class that implements this.
     */
    default T item(PaginatedSlot where, ItemBuilder item) {
        return type();
    }

    /**
     * Open the menu for a player at a specific page.
     *
     * @param player The player to open the menu for.
     * @param page   The page to open.
     */
    default void open(Player player, int page) {
        open(player);
    }
}
