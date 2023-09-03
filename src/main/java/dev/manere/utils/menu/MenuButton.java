package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.listeners.MenuButtonListener;

public class MenuButton {
    private final ItemBuilder item;
    private MenuButtonListener listener;

    public MenuButton(ItemBuilder item) {
        this.item = item;
    }

    public MenuButton withListener(MenuButtonListener listener) {
        this.listener = listener;
        return this;
    }

    public ItemBuilder getItem() {
        return item;
    }

    public MenuButtonListener getListener() {
        return listener;
    }
}
