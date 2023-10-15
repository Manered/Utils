package dev.manere.utils.menu.paginated;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.menu.MenuButton;
import dev.manere.utils.scheduler.builder.SchedulerBuilder;
import dev.manere.utils.scheduler.builder.TaskType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * A {@link PaginatedMenuBuilder} class for creating paginated menus in Bukkit/Spigot.
 * This class allows you to build and manage paginated menus with customizable buttons,
 * items, and pagination controls.
 */

public class PaginatedMenuBuilder implements InventoryHolder {

    public final Inventory inventory;
    public final Component title;
    public final int size;
    public final Map<PageSlotHolder, MenuButton> buttons;
    public final Map<PageSlotHolder, ItemBuilder> items;
    public int totalPages;
    public int currentPage;
    public final HashMap<Integer, ItemBuilder> previousButton;
    public final HashMap<Integer, ItemBuilder> nextButton;
    public final HashMap<MenuButton, String[]> borderMap;
    public final HashMap<Integer, MenuButton> stickyButtons;
    public boolean currentPageItemEnabled;
    public ItemBuilder currentPageItem;
    public int currentPageSlot;

    /**
     * Constructs a new PaginatedMenuBuilder with the specified title and size.
     *
     * @param title The title of the paginated menu.
     * @param size  The size of the paginated menu (number of slots).
     */
    public PaginatedMenuBuilder(Component title, int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.title = title;
        this.size = size;
        this.buttons = new HashMap<>();
        this.items = new HashMap<>();
        this.previousButton = new HashMap<>();
        this.currentPage = 1;
        this.nextButton = new HashMap<>();
        this.borderMap = new HashMap<>();
        this.stickyButtons = new HashMap<>();
    }

    /**
     * Get the title of the menu.
     *
     * @return The title of the menu.
     */
    public Component getTitle() {
        return title;
    }

    /**
     * Get the size (number of slots) of the menu.
     *
     * @return The size of the menu.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get a map of buttons in the menu.
     *
     * @return A map of buttons and their positions.
     */
    public Map<PageSlotHolder, MenuButton> getButtons() {
        return buttons;
    }

    /**
     * Get a map of previous buttons in the menu.
     *
     * @return A map of previous buttons and their positions.
     */
    public HashMap<Integer, ItemBuilder> getPreviousButton() {
        return previousButton;
    }

    /**
     * Get a map of next buttons in the menu.
     *
     * @return A map of next buttons and their positions.
     */
    public HashMap<Integer, ItemBuilder> getNextButton() {
        return nextButton;
    }

    /**
     * Get a map of border items in the menu.
     *
     * @return A map of border items and their patterns.
     */
    public HashMap<MenuButton, String[]> getBorderMap() {
        return borderMap;
    }

    /**
     * Get a map of sticky buttons in the menu.
     *
     * @return A map of sticky buttons and their positions.
     */
    public HashMap<Integer, MenuButton> getStickyButtons() {
        return stickyButtons;
    }

    /**
     * Get a map of items in the menu.
     *
     * @return A map of items and their positions.
     */
    public Map<PageSlotHolder, ItemBuilder> getItems() {
        return items;
    }

    /**
     * Get the total number of pages in the menu.
     *
     * @return The total number of pages.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Set the total number of pages in the menu.
     *
     * @param totalPages The total number of pages.
     * @return This PaginatedMenuBuilder instance.
     */
    public PaginatedMenuBuilder totalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    /**
     * Get the current page of the menu.
     *
     * @return The current page.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Set a button in the menu at a specific location.
     *
     * @param where  The position to place the button.
     * @param button The button to set.
     * @return This PaginatedMenuBuilder instance.
     */
    public PaginatedMenuBuilder button(PageSlotHolder where, MenuButton button) {
        buttons.put(where, button);

        return this;
    }

    /**
     * Set an item in the menu at a specific location.
     *
     * @param where The position to place the item.
     * @param item  The item to set.
     * @return This PaginatedMenuBuilder instance.
     */
    public PaginatedMenuBuilder item(PageSlotHolder where, ItemBuilder item) {
        items.put(where, item);

        return this;
    }

    /**
     * Creates a new PaginatedMenuBuilder instance.
     *
     * @return A new PaginatedMenuBuilder instance.
     */
    public static PaginatedMenuBuilder of(Component title, int size) {
        return new PaginatedMenuBuilder(title, size);
    }

    /**
     * Creates a new PaginatedMenuBuilder instance.
     *
     * @return A new PaginatedMenuBuilder instance.
     */
    public static PaginatedMenuBuilder of(Component title, int width, int height) {
        return of(title, width*height);
    }

    /**
     * Set a sticky button in the menu at a specific location.
     *
     * @param where  The position to place the sticky button.
     * @param button The sticky button to set.
     * @return This PaginatedMenuBuilder instance.
     */
    public PaginatedMenuBuilder stickyButton(int where, MenuButton button) {
        stickyButtons.put(where, button);

        return this;
    }

    /**
     * Set pagination buttons for navigating the menu.
     *
     * @param previousItemSlot The slot for the previous page button.
     * @param previousItem     The item for the previous page button.
     * @param nextItemSlot     The slot for the next page button.
     * @param nextItem         The item for the next page button.
     * @return This PaginatedMenuBuilder instance.
     */
    public PaginatedMenuBuilder paginationButtons(int previousItemSlot, ItemBuilder previousItem, int nextItemSlot, ItemBuilder nextItem) {
        this.inventory.setItem(previousItemSlot, previousItem.build());
        previousButton.put(previousItemSlot, previousItem);

        this.inventory.setItem(nextItemSlot, nextItem.build());
        nextButton.put(nextItemSlot, nextItem);

        return this;
    }

    public PaginatedMenuBuilder currentPageButton(ItemBuilder currentPageItem) {
        this.currentPageItemEnabled = true;
        this.currentPageItem = currentPageItem;

        return this;
    }

    /**
     * Set a border around the menu with a specific pattern.
     *
     * @param borderItem     The item for the border.
     * @param borderPatterns The pattern for the border.
     * @return This PaginatedMenuBuilder instance.
     */
    public PaginatedMenuBuilder border(MenuButton borderItem, String... borderPatterns) {
        int row = 0;
        for (String borderPattern : borderPatterns) {
            if (row < this.size) {
                String[] rowCharacters = borderPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];

                    if (character.equals("X")) {
                        this.inventory.setItem(col + row * 9, borderItem.getItem().build().clone());
                        borderMap.put(borderItem, borderPatterns);
                        buttons.put(new PageSlotHolder(col + row * 9, currentPage), borderItem);
                    }
                }

                row++;
            }
        }

        return this;
    }

    /**
     * Fill the menu with a specific pattern using a filler item.
     *
     * @param filler  The item to fill the menu with.
     * @param pattern The pattern for filling the menu.
     * @return This PaginatedMenuBuilder instance.
     */
    public PaginatedMenuBuilder fill(Object filler, String... pattern) {
        int row = 0;

        for (String rowPattern : pattern) {
            if (row < size / 9) {
                String[] rowCharacters = rowPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];
                    int slot = col + row * 9;

                    if (character.equals("X")) {
                        if (filler instanceof MenuButton) {
                            button(new PageSlotHolder(slot, currentPage), (MenuButton) filler);
                        } else if (filler instanceof ItemBuilder) {
                            item(new PageSlotHolder(slot, currentPage), (ItemBuilder) filler);
                        } else {
                            int callersLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
                            Utils.getPlugin().getLogger().log(Level.WARNING, "You can not use PaginatedMenuBuilder.fill(Object filler, String... pattern) with a object different than an ItemBuilder or MenuButton!");
                            Utils.getPlugin().getLogger().log(Level.WARNING, "    Line: [" + callersLineNumber + "], File: [" + Thread.currentThread().getStackTrace()[2].getFileName() + "]");
                        }
                    }
                }

                row++;
            }
        }

        return this;
    }

    /**
     * Get the border mapping of buttons and patterns.
     *
     * @return The border mapping.
     */
    public HashMap<MenuButton, String[]> getBorder() {
        return borderMap;
    }

    /**
     * Open the menu for a player at the first page.
     *
     * @param player The player to open the menu for.
     */
    public void open(Player player) {
        open(player, 1);
    }

    /**
     * Open the menu for a player at a specific page.
     *
     * @param player The player to open the menu for.
     * @param page   The page to open.
     */
    @SuppressWarnings("UnstableApiUsage")
    public void open(Player player, int page) {
        this.inventory.clear();

        player.openInventory(this.inventory);

        this.currentPage = page;

        int highestPage = 1;

        for (PageSlotHolder slotHolder : buttons.keySet()) {
            highestPage = Math.max(highestPage, slotHolder.page());
            this.totalPages = highestPage;
        }

        for (PageSlotHolder slotHolder : items.keySet()) {
            if (totalPages > Math.max(highestPage, slotHolder.page())) {
                highestPage = Math.max(highestPage, slotHolder.page());
                this.totalPages = highestPage;
            }
        }

        for (MenuButton button : buttons.values()) {
            if (getPageSlotHolderByButton(button).page() == currentPage) {
                if (!button.isRefreshingButton()) {
                    this.inventory.setItem(getPageSlotHolderByButton(button).slot(), button.getItem().build());
                } else {
                    SchedulerBuilder.of()
                            .type(TaskType.REPEATING)
                            .async(button.isRefreshingAsync())
                            .after(button.getRefreshDelay())
                            .every(button.getRefreshPeriod())
                            .task(task -> {
                                if (player.getOpenInventory().getTopInventory() != getInventory()) {
                                    task.cancel();
                                    return;
                                }

                                if (getPageSlotHolderByButton(button).page() != currentPage) {
                                    task.cancel();
                                    return;
                                }

                                int slot = getPageSlotHolderByButton(button).slot();

                                getInventory().clear(slot);
                                getInventory().setItem(slot, button.getItem().build());

                                player.updateInventory();
                            })
                            .build();
                }
            }
        }

        for (ItemBuilder item : items.values()) {
            if (getPageSlotHolderByItem(item).page() == currentPage) {
                this.inventory.setItem(getPageSlotHolderByItem(item).slot(), item.build());
            }
        }

        if (currentPage > 1) {
            for (Map.Entry<Integer, ItemBuilder> entry : previousButton.entrySet()) {
                this.inventory.setItem(entry.getKey(), previousButton.get(entry.getKey()).build());
            }
        }

        if (currentPage < totalPages) {
            for (Map.Entry<Integer, ItemBuilder> entry : nextButton.entrySet()) {
                this.inventory.setItem(entry.getKey(), nextButton.get(entry.getKey()).build());
            }
        }

        if (!borderMap.isEmpty()) {
            for (Map.Entry<MenuButton, String[]> entry : borderMap.entrySet()) {
                border(entry.getKey(), borderMap.get(entry.getKey()));
            }
        }

        for (Map.Entry<Integer, MenuButton> entry : stickyButtons.entrySet()) {
            this.inventory.setItem(entry.getKey(), stickyButtons.get(entry.getKey()).getItem().build());
        }

        if (totalPages > 1 && currentPageItemEnabled) {
            //noinspection deprecation
            this.inventory.setItem(currentPageSlot,
                    currentPageItem
                            .rawName(currentPageItem.getMeta()
                                    .getDisplayName()
                                    .replaceAll("<current_page>", String.valueOf(currentPage))
                                    .replaceAll("<page>", String.valueOf(currentPage))
                                    .replaceAll("\\{current_page}", String.valueOf(currentPage))
                                    .replaceAll("\\{page}", String.valueOf(currentPage)))
                            .build());
        }

        player.updateInventory();
    }

    /**
     * Open the next page of the menu for a player.
     *
     * @param player The player to open the menu for.
     */
    public void nextPage(Player player) {
        open(player, getCurrentPage() + 1);
    }

    /**
     * Open the previous page of the menu for a player.
     *
     * @param player The player to open the menu for.
     */
    public void previousPage(Player player) {
        open(player, getCurrentPage() - 1);
    }

    /**
     * Get an item at a specific position in the menu.
     *
     * @param where The position to get the item from.
     * @return The item at the specified position.
     */
    public ItemBuilder getItem(PageSlotHolder where) {
        return items.get(where);
    }

    /**
     * Get a button at a specific position in the menu.
     *
     * @param where The position to get the button from.
     * @return The button at the specified position.
     */
    public MenuButton getButton(PageSlotHolder where) {
        return buttons.get(where);
    }

    /**
     * Get a sticky button at a specific position in the menu.
     *
     * @param where The position to get the sticky button from.
     * @return The sticky button at the specified position.
     */
    public MenuButton getStickyButton(int where) {
        return stickyButtons.get(where);
    }

    /**
     * Get the position of a button in the menu.
     *
     * @param button The button to find the position of.
     * @return The position of the button.
     */
    public PageSlotHolder getPageSlotHolderByButton(MenuButton button) {
        for (Map.Entry<PageSlotHolder, MenuButton> entry : buttons.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }

        return null;
    }

    /**
     * Get the position of an item in the menu.
     *
     * @param item The item to find the position of.
     * @return The position of the item.
     */
    public PageSlotHolder getPageSlotHolderByItem(ItemBuilder item) {
        for (Map.Entry<PageSlotHolder, ItemBuilder> entry : items.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }

        return null;
    }

    /**
     * Get the slot of a sticky button in the menu.
     *
     * @param button The sticky button to find the slot of.
     * @return The slot of the sticky button.
     */
    public int getSlotByStickyButton(MenuButton button) {
        for (Map.Entry<Integer, MenuButton> entry : stickyButtons.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * Get the slot of the "Next Page" button in the menu.
     *
     * @param item The "Next Page" button item to find the slot of.
     * @return The slot of the "Next Page" button.
     */
    public int getSlotByNextPageButton(ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : nextButton.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * Get the slot of the "Previous Page" button in the menu.
     *
     * @param item The "Previous Page" button item to find the slot of.
     * @return The slot of the "Previous Page" button.
     */
    public int getSlotByPreviousPageButton(ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : previousButton.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * Get the inventory associated with this PaginatedMenuBuilder.
     *
     * @return The associated Inventory object.
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}