package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.listeners.MenuButtonListener;

/**
 * The MenuButton class represents a button in a menu with an associated ItemBuilder and listener.
 */
public class MenuButton {
    private final ItemBuilder item;
    private MenuButtonListener listener;

    /**
     * Constructs a new MenuButton with the specified ItemBuilder.
     *
     * @param item The ItemBuilder associated with the button.
     */
    public MenuButton(ItemBuilder item) {
        this.item = item;
    }

    /**
     * Sets a listener for the MenuButton.
     *
     * @param listener The MenuButtonListener to set as the listener.
     * @return The MenuButton instance.
     */
    public MenuButton withListener(MenuButtonListener listener) {
        this.listener = listener;
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
