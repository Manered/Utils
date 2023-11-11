package dev.manere.utils.serializers;

import dev.manere.utils.item.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * The Base64Serializer class provides methods for serializing and deserializing ItemStacks and ItemBuilders using Base64 encoding.
 */
public class Base64Serializer {

    /**
     * Serialize a map of ItemStacks into a Base64 encoded string.
     *
     * @param itemStacks A map of ItemStacks where the keys represent the positions and the values are the ItemStacks.
     * @return A Base64 encoded string representing the serialized ItemStacks.
     */
    public String serializeItemStacks(Map<Integer, ItemStack> itemStacks) {
        ItemStack[] items = itemStacks.values().toArray(new ItemStack[0]);

        List<String> serializedItems = new ArrayList<>();
        for (ItemStack item : items) {
            String serialized = serializeItemStack(item);
            serializedItems.add(serialized);
        }

        return String.join(";", serializedItems);
    }

    /**
     * Serialize a map of ItemBuilders into a Base64 encoded string.
     *
     * @param itemBuilders A map of ItemBuilders where the keys represent the positions and the values are the ItemBuilders.
     * @return A Base64 encoded string representing the serialized ItemStacks.
     */
    public String serializeItemBuilders(Map<Integer, ItemBuilder> itemBuilders) {
        Map<Integer, ItemStack> itemStacks = new HashMap<>();

        for (int i = 0; i < itemBuilders.size(); i++) {
            for (ItemBuilder builder : itemBuilders.values()) {
                ItemStack itemStack = builder.build();

                itemStacks.put(i, itemStack);
            }
        }

        return serializeItemStacks(itemStacks);
    }

    /**
     * Serialize a list of ItemStacks into a Base64 encoded string.
     *
     * @param itemStacks A list of ItemStacks to be serialized.
     * @return A Base64 encoded string representing the serialized ItemStacks.
     */
    public String serializeItemStacks(List<ItemStack> itemStacks) {
        Map<Integer, ItemStack> itemStacksMap = new HashMap<>();

        for (int i = 0; i < itemStacks.size(); i++) {
            for (ItemStack stack : itemStacks) {
                itemStacksMap.put(i, stack);
            }
        }

        return serializeItemStacks(itemStacksMap);
    }

    /**
     * Serialize a list of ItemBuilders into a Base64 encoded string.
     *
     * @param itemBuilders A list of ItemBuilders to be serialized.
     * @return A Base64 encoded string representing the serialized ItemStacks.
     */
    public String serializeItemBuilders(List<ItemBuilder> itemBuilders) {
        Map<Integer, ItemStack> itemStacksMap = new HashMap<>();

        for (int i = 0; i < itemBuilders.size(); i++) {
            for (ItemBuilder itemBuilder : itemBuilders) {
                ItemStack itemStack = itemBuilder.build();
                itemStacksMap.put(i, itemStack);
            }
        }

        return serializeItemStacks(itemStacksMap);
    }

    /**
     * Serialize an array of ItemStacks into a Base64 encoded string.
     *
     * @param itemStacks An array of ItemStacks to be serialized.
     * @return A Base64 encoded string representing the serialized ItemStacks.
     */
    public String serializeItemStacks(ItemStack[] itemStacks) {
        List<ItemStack> itemStacksList = Arrays.stream(itemStacks).toList();

        return serializeItemStacks(itemStacksList);
    }

    /**
     * Serialize an array of ItemBuilders into a Base64 encoded string.
     *
     * @param itemBuilders An array of ItemBuilders to be serialized.
     * @return A Base64 encoded string representing the serialized ItemStacks.
     */
    public String serializeItemStacks(ItemBuilder[] itemBuilders) {
        List<ItemBuilder> itemBuildersList = Arrays.stream(itemBuilders).toList();

        return serializeItemBuilders(itemBuildersList);
    }

    /**
     * Deserialize a Base64 encoded string into a map of ItemStacks.
     *
     * @param data A Base64 encoded string representing the serialized ItemStacks.
     * @return A map of ItemStacks where the keys represent the positions and the values are the ItemStacks.
     */
    public Map<Integer, ItemStack> deserializeItemStackMap(String data) {
        Map<Integer, ItemStack> kitContents = new HashMap<>();
        String[] serializedItems = data.split(";");

        for (int i = 0; i < serializedItems.length; i++) {
            ItemStack item = deserialize(serializedItems[i]);
            kitContents.put(i, item);
        }

        return kitContents;
    }

    /**
     * Deserialize a Base64 encoded string into a map of ItemBuilders.
     *
     * @param data A Base64 encoded string representing the serialized ItemStacks.
     * @return A map of ItemBuilders where the keys represent the positions and the values are the ItemBuilders.
     */
    public Map<Integer, ItemBuilder> deserializeItemBuilderMap(String data) {
        Map<Integer, ItemBuilder> itemBuilders = new HashMap<>();

        for (int i = 0; i < deserializeItemStackMap(data).size(); i++) {
            for (ItemStack itemStack : deserializeItemStackMap(data).values()) {
                itemBuilders.put(i, ItemBuilder.item(itemStack));
            }
        }

        return itemBuilders;
    }

    /**
     * Deserialize a Base64 encoded string into a list of ItemStacks.
     *
     * @param data A Base64 encoded string representing the serialized ItemStacks.
     * @return A list of ItemStacks.
     */
    public List<ItemStack> deserializeItemStackList(String data) {
        return deserializeItemStackMap(data).values().stream().toList();
    }

    /**
     * Deserialize a Base64 encoded string into a list of ItemBuilders.
     *
     * @param data A Base64 encoded string representing the serialized ItemStacks.
     * @return A list of ItemBuilders.
     */
    public List<ItemBuilder> deserializeItemBuilderList(String data) {
        return deserializeItemBuilderMap(data).values().stream().toList();
    }

    /**
     * Serialize an ItemStack into a Base64 encoded string.
     *
     * @param itemStack The ItemStack to be serialized.
     * @return A Base64 encoded string representing the serialized ItemStack.
     */
    public String serializeItemStack(ItemStack itemStack) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream bukkitObjectOutputStream;

        try {
            bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject(itemStack);
            bukkitObjectOutputStream.close();

            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Serialize an ItemBuilder into a Base64 encoded string.
     *
     * @param itemBuilder The ItemBuilder to be serialized.
     * @return A Base64 encoded string representing the serialized ItemStack.
     */
    public String serializeItemBuilder(ItemBuilder itemBuilder) {
        return serializeItemStack(itemBuilder.build());
    }

    /**
     * Deserialize a Base64 encoded string into an ItemStack.
     *
     * @param data A Base64 encoded string representing the serialized ItemStack.
     * @return The deserialized ItemStack.
     */
    public ItemStack deserialize(String data) {
        byte[] bytes = Base64.getDecoder().decode(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            ItemStack itemStack = (ItemStack) bukkitObjectInputStream.readObject();
            bukkitObjectInputStream.close();

            return itemStack;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
