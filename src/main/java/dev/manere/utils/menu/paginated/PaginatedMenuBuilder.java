package dev.manere.utils.menu.paginated;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.MenuButton;
import dev.manere.utils.menu.normal.MenuBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PaginatedMenuBuilder implements InventoryHolder {
    public final Inventory inventory;
    public final String title;
    public final int size;
    public final Map<PageSlotHolder, MenuButton> buttons;
    public final Map<PageSlotHolder, ItemBuilder> items;
    private int totalPages;
    private int currentPage;
    public final HashMap<Integer, ItemBuilder> previousButton;
    public final HashMap<Integer, ItemBuilder> nextButton;

    public PaginatedMenuBuilder(String title, int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.title = title;
        this.size = size;
        this.buttons = new HashMap<>();
        this.items = new HashMap<>();
        this.currentPage = 1;
        this.previousButton = new HashMap<>();
        this.nextButton = new HashMap<>();
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public Map<PageSlotHolder, MenuButton> getButtons() {
        return buttons;
    }

    public Map<PageSlotHolder, ItemBuilder> getItems() {
        return items;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public PaginatedMenuBuilder setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public PaginatedMenuBuilder setButton(PageSlotHolder where, MenuButton button) {
        buttons.putIfAbsent(where, button);

        if (currentPage == where.getPage()) {
            this.inventory.setItem(where.getSlot(), button.getItem().build());
        }

        return this;
    }

    public PaginatedMenuBuilder setItem(PageSlotHolder where, ItemBuilder item) {
        items.putIfAbsent(where, item);

        if (currentPage == where.getPage()) {
            this.inventory.setItem(where.getSlot(), item.build());
        }

        return this;
    }

    public PaginatedMenuBuilder setPaginationButtons(int previousItemSlot, ItemBuilder previousItem, int nextItemSlot, ItemBuilder nextItem) {
        this.inventory.setItem(previousItemSlot, previousItem.build());
        previousButton.put(previousItemSlot, previousItem);

        this.inventory.setItem(nextItemSlot, nextItem.build());
        nextButton.put(nextItemSlot, nextItem);

        return this;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);

        for (MenuButton button : buttons.values()) {
            if (getPageSlotHolderByButton(button).getPage() == 1) {
                this.inventory.setItem(getPageSlotHolderByButton(button).getSlot(), button.getItem().build());
            }
        }

        for (ItemBuilder item : items.values()) {
            if (getPageSlotHolderByItem(item).getPage() == 1) {
                this.inventory.setItem(getPageSlotHolderByItem(item).getSlot(), item.build());
            }
        }

        for (Map.Entry<Integer, ItemBuilder> entry : previousButton.entrySet()) {
            this.inventory.setItem(entry.getKey(), previousButton.get(entry.getKey()).build());
        }

        for (Map.Entry<Integer, ItemBuilder> entry : nextButton.entrySet()) {
            this.inventory.setItem(entry.getKey(), nextButton.get(entry.getKey()).build());
        }
    }

    public void open(Player player, int page) {
        player.openInventory(this.inventory);

        this.currentPage = page;

        for (MenuButton button : buttons.values()) {
            if (getPageSlotHolderByButton(button).getPage() == currentPage) {
                this.inventory.setItem(getPageSlotHolderByButton(button).getSlot(), button.getItem().build());
            }
        }

        for (ItemBuilder item : items.values()) {
            if (getPageSlotHolderByItem(item).getPage() == currentPage) {
                this.inventory.setItem(getPageSlotHolderByItem(item).getSlot(), item.build());
            }
        }

        for (Map.Entry<Integer, ItemBuilder> entry : previousButton.entrySet()) {
            this.inventory.setItem(entry.getKey(), previousButton.get(entry.getKey()).build());
        }

        for (Map.Entry<Integer, ItemBuilder> entry : nextButton.entrySet()) {
            this.inventory.setItem(entry.getKey(), nextButton.get(entry.getKey()).build());
        }
    }

    public void nextPage(Player player) {
        open(player, getCurrentPage() + 1);
    }

    public void previousPage(Player player) {
        open(player, getCurrentPage() - 1);
    }

    public ItemBuilder getItem(PageSlotHolder where) {
        return items.get(where);
    }

    public MenuButton getButton(PageSlotHolder where) {
        return buttons.get(where);
    }

    public PageSlotHolder getPageSlotHolderByButton(MenuButton button) {
        for (Map.Entry<PageSlotHolder, MenuButton> entry : buttons.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }

        return null;
    }

    public PageSlotHolder getPageSlotHolderByItem(ItemBuilder item) {
        for (Map.Entry<PageSlotHolder, ItemBuilder> entry : items.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }

        return null;
    }

    public int getSlotByNextPageButton(ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : nextButton.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }

        return -1;
    }

    public int getSlotByPreviousPageButton(ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : previousButton.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }

        return -1;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
