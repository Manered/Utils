package dev.manere.utils.item;

import dev.manere.utils.library.Utils;
import dev.manere.utils.misc.Storable;
import dev.manere.utils.player.PlayerUtils;
import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.serializers.Serializers;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Utility class for building customized {@link ItemStack}s.
 * Provides methods for setting display name, lore, enchantments, etc.
 */
public class ItemBuilder implements Storable<ItemBuilder> {
    private final ItemStack itemStack;

    /**
     * Creates a new ItemBuilder for the given material.
     *
     * @param material The material to create the ItemStack with
     */
    public ItemBuilder(Material material){
        this(material, 1);
    }

    /**
     * Creates a new ItemBuilder using the given ItemStack.
     *
     * @param itemStack The ItemStack to build upon
     */
    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    /**
     * Creates a new ItemBuilder for the given material and amount.
     *
     * @param material The material to create the ItemStack with
     * @param amount The amount of the material
     */
    public ItemBuilder(Material material, int amount){
        itemStack = new ItemStack(material, amount);
    }

    /**
     * Creates a new ItemBuilder using the given ItemStack.
     *
     * @param itemStack The ItemStack to build upon
     */
    public static ItemBuilder item(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    /**
     * Creates a new ItemBuilder for the given material.
     *
     * @param material The material to create the ItemStack with
     */
    public static ItemBuilder item(Material material) {
        return new ItemBuilder(material);
    }

    /**
     * Creates a new ItemBuilder for the given material and amount.
     *
     * @param material The material to create the ItemStack with
     * @param amount The amount of the material
     */
    public static ItemBuilder item(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    /**
     * Creates a clone of this ItemBuilder.
     *
     * @return A cloned instance
     */
    public ItemBuilder clone() {
        try {
            ItemBuilder clone = (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return new ItemBuilder(itemStack);
    }

    /**
     * Sets the display name of the ItemStack.
     *
     * @param name The name to set
     * @return This builder, for chaining
     */
    public ItemBuilder name(Component name) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.displayName(name);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder name(String name) {
        return name(TextStyle.style(name));
    }

    /**
     * Sets the display name of the ItemStack without automatically colorizing it.
     *
     * @param name The name to set
     * @return This builder, for chaining
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder rawName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds an unsafe enchantment to the ItemStack.
     *
     * @param enchantment The enchantment to add
     * @param level       The level of the enchantment
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Removes an enchantment from the ItemStack.
     *
     * @param enchantment The enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Sets the skull owner for a skull ItemStack.
     *
     * @param playerName The name of the skull owner
     * @return This builder, for chaining
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder skullOwner(String playerName){
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        if (itemMeta != null) {
            Schedulers.async().execute(() -> itemMeta.setOwnerProfile(Bukkit.getOfflinePlayer(UUID.fromString(playerName)).getPlayerProfile()));
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the skull owner for a skull ItemStack.
     *
     * @param player The player of the skull owner
     * @return This builder, for chaining
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder skullOwner(Player player) {
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        if (itemMeta != null) {
            Schedulers.async().execute(() -> itemMeta.setOwnerProfile(player.getPlayerProfile()));
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the skull owner for a skull ItemStack.
     *
     * @param player The offline player of the skull owner
     * @return This builder, for chaining
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder skullOwner(OfflinePlayer player) {
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        if (itemMeta != null) {
            player.getPlayerProfile()
                    .update()
                    .thenAcceptAsync(itemMeta::setOwnerProfile,
                            runnable -> Bukkit.getScheduler().runTask(Utils.plugin(), runnable));
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the skull owner for a skull ItemStack.
     *
     * @param uuid The UUID of the skull owner
     * @return This builder, for chaining
     */
    public ItemBuilder skullOwner(UUID uuid) {
        return skullOwner(PlayerUtils.offline(uuid));
    }

    /**
     * Adds an enchantment to the ItemStack.
     *
     * @param enchantment The enchantment to add
     * @param level The level of the enchantment
     * @return This builder, for chaining
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.addEnchant(enchantment, level, true);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds a map of enchantments to the ItemStack.
     *
     * @param enchantments The map of enchantments to add
     * @return This builder, for chaining
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
        itemStack.addEnchantments(enchantments);
        return this;
    }

    /**
     * Sets the lore of the ItemStack.
     *
     * @param lore The list of lore to set
     * @return This builder, for chaining
     */
    public ItemBuilder lore(Component... lore){
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.lore(List.of(lore));
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the lore of the ItemStack.
     *
     * @param lore The list of lore to set
     * @return This builder, for chaining
     */
    public ItemBuilder lore(List<Component> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.lore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the amount of the ItemStack.
     *
     * @param amount The new amount
     * @return This builder, for chaining
     */
    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the durability of the ItemStack.
     *
     * @param damage The durability to set
     * @return This builder, for chaining
     */
    public ItemBuilder durability(int damage) {
        if (itemStack.getItemMeta() instanceof Damageable damageable) {
            damageable.setDamage(damage);
            itemStack.setItemMeta(damageable);
        } else {
            throw new IllegalArgumentException("ItemMeta is required to be an instance of Damageable to set the durability!");
        }

        return this;
    }

    /**
     * Adds an ItemFlag to the ItemStack.
     *
     * @param flag The Item Flag to add
     * @return This builder, for chaining
     */
    public ItemBuilder addFlag(ItemFlag flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.addItemFlags(flag);
            itemStack.setItemMeta(itemMeta);
        }

        return this;
    }

    /**
     * Removes an ItemFlag from the ItemStack.
     *
     * @param flag The Item Flag to remove
     * @return This builder, for chaining
     */
    public ItemBuilder removeFlag(ItemFlag flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.removeItemFlags(flag);
            itemStack.setItemMeta(itemMeta);
        }

        return this;
    }

    /**
     * Adds glow effect to the ItemStack.
     *
     * @return This builder, for chaining
     */
    public ItemBuilder glow() {
        addUnsafeEnchantment(Enchantment.LUCK, 1);
        addFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Removes glow effect from the ItemStack.
     *
     * @return This builder, for chaining
     */
    public ItemBuilder removeGlow() {
        removeEnchantment(Enchantment.LUCK);
        removeFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Sets the ItemStack to be unbreakable.
     *
     * @return This builder, for chaining
     */
    public ItemBuilder unbreakable() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setUnbreakable(true);
        }

        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the ItemStack to be breakable.
     *
     * @return This builder, for chaining
     */
    public ItemBuilder breakable() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setUnbreakable(false);
        }

        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the custom model data of the ItemStack.
     *
     * @param data The custom model data to set
     * @return This builder, for chaining
     */
    public ItemBuilder customModelData(int data) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setCustomModelData(data);
        }

        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the meta of the ItemStack.
     *
     * @param itemMeta The item meta to set the item stack's item meta to
     * @return This builder, for chaining
     */
    public ItemBuilder meta(ItemMeta itemMeta) {
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Gets the {@link ItemMeta} that is being built.
     *
     * @return The ItemMeta
     */
    public ItemMeta meta() {
        return itemStack.getItemMeta();
    }

    /**
     * Gets the {@link PersistentDataContainer} of the ItemStack.
     *
     * @return The PersistentDataContainer
     */
    public PersistentDataContainer pdc() {
        return itemStack.getItemMeta().getPersistentDataContainer();
    }

    /**
     * Edits the ItemMeta of the ItemStack using the provided consumer.
     *
     * @param metaConsumer The consumer to apply modifications to the ItemMeta
     * @return This builder, for chaining
     */
    public ItemBuilder editMeta(Consumer<ItemMeta> metaConsumer) {
        itemStack.editMeta(metaConsumer);
        return this;
    }

    /**
     * Edits the ItemStack using the provided consumer.
     *
     * @param stackConsumer The consumer to apply modifications to the ItemStack
     * @return This builder, for chaining
     */
    public ItemBuilder editStack(Consumer<ItemStack> stackConsumer) {
        stackConsumer.accept(itemStack);
        return this;
    }

    /**
     * Edits the PersistentDataContainer of the ItemStack using the provided consumer.
     *
     * @param pdcConsumer The consumer to apply modifications to the PersistentDataContainer
     * @return This builder, for chaining
     */
    public ItemBuilder editPdc(Consumer<PersistentDataContainer> pdcConsumer) {
        pdcConsumer.accept(itemStack.getItemMeta().getPersistentDataContainer());
        return this;
    }

    /**
     * Creates an ItemBuilder from an existing {@link ItemStack}.
     *
     * @param itemStack The ItemStack to convert to an ItemBuilder
     * @return The converted ItemBuilder
     */
    public ItemBuilder from(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    /**
     * Sets the book-related data for the ItemStack.
     *
     * @param bookMeta The BookMeta containing book-related data
     * @return This builder, for chaining
     */
    public ItemBuilder bookData(BookMeta bookMeta) {
        itemStack.setItemMeta(bookMeta);
        return this;
    }

    /**
     * Sets the banner-related data for the ItemStack.
     *
     * @param bannerMeta The BannerMeta containing banner-related data
     * @return This builder, for chaining
     */
    public ItemBuilder bannerData(BannerMeta bannerMeta) {
        itemStack.setItemMeta(bannerMeta);
        return this;
    }

    /**
     * Sets the color of the leather armor ItemStack.
     *
     * @param color The color to set for the leather armor
     * @return This builder, for chaining
     */
    public ItemBuilder leatherArmorColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();

        if (meta != null) meta.setColor(color);

        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the firework-related data for the ItemStack.
     *
     * @param meta The FireworkMeta containing firework-related data
     * @return This builder, for chaining
     */
    public ItemBuilder fireworkData(FireworkMeta meta) {
        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Adds an attribute to the ItemStack.
     *
     * @param attribute The attribute to add
     * @param modifier The attribute modifier to apply
     * @return This builder, for chaining
     */
    public ItemBuilder addAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) meta.addAttributeModifier(attribute, modifier);

        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Clears all attribute modifiers from the ItemStack.
     *
     * @return This builder, for chaining
     */
    public ItemBuilder clearAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) Objects.requireNonNull(meta.getAttributeModifiers())
                .forEach(meta::removeAttributeModifier);

        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Hides attributes from being displayed on the ItemStack.
     *
     * @return This builder, for chaining
     */
    public ItemBuilder hideAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Builds and returns the final {@link ItemStack}.
     *
     * @return The built ItemStack
     */
    public ItemStack build() {
        return itemStack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize() {
        return Serializers.base64().serializeItemBuilder(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemBuilder deserialize(String serialized) {
        return ItemBuilder.item(Serializers.base64().deserialize(serialized));
    }
}