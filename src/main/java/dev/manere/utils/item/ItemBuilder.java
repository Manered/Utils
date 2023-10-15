package dev.manere.utils.item;

import dev.manere.utils.library.Utils;
import dev.manere.utils.player.PlayerUtils;
import dev.manere.utils.scheduler.Schedulers;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Utility class for building customized {@link ItemStack}s.
 * Provides methods for setting display name, lore, enchantments, etc.
 */
public class ItemBuilder {

    /** The ItemStack being built */
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
    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    /**
     * Creates a new ItemBuilder for the given material.
     *
     * @param material The material to create the ItemStack with
     */
    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    /**
     * Creates a new ItemBuilder for the given material and amount.
     *
     * @param material The material to create the ItemStack with
     * @param amount The amount of the material
     */
    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    /**
     * Creates a clone of this ItemBuilder.
     *
     * @return A cloned instance
     */
    public ItemBuilder clone(){
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
    public ItemBuilder name(Component name){
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.displayName(name);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the display name of the ItemStack without automatically colorizing it.
     *
     * @param name The name to set
     * @return This builder, for chaining
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
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
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level){
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Removes an enchantment from the ItemStack.
     *
     * @param enchantment The enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment){
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Sets the skull owner for a skull ItemStack.
     *
     * @param playerName The name of the skull owner
     * @return This builder, for chaining
     */
    public ItemBuilder skullOwner(String playerName){
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        if (itemMeta != null) {
            Schedulers.async().now(() -> itemMeta.setOwnerProfile(Bukkit.getOfflinePlayer(UUID.fromString(playerName)).getPlayerProfile()));
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
    public ItemBuilder skullOwner(Player player) {
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        if (itemMeta != null) {
            Schedulers.async().now(() -> itemMeta.setOwnerProfile(player.getPlayerProfile()));
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
    public ItemBuilder skullOwner(OfflinePlayer player) {
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        if (itemMeta != null) {
            player.getPlayerProfile()
                    .update()
                    .thenAcceptAsync(itemMeta::setOwnerProfile,
                            runnable -> Bukkit.getScheduler().runTask(Utils.getPlugin(), runnable));
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
     * @param durability The durability to set
     * @return This builder, for chaining
     */
    public ItemBuilder durability(int durability) {
        if (itemStack.getItemMeta() instanceof Damageable damageable) {
            damageable.setDamage(durability);
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
    public ItemMeta getMeta() {
        return itemStack.getItemMeta();
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

    public ItemBuilder bookData(BookMeta bookMeta) {
        itemStack.setItemMeta(bookMeta);
        return this;
    }

    public ItemBuilder bannerData(BannerMeta bannerMeta) {
        itemStack.setItemMeta(bannerMeta);
        return this;
    }

    public ItemBuilder leatherArmorColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();

        if (meta != null) meta.setColor(color);

        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder fireworkData(FireworkMeta meta) {
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) meta.addAttributeModifier(attribute, modifier);

        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearItemAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) Objects.requireNonNull(meta.getAttributeModifiers())
                .forEach(meta::removeAttributeModifier);

        itemStack.setItemMeta(meta);
        return this;
    }

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
    public ItemStack build(){
        return itemStack;
    }
}