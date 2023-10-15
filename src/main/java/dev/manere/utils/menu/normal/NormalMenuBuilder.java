package dev.manere.utils.menu.normal;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.menu.MenuButton;
import dev.manere.utils.scheduler.builder.SchedulerBuilder;
import dev.manere.utils.scheduler.builder.TaskType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The NormalMenuBuilder class allows you to create custom menus with buttons and items in Bukkit/Spigot.
 * You can set buttons, items, borders, and fill patterns within the menu.
 * Menus are displayed to players using the open(Player player) method.
 */
public class NormalMenuBuilder implements InventoryHolder {
    private final Inventory inventory;
    private final Component title;
    private final int size;
    private final Map<Integer, MenuButton> buttons;
    private final Map<Integer, ItemBuilder> items;

    /**
     * Constructs a new NormalMenuBuilder with the specified title and size.
     *
     * @param title The title of the menu.
     * @param size  The size (number of slots) of the menu.
     */
    public NormalMenuBuilder(Component title, int size) {
        this.inventory = Utils.getPlugin().getServer().createInventory(this, size, title);
        this.title = title;
        this.size = size;
        this.buttons = new HashMap<>();
        this.items = new HashMap<>();
    }

    /**
     * Sets a button at the specified slot in the menu.
     *
     * @param slot   The slot to set the button.
     * @param button The MenuButton to set at the slot.
     * @return The NormalMenuBuilder instance.
     */
    public NormalMenuBuilder button(int slot, MenuButton button) {
        buttons.put(slot, button);
        this.inventory.setItem(slot, button.getItem().build());

        return this;
    }

    /**
     * Sets an item at the specified slot in the menu.
     *
     * @param slot The slot to set the item.
     * @param item The ItemBuilder to set at the slot.
     * @return The NormalMenuBuilder instance.
     */
    public NormalMenuBuilder item(int slot, ItemBuilder item) {
        items.put(slot, item);
        this.inventory.setItem(slot, item.build());

        return this;
    }

    /**
     * Creates a new NormalMenuBuilder instance.
     *
     * @return A new NormalMenuBuilder instance.
     */
    public static NormalMenuBuilder of(Component title, int size) {
        return new NormalMenuBuilder(title, size);
    }

    /**
     * Creates a new NormalMenuBuilder instance.
     *
     * @return A new NormalMenuBuilder instance.
     */
    public static NormalMenuBuilder of(Component title, int width, int height) {
        return of(title, width*height);
    }

    /**
     * Gets the size (number of slots) of the menu.
     *
     * @return The size of the menu.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the title of the menu.
     *
     * @return The title of the menu.
     */
    public Component getTitle() {
        return title;
    }

    /**
     * Opens the menu for a specific player.
     *
     * @param player The player to open the menu for.
     */
    public void open(Player player) {
        player.openInventory(this.inventory);

        for (MenuButton button : buttons.values()) {
            if (button.isRefreshingButton()) {
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

                            int slot = getSlotByButton(button);

                            getInventory().clear(slot);
                            getInventory().setItem(slot, button.getItem().build());

                            /* player.updateInventory();
                             * This probably shouldn't be used.
                             */
                        })
                        .build();
            }
        }
    }

    /**
     * Sets a border around the menu using a MenuButton and a pattern.
     *
     * @param borderItem    The MenuButton to use for the border.
     * @param borderPatterns The patterns for the border.
     * @return The NormalMenuBuilder instance.
     */
    public NormalMenuBuilder border(MenuButton borderItem, String... borderPatterns) {
        int row = 0;
        for (String borderPattern : borderPatterns) {
            if (row < this.size) {
                String[] rowCharacters = borderPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];

                    if (character.equals("X")) {
                        this.inventory.setItem(col + row * 9, borderItem.getItem().build().clone());
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
     * @param filler  The filler object (MenuButton or ItemBuilder) to fill the slots with.
     * @param pattern The pattern to use for filling.
     * @return The NormalMenuBuilder instance.
     */
    public NormalMenuBuilder fill(Object filler, String... pattern) {
        int row = 0;

        for (String rowPattern : pattern) {
            if (row < size / 9) {
                String[] rowCharacters = rowPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];
                    int slot = col + row * 9;

                    if (character.equals("X")) {
                        if (filler instanceof MenuButton) {
                            button(slot, (MenuButton) filler);
                        } else if (filler instanceof ItemBuilder) {
                            item(slot, (ItemBuilder) filler);
                        } else {
                            int callersLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
                            Utils.getPlugin().getLogger().log(Level.WARNING, "You can not use NormalMenuBuilder.fill(Object filler, String... pattern) with a object different than an ItemBuilder or MenuButton!");
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
     * Gets the ItemBuilder at a specific slot in the menu.
     *
     * @param slot The slot to get the ItemBuilder from.
     * @return The ItemBuilder at the specified slot.
     */
    public ItemBuilder getItem(int slot) {
        return items.get(slot);
    }

    /**
     * Gets the MenuButton at a specific slot in the menu.
     *
     * @param slot The slot to get the MenuButton from.
     * @return The MenuButton at the specified slot.
     */
    public MenuButton getButton(int slot) {
        return buttons.get(slot);
    }

    /**
     * Gets the slot of a MenuButton within the menu.
     *
     * @param button The MenuButton to find the slot for.
     * @return The slot of the MenuButton, or -1 if not found.
     */
    public int getSlotByButton(MenuButton button) {
        for (Map.Entry<Integer, MenuButton> entry : buttons.entrySet()) {
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
    public int getSlotByItem(ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : items.entrySet()) {
            if (entry.getValue() == item) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Retrieves the Bukkit Inventory associated with this NormalMenuBuilder.
     *
     * @return The Inventory object.
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
