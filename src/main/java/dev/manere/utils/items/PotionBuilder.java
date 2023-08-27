package dev.manere.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.function.Consumer;

/**
 * A utility class for building custom potion items with different potion effects.
 */
public class PotionBuilder {

    private final ItemStack item;
    private final PotionMeta potionMeta;

    /**
     * Constructs a PotionBuilder using an existing ItemStack.
     *
     * @param item The ItemStack to be used as a base for the potion.
     * @throws IllegalArgumentException If the provided item's ItemMeta is not an instance of PotionMeta.
     */
    public PotionBuilder(ItemStack item) {
        if (!(item.getItemMeta() instanceof PotionMeta)) {
            throw new IllegalArgumentException("ItemMeta is required to be an instance of PotionMeta to use PotionBuilder!");
        }

        this.item = item;
        this.potionMeta = (PotionMeta) item.getItemMeta();
    }

    /**
     * Constructs a PotionBuilder using a specified material for the potion item.
     *
     * @param material The material to be used for the potion.
     */
    public PotionBuilder(Material material) {
        this(new ItemStack(material));
    }

    /**
     * Sets custom metadata for the potion.
     *
     * @param consumer A consumer that accepts a PotionMeta and applies custom metadata modifications.
     * @return This PotionBuilder instance for method chaining.
     */
    public PotionBuilder setMeta(Consumer<PotionMeta> consumer) {
        consumer.accept(potionMeta);
        return this;
    }

    /**
     * Adds a custom potion effect to the potion.
     *
     * @param effect   The PotionEffect to be added.
     * @param override Whether to override any conflicting effects with the same type.
     * @return This PotionBuilder instance for method chaining.
     */
    public PotionBuilder addEffect(PotionEffect effect, boolean override) {
        potionMeta.addCustomEffect(effect, override);
        return this;
    }

    /**
     * Creates a PotionBuilder instance for a consumable potion item.
     *
     * @return A PotionBuilder instance pre-configured for consumable potions.
     */
    public static PotionBuilder getConsumablePotion() {
        return new PotionBuilder(Material.POTION);
    }

    /**
     * Builds and returns the final potion ItemStack with applied metadata.
     *
     * @return The constructed ItemStack with applied potion metadata.
     */
    public ItemStack build() {
        item.setItemMeta(potionMeta);
        return item;
    }
}
