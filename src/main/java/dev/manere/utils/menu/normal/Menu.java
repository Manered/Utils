package dev.manere.utils.menu.normal;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.listener.CloseListener;
import dev.manere.utils.menu.listener.DragListener;
import dev.manere.utils.scheduler.Schedulers;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The Menu class allows you to create custom menus with buttons and items in Paper/Bukkit/Spigot.
 * <P>
 * You can set buttons, items, borders, and fill patterns within the menu.
 * <P>
 * Menus are displayed to players using the open(Player player) method.
 * <P>
 * This implementation does not support pagination.
 */
public class Menu implements InventoryHolder, MenuBase<Menu> {
    private final Inventory inventory;
    private final Component title;
    private final int size;
    private final Map<Integer, Button> buttons;
    private final Map<Integer, ItemBuilder> items;
    private CloseListener onClose;
    private DragListener onDrag;

    /**
     * Constructs a new Menu with the specified title and size.
     *
     * @param title The title of the menu.
     * @param size  The size (number of slots) of the menu.
     */
    public Menu(@NotNull Component title, int size) {
        this.inventory = Utils.plugin().getServer().createInventory(this, size, title);
        this.title = title;
        this.size = size;
        this.buttons = new HashMap<>();
        this.items = new HashMap<>();
    }

    /**
     * Sets a button at the specified slot in the menu.
     *
     * @param slot   The slot to set the button.
     * @param button The Button to set at the slot.
     * @return The Menu instance.
     */
    @Override
    public @NotNull Menu button(int slot, @NotNull Button button) {
        buttons.put(slot, button);
        this.inventory.setItem(slot, button.item().build());

        return this;
    }

    /**
     * Sets an item at the specified slot in the menu.
     *
     * @param slot The slot to set the item.
     * @param item The ItemBuilder to set at the slot.
     * @return The Menu instance.
     */
    @Override
    public @NotNull Menu item(int slot, @NotNull ItemBuilder item) {
        items.put(slot, item);
        this.inventory.setItem(slot, item.build());

        return this;
    }

    /**
     * Creates a new Menu instance.
     *
     * @return A new Menu instance.
     */
    public static @NotNull Menu menu(@NotNull Component title, int size) {
        return new Menu(title, size);
    }

    /**
     * Creates a new Menu instance.
     *
     * @return A new Menu instance.
     */
    public static @NotNull Menu menu(@NotNull Component title, int width, int height) {
        return menu(title, width*height);
    }

    /**
     * Gets the size (number of slots) of the menu.
     *
     * @return The size of the menu.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Gets the title of the menu.
     *
     * @return The title of the menu.
     */
    @Override
    public @NotNull Component title() {
        return title;
    }

    /**
     * Opens the menu for a specific player.
     *
     * @param player The player to open the menu for.
     */
    @Override
    public void open(@NotNull Player player) {
        player.openInventory(this.inventory);

        for (Button button : buttons.values()) {
            if (button.isRefreshingButton()) {
                Schedulers.builder(task -> {
                    if (player.getOpenInventory().getTopInventory() != getInventory()) {
                        task.cancel();
                        return;
                    }

                    int slot = slotByButton(button);

                    getInventory().clear(slot);
                    getInventory().setItem(slot, button.item().build());
                }).config(config -> {
                    if (button.isRefreshingAsync()) {
                        config.async();
                    } else {
                        config.sync();
                    }

                    config.afterTicks((int) button.refreshDelay());
                    config.everyTicks((int) button.refreshPeriod());
                }).execute();
            }
        }
    }

    /**
     * Sets a border around the menu using a Button and a pattern.
     *
     * @param borderItem    The Button to use for the border.
     * @param borderPatterns The patterns for the border.
     * @return The Menu instance.
     */
    @Override
    public @NotNull Menu border(@NotNull Button borderItem, @NotNull String... borderPatterns) {
        int row = 0;
        for (String borderPattern : borderPatterns) {
            if (row < this.size) {
                String[] rowCharacters = borderPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];

                    if (character.equals("X")) {
                        this.inventory.setItem(col + row * 9, borderItem.item().build().clone());
                        buttons.put(col + row * 9, borderItem);
                    }
                }

                row++;
            }
        }

        return this;
    }

    /**
     * Fills the menu slots with a specified filler object based on a pattern.
     *
     * @param filler  The filler object (Button or ItemBuilder) to fill the slots with.
     * @param pattern The pattern to use for filling.
     * @return The Menu instance.
     */
    @Override
    public @NotNull Menu fill(@NotNull Object filler, @NotNull String... pattern) {
        int row = 0;

        for (String rowPattern : pattern) {
            if (row < size / 9) {
                String[] rowCharacters = rowPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];
                    int slot = col + row * 9;

                    if (character.equals("X")) {
                        if (filler instanceof Button) {
                            // Assignment just so the stupid "return value not used" method doesn't show up
                            Menu builder = button(slot, (Button) filler);
                        } else if (filler instanceof ItemBuilder) {
                            // Assignment just so the stupid "return value not used" method doesn't show up
                            Menu builder = item(slot, (ItemBuilder) filler);
                        } else {
                            int callersLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
                            Utils.plugin().getLogger().log(Level.WARNING, "You can not use Menu.fill(Object filler, String... pattern) with a object different than an ItemBuilder or Button!");
                            Utils.plugin().getLogger().log(Level.WARNING, "    Line: [" + callersLineNumber + "], FileBuilder: [" + Thread.currentThread().getStackTrace()[2].getFileName() + "]");
                        }
                    }
                }

                row++;
            }
        }

        return this;
    }

    /**
     * Gets the ItemBuilder at a specific slot in the menu.
     *
     * @param slot The slot to get the ItemBuilder from.
     * @return The ItemBuilder at the specified slot.
     */
    public @Nullable ItemBuilder item(int slot) {
        return items.get(slot);
    }

    /**
     * Gets the Button at a specific slot in the menu.
     *
     * @param slot The slot to get the Button from.
     * @return The Button at the specified slot.
     */
    public @Nullable Button button(int slot) {
        return buttons.get(slot);
    }

    /**
     * Gets the slot of a Button within the menu.
     *
     * @param button The Button to find the slot for.
     * @return The slot of the Button, or -1 if not found.
     */
    public int slotByButton(@NotNull Button button) {
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * Gets the slot of an ItemBuilder within the menu.
     *
     * @param item The ItemBuilder to find the slot for.
     * @return The slot of the ItemBuilder, or -1 if not found.
     */
    public int slotByItem(@NotNull ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : items.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Retrieves the Bukkit Inventory associated with this Menu.
     *
     * @return The Inventory object.
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Get the inventory associated with this Menu.
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
    public @NotNull Menu type() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Menu onClose(@Nullable CloseListener onClose) {
        this.onClose = onClose;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Menu onDrag(@Nullable DragListener onDrag) {
        this.onDrag = onDrag;
        return this;
    }

    public CloseListener onClose() {
        return onClose;
    }

    public DragListener onDrag() {
        return onDrag;
    }
}