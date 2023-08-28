package dev.manere.utils.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

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
    public ItemBuilder setName(String name){
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
    public void addUnsafeEnchantment(Enchantment enchantment, int level){
        itemStack.addUnsafeEnchantment(enchantment, level);
    }

    /**
     * Removes an enchantment from the ItemStack.
     *
     * @param enchantment The enchantment to remove
     */
    public void removeEnchantment(Enchantment enchantment){
        itemStack.removeEnchantment(enchantment);
    }

    /**
     * Sets the skull owner for a skull ItemStack.
     *
     * @param owner The UUID or name of the skull owner
     * @return This builder, for chaining
     */
    public ItemBuilder setSkullOwner(String owner){
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setOwnerProfile(
                    Bukkit.getOfflinePlayer(UUID.fromString(owner))
                          .getPlayerProfile()
            );
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds an enchantment to the ItemStack.
     *
     * @param enchantment The enchantment to add
     * @param level The level of the enchantment
     * @return This builder, for chaining
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level){
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
    public ItemBuilder setLore(String... lore){
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setLore(Arrays.asList(lore));
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
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Removes a line of lore from the ItemStack
     * based on the text of the line.
     *
     * @param line The text of the line to remove
     * @return This builder, for chaining
     */
    public ItemBuilder removeLoreLine(String line){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = null;

        if (itemMeta != null) lore = new ArrayList<>(Objects.requireNonNull(itemMeta.getLore()));

        if (lore != null && !lore.contains(line)) return this;

        if (lore != null) lore.remove(line);

        if (itemMeta != null) itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Removes a line of lore from the ItemStack
     * based on the index of the line.
     *
     * @param index The index of the line to remove
     * @return This builder, for chaining
     */
    public ItemBuilder removeLoreLine(int index){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = null;

        if (itemMeta != null) lore = new ArrayList<>(Objects.requireNonNull(itemMeta.getLore()));

        if (lore != null && (index < 0 || index > lore.size())) return this;

        if (lore != null) lore.remove(index);

        if (itemMeta != null) itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds a new line of lore to the end of the ItemStack's lore.
     *
     * @param line The text for the new line
     * @return This builder, for chaining
     */
    public ItemBuilder addLoreLine(String line){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (itemMeta != null && itemMeta.hasLore()) lore = new ArrayList<>(Objects.requireNonNull(itemMeta.getLore()));

        if (itemMeta != null) {
            lore.add(line);
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds or replaces a line of lore at a specific position.
     *
     * @param line The text for the line
     * @param pos The index where the line should be added or replaced
     * @return This builder, for chaining
     */
    public ItemBuilder addLoreLine(String line, int pos){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = null;

        if (itemMeta != null) lore = new ArrayList<>(Objects.requireNonNull(itemMeta.getLore()));

        if (lore != null) lore.set(pos, line);

        if (itemMeta != null) itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the amount of the ItemStack.
     *
     * @param amount The new amount
     * @return This builder, for chaining
     */
    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the durability of the ItemStack.
     *
     * @param durability The durability to set
     * @return This builder, for chaining
     */
    public ItemBuilder setDurability(int durability) {
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
    public ItemBuilder addGlow() {
        addUnsafeEnchantment(Enchantment.LUCK, 1);
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
    public ItemBuilder setUnbreakable() {
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
    public ItemBuilder setCustomModelData(int data) {
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
    public ItemBuilder setMeta(ItemMeta itemMeta) {
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

    /**
     * Builds and returns the final {@link ItemStack}.
     *
     * @return The built ItemStack
     */
    public ItemStack build(){
        return itemStack;
    }
}