package dev.manere.utils.menu.paginated;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.CloseListener;
import dev.manere.utils.menu.DragListener;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.scheduler.builder.SchedulerBuilder;
import dev.manere.utils.scheduler.builder.TaskType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * A {@link PaginatedMenu} class for creating paginated menus in Bukkit/Spigot.
 * This class allows you to build and manage paginated menus with customizable buttons,
 * items, and pagination controls.
 */

public class PaginatedMenu implements InventoryHolder, MenuBase<PaginatedMenu> {

    public final Inventory inventory;
    public final Component title;
    public final int size;
    public final Map<PaginatedSlot, Button> buttons;
    public final Map<PaginatedSlot, ItemBuilder> items;
    public int totalPages;
    public int currentPage;
    public final Map<Integer, ItemBuilder> previousButton;
    public final Map<Integer, ItemBuilder> nextButton;
    public final Map<Button, String[]> borderMap;
    public final Map<Integer, Button> stickyButtons;
    public boolean currentPageItemEnabled;
    public ItemBuilder currentPageItem;
    public int currentPageSlot;
    public CloseListener onClose;
    public DragListener onDrag;

    /**
     * Constructs a new PaginatedMenu with the specified title and size.
     *
     * @param title The title of the paginated menu.
     * @param size  The size of the paginated menu (number of slots).
     */
    public PaginatedMenu(@NotNull Component title, int size) {
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
    @Override
    public @NotNull Component title() {
        return title;
    }

    /**
     * Get the size (number of slots) of the menu.
     *
     * @return The size of the menu.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get a map of buttons in the menu.
     *
     * @return A map of buttons and their positions.
     */
    public @NotNull Map<PaginatedSlot, Button> buttons() {
        return buttons;
    }

    /**
     * Get a map of previous buttons in the menu.
     *
     * @return A map of previous buttons and their positions.
     */
    public @NotNull Map<Integer, ItemBuilder> previousButton() {
        return previousButton;
    }

    /**
     * Get a map of next buttons in the menu.
     *
     * @return A map of next buttons and their positions.
     */
    public @NotNull Map<Integer, ItemBuilder> nextButton() {
        return nextButton;
    }

    /**
     * Get a map of border items in the menu.
     *
     * @return A map of border items and their patterns.
     */
    public @NotNull Map<Button, String[]> borderMap() {
        return borderMap;
    }

    /**
     * Get a map of sticky buttons in the menu.
     *
     * @return A map of sticky buttons and their positions.
     */
    public @NotNull Map<Integer, Button> stickyButtons() {
        return stickyButtons;
    }

    /**
     * Get a map of items in the menu.
     *
     * @return A map of items and their positions.
     */
    public @NotNull Map<PaginatedSlot, ItemBuilder> items() {
        return items;
    }

    /**
     * Get the total number of pages in the menu.
     *
     * @return The total number of pages.
     */
    public int totalPages() {
        return totalPages;
    }

    /**
     * Set the total number of pages in the menu.
     *
     * @param totalPages The total number of pages.
     * @return This PaginatedMenu instance.
     */
    public @NotNull PaginatedMenu totalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    /**
     * Get the current page of the menu.
     *
     * @return The current page.
     */
    public int currentPage() {
        return currentPage;
    }

    /**
     * Set a button in the menu at a specific location.
     *
     * @param where  The position to place the button.
     * @param button The button to set.
     * @return This PaginatedMenu instance.
     */
    @Override
    public @NotNull PaginatedMenu button(@NotNull PaginatedSlot where, @NotNull Button button) {
        buttons.put(where, button);

        return this;
    }

    /**
     * Set an item in the menu at a specific location.
     *
     * @param where The position to place the item.
     * @param item  The item to set.
     * @return This PaginatedMenu instance.
     */
    @Override
    public @NotNull PaginatedMenu item(@NotNull PaginatedSlot where, @Nullable ItemBuilder item) {
        items.put(where, item);

        return this;
    }

    /**
     * Creates a new PaginatedMenu instance.
     *
     * @return A new PaginatedMenu instance.
     */
    public static @NotNull PaginatedMenu menu(@NotNull Component title, int size) {
        return new PaginatedMenu(title, size);
    }

    /**
     * Creates a new PaginatedMenu instance.
     *
     * @return A new PaginatedMenu instance.
     */
    public static @NotNull PaginatedMenu menu(@NotNull Component title, int width, int height) {
        return menu(title, width*height);
    }

    /**
     * Set a sticky button in the menu at a specific location.
     *
     * @param where  The position to place the sticky button.
     * @param button The sticky button to set.
     * @return This PaginatedMenu instance.
     */
    public @NotNull PaginatedMenu stickyButton(int where, @NotNull Button button) {
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
     * @return This PaginatedMenu instance.
     */
    public @NotNull PaginatedMenu paginationButtons(int previousItemSlot, @NotNull ItemBuilder previousItem, int nextItemSlot, @NotNull ItemBuilder nextItem) {
        this.inventory.setItem(previousItemSlot, previousItem.build());
        previousButton.put(previousItemSlot, previousItem);

        this.inventory.setItem(nextItemSlot, nextItem.build());
        nextButton.put(nextItemSlot, nextItem);

        return this;
    }

    public @NotNull PaginatedMenu currentPageButton(int slot, @NotNull ItemBuilder item) {
        this.currentPageItemEnabled = true;
        this.currentPageItem = item;
        this.currentPageSlot = slot;

        return this;
    }

    /**
     * Set a border around the menu with a specific pattern.
     *
     * @param borderItem     The item for the border.
     * @param borderPatterns The pattern for the border.
     * @return This PaginatedMenu instance.
     */
    @Override
    public @NotNull PaginatedMenu border(@NotNull Button borderItem, @NotNull String... borderPatterns) {
        int row = 0;
        for (String borderPattern : borderPatterns) {
            if (row < this.size) {
                String[] rowCharacters = borderPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];

                    if (character.equals("X")) {
                        this.inventory.setItem(col + row * 9, borderItem.item().build().clone());
                        borderMap.put(borderItem, borderPatterns);
                        buttons.put(new PaginatedSlot(col + row * 9, currentPage), borderItem);
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
     * @return This PaginatedMenu instance.
     */
    @Override
    public @NotNull PaginatedMenu fill(@NotNull Object filler, @NotNull String... pattern) {
        int row = 0;

        for (String rowPattern : pattern) {
            if (row < size / 9) {
                String[] rowCharacters = rowPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];
                    int slot = col + row * 9;

                    if (character.equals("X")) {
                        if (filler instanceof Button) {
                            PaginatedMenu menu = button(new PaginatedSlot(slot, currentPage), (Button) filler);
                        } else if (filler instanceof ItemBuilder) {
                            PaginatedMenu menu = item(new PaginatedSlot(slot, currentPage), (ItemBuilder) filler);
                        } else {
                            int callersLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
                            Utils.plugin().getLogger().log(Level.WARNING, "You can not use PaginatedMenu.fill(Object filler, String... pattern) with a object different than an ItemBuilder or Button!");
                            Utils.plugin().getLogger().log(Level.WARNING, "    Line: [" + callersLineNumber + "], File: [" + Thread.currentThread().getStackTrace()[2].getFileName() + "]");
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
    public @NotNull Map<Button, String[]> border() {
        return borderMap;
    }

    /**
     * Open the menu for a player at the first page.
     *
     * @param player The player to open the menu for.
     */
    @Override
    public void open(@NotNull Player player) {
        open(player, 1);
    }

    /**
     * Open the menu for a player at a specific page.
     *
     * @param player The player to open the menu for.
     * @param page   The page to open.
     */
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void open(@NotNull Player player, int page) {
        this.inventory.clear();

        player.openInventory(this.inventory);

        this.currentPage = page;

        int highestPage = 1;

        for (PaginatedSlot slotHolder : buttons.keySet()) {
            highestPage = Math.max(highestPage, slotHolder.page());
            this.totalPages = highestPage;
        }

        for (PaginatedSlot slotHolder : items.keySet()) {
            if (totalPages > Math.max(highestPage, slotHolder.page())) {
                highestPage = Math.max(highestPage, slotHolder.page());
                this.totalPages = highestPage;
            }
        }

        for (Button button : buttons.values()) {
            if (pageSlotHolderByButton(button).page() == currentPage) {
                if (!button.isRefreshingButton()) {
                    this.inventory.setItem(pageSlotHolderByButton(button).slot(), button.item().build());
                } else {
                    SchedulerBuilder.scheduler()
                            .type(TaskType.REPEATING)
                            .async(button.isRefreshingAsync())
                            .after(button.refreshDelay())
                            .every(button.refreshPeriod())
                            .task(task -> {
                                if (player.getOpenInventory().getTopInventory() != getInventory()) {
                                    task.cancel();
                                    return;
                                }

                                if (pageSlotHolderByButton(button).page() != currentPage) {
                                    task.cancel();
                                    return;
                                }

                                int slot = pageSlotHolderByButton(button).slot();

                                getInventory().clear(slot);
                                getInventory().setItem(slot, button.item().name(button.item().build().displayName()).build());

                                player.updateInventory();
                            })
                            .build();
                }
            }
        }

        for (ItemBuilder item : items.values()) {
            if (pageSlotHolderByItem(item).page() == currentPage) {
                this.inventory.setItem(pageSlotHolderByItem(item).slot(), item.build());
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
            for (Map.Entry<Button, String[]> entry : borderMap.entrySet()) {
                PaginatedMenu menu = border(entry.getKey(), borderMap.get(entry.getKey()));
            }
        }

        for (Map.Entry<Integer, Button> entry : stickyButtons.entrySet()) {
            this.inventory.setItem(entry.getKey(), stickyButtons.get(entry.getKey()).item().build());
        }

        if (totalPages > 1 && currentPageItemEnabled) {
            //noinspection deprecation
            this.inventory.setItem(currentPageSlot,
                    currentPageItem
                            .rawName(currentPageItem.meta()
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
    public void nextPage(@NotNull Player player) {
        open(player, currentPage() + 1);
    }

    /**
     * Open the previous page of the menu for a player.
     *
     * @param player The player to open the menu for.
     */
    public void previousPage(@NotNull Player player) {
        open(player, currentPage() - 1);
    }

    /**
     * Get an item at a specific position in the menu.
     *
     * @param where The position to get the item from.
     * @return The item at the specified position.
     */
    public @Nullable ItemBuilder item(@NotNull PaginatedSlot where) {
        return items.get(where);
    }

    /**
     * Get a button at a specific position in the menu.
     *
     * @param where The position to get the button from.
     * @return The button at the specified position.
     */
    public @Nullable Button button(@NotNull PaginatedSlot where) {
        return buttons.get(where);
    }

    /**
     * Get a sticky button at a specific position in the menu.
     *
     * @param where The position to get the sticky button from.
     * @return The sticky button at the specified position.
     */
    public @Nullable Button stickyButton(int where) {
        return stickyButtons.get(where);
    }

    /**
     * Get the position of a button in the menu.
     *
     * @param button The button to find the position of.
     * @return The position of the button.
     */
    public PaginatedSlot pageSlotHolderByButton(@NotNull Button button) {
        for (Map.Entry<PaginatedSlot, Button> entry : buttons.entrySet()) {
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
    public PaginatedSlot pageSlotHolderByItem(@NotNull ItemBuilder item) {
        for (Map.Entry<PaginatedSlot, ItemBuilder> entry : items.entrySet()) {
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
    public int slotByStickyButton(@NotNull Button button) {
        for (Map.Entry<Integer, Button> entry : stickyButtons.entrySet()) {
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
    public int slotByNextPageButton(@NotNull ItemBuilder item) {
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
    public int slotByPreviousPageButton(@NotNull ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : previousButton.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * Get the inventory associated with this PaginatedMenu.
     *
     * @return The associated Inventory object.
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Get the inventory associated with this PaginatedMenu.
     *
     * @return The associated Inventory object.
     */
    @NotNull
    @Override
    public Inventory inventory() {
        return getInventory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull PaginatedMenu type() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull PaginatedMenu onClose(@Nullable CloseListener onClose) {
        this.onClose = onClose;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull PaginatedMenu onDrag(@Nullable DragListener onDrag) {
        this.onDrag = onDrag;
        return this;
    }
}