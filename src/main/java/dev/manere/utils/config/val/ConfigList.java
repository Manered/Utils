package dev.manere.utils.config.val;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.library.Utils;
import dev.manere.utils.text.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of values in the configuration associated with a specific ConfigVal.
 */
public record ConfigList(@NotNull ConfigVal val) {

    /**
     * Creates a new ConfigList instance with the specified ConfigVal.
     *
     * @param value The ConfigVal associated with this ConfigList.
     * @return A new ConfigList instance.
     */
    public static @NotNull ConfigList list(@NotNull ConfigVal value) {
        return new ConfigList(value);
    }

    /**
     * Retrieves a list of ItemStacks from the configuration.
     *
     * @return A list of ItemStacks.
     */
    @SuppressWarnings("unchecked")
    public List<ItemStack> itemStacks() {
        return (List<ItemStack>) Utils.plugin().getConfig().getList(this.val.path);
    }

    /**
     * Converts the list of ItemStacks to a list of ItemBuilders.
     *
     * @return A list of ItemBuilders.
     */
    public List<ItemBuilder> itemBuilders() {
        List<ItemBuilder> itemBuilders = new ArrayList<>();

        for (ItemStack itemStack : itemStacks()) {
            itemBuilders.add(ItemBuilder.item(itemStack));
        }

        return itemBuilders;
    }

    /**
     * Converts the list of strings from the configuration to a list of Text objects.
     *
     * @return A list of Text objects.
     */
    public List<Text> texts() {
        List<Text> texts = new ArrayList<>();

        for (String string : Utils.plugin().getConfig().getStringList(this.val.path)) {
            texts.add(Text.text(string));
        }

        return texts;
    }

    /**
     * Converts the list of Text objects to a list of Kyori Adventure Components.
     *
     * @return A list of Kyori Adventure Components.
     */
    public List<Component> components() {
        List<Component> components = new ArrayList<>();

        for (Text text : texts()) {
            components.add(text.component());
        }

        return components;
    }
}
