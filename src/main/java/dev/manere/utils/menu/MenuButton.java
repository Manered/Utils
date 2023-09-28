package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import org.bukkit.Material;

/**
 * The MenuButton class represents a button in a menu with an associated ItemBuilder and listener.
 */
public class MenuButton {
    private ItemBuilder item;
    private MenuButtonListener listener;

    /**
     * Constructs a new MenuButton with the specified ItemBuilder.
     */
    public MenuButton() {
        this.item = new ItemBuilder(Material.AIR);
    }

    /**
     * Creates a new MenuButton instance.
     *
     * @return A new MenuButton instance.
     */
    public static MenuButton of() {
        return new MenuButton();
    }

    /**
     * Creates a new MenuButton instance.
     *
     * @param item The ItemBuilder to use for the MenuButton.
     * @return A new MenuButton instance.
     */
    public static MenuButton of(ItemBuilder item) {
        return new MenuButton()
                .setItem(item);
    }

    /**
     * Creates a new MenuButton instance.
     *
     * @param item The ItemBuilder to use for the MenuButton.
     * @param listener The MenuButtonListener to use for the MenuButton.
     * @return A new MenuButton instance.
     */
    public static MenuButton of(ItemBuilder item, MenuButtonListener listener) {
        return new MenuButton()
                .setItem(item)
                .setListener(listener);
    }

    /**
     * Sets a listener for the MenuButton.
     *
     * @param listener The MenuButtonListener to set as the listener.
     * @return The MenuButton instance.
     */
    public MenuButton setListener(MenuButtonListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Sets an ItemBuilder for the MenuButton.
     *
     * @param item The ItemBuilder to set as the item.
     * @return The MenuButton instance.
     */
    public MenuButton setItem(ItemBuilder item) {
        this.item = item;
        return this;
    }

    /**
     * Gets the ItemBuilder associated with the MenuButton.
     *
     * @return The ItemBuilder of the MenuButton.
     */
    public ItemBuilder getItem() {
        return item;
    }

    /**
     * Gets the listener associated with the MenuButton.
     *
     * @return The MenuButtonListener of the MenuButton.
     */
    public MenuButtonListener getListener() {
        return listener;
    }
}
