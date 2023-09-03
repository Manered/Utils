package dev.manere.utils.menu;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class MenuBuilder implements InventoryHolder {
    private final Inventory inventory;
    private final String title;
    private final int size;
    private final Map<Integer, MenuButton> buttons;
    private final Map<Integer, ItemBuilder> items;

    public MenuBuilder(String title, int size) {
        this.inventory = Utils.getPlugin().getServer().createInventory(this, size, title);
        this.title = title;
        this.size = size;
        this.buttons = new HashMap<>();
        this.items = new HashMap<>();
    }

    public MenuBuilder setButton(int slot, MenuButton button) {
        buttons.putIfAbsent(slot, button);
        this.inventory.setItem(slot, button.getItem().build());
        return this;
    }

    public MenuBuilder setItem(int slot, ItemBuilder item) {
        items.putIfAbsent(slot, item);
        this.inventory.setItem(slot, item.build());
        return this;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public MenuBuilder setBorder(MenuButton borderItem, String... borderPatterns) {
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

    public MenuBuilder fill(Object filler, String... pattern) {
        int row = 0;

        for (String rowPattern : pattern) {
            if (row < size / 9) {
                String[] rowCharacters = rowPattern.split(" ");

                for (int col = 0; col < rowCharacters.length && col < 9; col++) {
                    String character = rowCharacters[col];
                    int slot = col + row * 9;

                    if (character.equals("X")) {
                        if (filler instanceof MenuButton) {
                            setButton(slot, (MenuButton) filler);
                        } else if (filler instanceof ItemBuilder) {
                            setItem(slot, (ItemBuilder) filler);
                        } else {
                            int callersLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
                            Utils.getPlugin().getLogger().log(Level.WARNING, "You can not use MenuBuilder.fill(Object filler, String... pattern) with a object different than an ItemBuilder or MenuButton!");
                            Utils.getPlugin().getLogger().log(Level.WARNING, "    Line: [" + callersLineNumber + "], File: [" + Thread.currentThread().getStackTrace()[2].getFileName() + "]");
                        }
                    }
                }
                row++;
            }
        }
        return this;
    }

    public ItemBuilder getItem(int slot) {
        return items.get(slot);
    }

    public MenuButton getButton(int slot) {
        return buttons.get(slot);
    }

    public int getSlotByButton(MenuButton button) {
        for (Map.Entry<Integer, MenuButton> entry : buttons.entrySet()) {
            if (entry.getValue() == button) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public int getSlotByItem(ItemBuilder item) {
        for (Map.Entry<Integer, ItemBuilder> entry : items.entrySet()) {
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
